package mc.bape.modules.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import mc.bape.Vapu.Client;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    private Numbers<Double> horizontal = new Numbers<Double>("Horizontal", "Horizontal",96.0, 0.0, 100.0,1.0);
    private Numbers<Double> vertical = new Numbers<Double>("Vertical", "Vertical",100.0, 0.0, 100.0,1.0);
    private Numbers<Double> chance = new Numbers<Double>("Chance", "Chance",100.0, 0.0, 100.0,1.0);
    private Option<Boolean> Targeting = new Option<Boolean>("On Targeting","On Targeting", false);
    private Option<Boolean> NoSword = new Option<Boolean>("No Sword","No Sword", false);
    public Velocity() {
        super("Velocity", Keyboard.KEY_NONE, ModuleType.Movement,"Reduces your knockback");
        this.addValues(this.horizontal,this.vertical,this.chance,this.Targeting,this.NoSword);
        Chinese="反击退";
    }
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent ev) {
        if(Client.nullCheck())
            return;

        if (mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
            if (this.Targeting.getValue() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                return;
            }

            if (this.NoSword.getValue() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                return;
            }

            if (this.chance.getValue() != 100.0D) {
                double ch = Math.random();
                if (ch >= this.chance.getValue() / 100.0D) {
                    return;
                }
            }

            if (this.horizontal.getValue() != 100.0D) {
                mc.thePlayer.motionX *= this.horizontal.getValue() / 100.0D;
                mc.thePlayer.motionZ *= this.horizontal.getValue() / 100.0D;
            }

            if (this.vertical.getValue() != 100.0D) {
                mc.thePlayer.motionY *= this.vertical.getValue() / 100.0D;
            }
        }

    }

}