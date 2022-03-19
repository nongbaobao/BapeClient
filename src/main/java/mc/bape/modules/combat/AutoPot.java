package mc.bape.modules.combat;

import mc.bape.Vapu.Debug;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.util.Helper;
import mc.bape.util.TimerUtil;
import mc.bape.value.Mode;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoPot extends Module {

    public Numbers<Double> health = new Numbers<Double>("Health", "health", 3.0, 0.0, 10.0, 0.5);
    public Numbers<Double> delay = new Numbers<Double>("Delay", "delay", 400.0, 0.0, 1000.0, 10.0);
    public Option<Boolean> jump = new Option<Boolean>("Jump", "jump", true);
    public Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])HealMode.values(), (Enum)HealMode.Potion);
    static boolean currentlyPotting = false;
    public boolean isUsing = true;
    public int slot;
    public TimerUtil timer = new TimerUtil();
    public AutoPot() {
        super("AutoPot", Keyboard.KEY_NONE, ModuleType.Combat,"Auto use pot when you low health");
        this.addValues(this.health,this.delay,this.jump,this.mode);
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (this.timer.hasReached(this.delay.getValue()) && (double)this.mc.thePlayer.getHealth() < this.health.getValue()) {
            this.slot = this.mode.getValue() == HealMode.Potion ? this.getPotionSlot() : (this.mode.getValue() == HealMode.Soup ? this.getSoupSlot() : this.getPotionSlot());
            boolean bl =  this.slot != -1 && (this.jump.getValue() == false || this.mc.thePlayer.onGround);
            Debug.sendMessage("do pot");
                this.timer.reset();
                if (this.mode.getValue() == HealMode.Potion) {
                    currentlyPotting = true;
                    if (this.timer.hasReached(1.0)) {
                        currentlyPotting = false;
                        int current = this.mc.thePlayer.inventory.currentItem;
                        int next = nextSlot();
                        moveToHotbar(this.slot, next);
                        mc.thePlayer.inventory.currentItem = next;
                        Debug.sendMessage("Do invent");
                        mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                        Debug.sendMessage("do switch");
                        mc.thePlayer.inventory.currentItem = current;
                        Debug.sendMessage("do return");
                        mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                        if (this.mc.thePlayer.onGround && this.jump.getValue().booleanValue() && this.mode.getValue() == HealMode.Potion) {
                            this.mc.thePlayer.motionY = 0.42;
                        }
                        this.timer.reset();
                }
            }
        }
    }

    public void moveToHotbar(final int slot, final int hotbar) {
        this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbar, 2, mc.thePlayer);
    }

    public int nextSlot() {
        return (mc.thePlayer.inventory.currentItem < 8) ? (mc.thePlayer.inventory.currentItem + 1) : 0;
    }

    public int getPotionSlot() {
        Debug.sendMessage("do pot slot");
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion)) continue;
            ItemPotion ip = (ItemPotion)is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata())) continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id) continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing) continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    public int getSoupSlot() {
        int slot = -1;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup)) continue;
            slot = s.slotNumber;
            break;
        }
        return slot;
    }

    public int getPotionCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemPotion)) continue;
            ItemPotion ip = (ItemPotion)is.getItem();
            if (!ItemPotion.isSplash(is.getMetadata())) continue;
            boolean hasHealing = false;
            for (PotionEffect pe : ip.getEffects(is)) {
                if (pe.getPotionID() != Potion.heal.id) continue;
                hasHealing = true;
                break;
            }
            if (!hasHealing) continue;
            ++count;
        }
        return count;
    }

    public int getSoupCount() {
        int count = 0;
        for (Slot s : this.mc.thePlayer.inventoryContainer.inventorySlots) {
            ItemStack is;
            if (!s.getHasStack() || !((is = s.getStack()).getItem() instanceof ItemSoup)) continue;
            ++count;
        }
        return count;
    }

    static enum HealMode {
        Potion,
        Soup;
    }

}
