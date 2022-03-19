package mc.bape.modules.world;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static mc.bape.modules.render.StorageESP.re;

public class Xray extends Module {
    private Numbers<Double> ragen = new Numbers<Double>("Ragen", "Ragen",20.0, 5.0, 50.0,1.0);
    private Option<Boolean> iron = new Option<Boolean>("Iron","iron", true);
    private Option<Boolean> gold = new Option<Boolean>("Gold","gold", true);
    private Option<Boolean> diamond = new Option<Boolean>("Diamond","diamond", true);
    private Option<Boolean> emerald = new Option<Boolean>("Emerald","emerald", true);
    private Option<Boolean> coal = new Option<Boolean>("Coal","coal", true);
    public static List<Block> ores;

    public Xray() {
        super("Xray", Keyboard.KEY_NONE, ModuleType.World,"Ore Perspective Render");
        this.addValues(this.ragen,this.iron,this.gold,this.diamond,this.emerald,this.coal);
    }

    @SubscribeEvent
    public void orl(final RenderWorldLastEvent ev) {
        final BlockPos pos = this.mc.thePlayer.getPosition();
        for (int range = (int) this.ragen.getValue().intValue(), x = pos.getX() - range; x <= pos.getX() + range; ++x) {
            for (int z = pos.getZ() - range; z < pos.getZ() + range; ++z) {
                for (int y = pos.getY() - range; y < pos.getY() + range; ++y) {
                    final Block bl = this.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (this.ores.contains(bl)) {
                        if (this.iron.getValue() || !bl.equals(Blocks.iron_ore)) {
                            if (this.gold.getValue() || !bl.equals(Blocks.gold_ore)) {
                                if (this.diamond.getValue() || !bl.equals(Blocks.diamond_ore)) {
                                    if (this.emerald.getValue() || !bl.equals(Blocks.emerald_ore)) {
                                                if (this.coal.getValue() || !bl.equals(Blocks.coal_ore)) {
                                                        this.x(bl, new BlockPos(x, y, z));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void x(final Block b, final BlockPos bp) {
        final String[] rgb = this.z(b).split("-");
        final int red = Integer.parseInt(rgb[0]);
        final int green = Integer.parseInt(rgb[1]);
        final int blue = Integer.parseInt(rgb[2]);
        re(bp, new Color(red, green, blue).getRGB());
    }

    private String z(final Block b) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (b.equals(Blocks.iron_ore)) {
            red = 255;
            green = 255;
            blue = 255;
        }
        else if (b.equals(Blocks.gold_ore)) {
            red = 255;
            green = 255;
        }
        else if (b.equals(Blocks.diamond_ore)) {
            green = 220;
            blue = 255;
        }
        else if (b.equals(Blocks.emerald_ore)) {
            red = 35;
            green = 255;
        }
        else if (b.equals(Blocks.lapis_ore)) {
            green = 50;
            blue = 255;
        }
        else if (b.equals(Blocks.redstone_ore)) {
            red = 255;
        }
        else if (b.equals(Blocks.mob_spawner)) {
            red = 30;
            blue = 135;
        }
        return red + "-" + green + "-" + blue;
    }

    {
        this.ores = new ArrayList<Block>();
    }


}
