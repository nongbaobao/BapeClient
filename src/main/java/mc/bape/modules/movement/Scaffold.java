package mc.bape.modules.movement;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.value.Mode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Scaffold extends Module {
    private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) ScaffoldMode.values(), (Enum) ScaffoldMode.Legit);
    public Scaffold() {
        super("Scaffold", Keyboard.KEY_NONE, ModuleType.Movement,"Make you bridge faster");
        this.addValues(this.mode);
        Chinese="ExampleModule";
    }
    static enum ScaffoldMode {
        Legit
    }
    public Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    public Block getBlockUnderPlayer(EntityPlayer player) {
        return getBlock(new BlockPos(player.posX, player.posY - 1.0d, player.posZ));
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if(this.mode.getValue() == ScaffoldMode.Legit){
            if (getBlockUnderPlayer(mc.thePlayer) instanceof BlockAir) {
                if (mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                }
            } else {
                if (mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
            }
        } else if(false) {
            double x2 = event.player.motionX;
            double y2 = event.player.motionY;
            double z2 = event.player.motionZ;
            if (mc.thePlayer.onGround) {
                double increment = 0.05;
                while (x2 != 0.0) {
                    if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(x2, -1.0, 0.0)).isEmpty())
                        break;
                    if (x2 < increment && x2 >= -increment) {
                        x2 = 0.0;
                        continue;
                    }
                    if (x2 > 0.0) {
                        x2 -= increment;
                        continue;
                    }
                    x2 += increment;
                }
                while (z2 != 0.0) {
                    if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, -1.0, z2)).isEmpty())
                        break;
                    if (z2 < increment && z2 >= -increment) {
                        z2 = 0.0;
                        continue;
                    }
                    if (z2 > 0.0) {
                        z2 -= increment;
                        continue;
                    }
                    z2 += increment;
                }
                while (x2 != 0.0 && z2 != 0.0 && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(x2, -1.0, z2)).isEmpty()) {
                    x2 = x2 < increment && x2 >= -increment ? 0.0 : (x2 > 0.0 ? (x2 -= increment) : (x2 += increment));
                    if (z2 < increment && z2 >= -increment) {
                        z2 = 0.0;
                        continue;
                    }
                    if (z2 > 0.0) {
                        z2 -= increment;
                        continue;
                    }
                    z2 += increment;
                }
            }
            event.player.motionX = x2;
            event.player.motionY = y2;
            event.player.motionZ = z2;
        }
    }

    public void onEnable() {
        mc.thePlayer.setSneaking(false);
        super.enable();
    }

    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
        super.disable();
    }


}
