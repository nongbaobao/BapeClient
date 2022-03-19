package mc.bape.modules.combat;

import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.util.TimerUtil;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AutoClicker extends Module {
    private final TimerUtil timer = new TimerUtil();
    private Numbers<Double> cps = new Numbers<Double>("CPS", "Cps",5.0, 1.0, 20.0,1.0);
    private Option<Boolean> autoblock = new Option<Boolean>("LegitAutoBlock","LegitAutoBlock", true);
    private Option<Boolean> Fautoblock = new Option<Boolean>("FastAutoBlock","FastAutoBlock", false);
    public AutoClicker() {
        super("AutoClicker", Keyboard.KEY_K, ModuleType.Combat,"auto Attack when you hold the attack button");
        this.addValues(this.cps,this.autoblock,this.Fautoblock);
        Chinese="连点器";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            float delays = new Random().nextInt(this.cps.getValue().intValue()) + 10;
            if (timer.delay(delays * 10)) {
                mc.thePlayer.swingItem();
                KeyBinding.onTick(key);
                try {
                    if(mc.objectMouseOver.entityHit != null) {
                        mc.playerController.attackEntity(mc.thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //NAUTOBLOCK
                if ((Boolean) this.autoblock.getValue() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.isEntityAlive() && mc.thePlayer.inventory.getCurrentItem() != null){
                    if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && timer.delay(100)) {
                        mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                        timer.reset();
                    }
                }
                //FAUTOBLOCK
                if ((Boolean) this.Fautoblock.getValue()){
                    if (mc.thePlayer.getCurrentEquippedItem() == null) {
                        return;
                    }
                    if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                        return;
                    }
                    mc.thePlayer.getHeldItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                }
                timer.reset();
            }
        }
    }
}
