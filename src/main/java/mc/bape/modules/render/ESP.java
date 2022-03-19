package mc.bape.modules.render;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.modules.combat.AntiBot;
import mc.bape.util.utils.ColorUtil;
import mc.bape.value.Option;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ESP extends Module {
    private Option<Boolean> invisible = new Option<Boolean>("Invisible","invisible", false);
    private Option<Boolean> redOnDalage = new Option<Boolean>("RedOnDalage","redOnDalage", true);
    public ESP() {
        super("ESP", Keyboard.KEY_NONE, ModuleType.Player,"Draw boxes for other player");
        this.addValues(this.invisible,this.redOnDalage);
        Chinese="ExampleModule";
    }
    @SubscribeEvent
    public void o(final RenderWorldLastEvent ev) {
        for (final EntityPlayer en : mc.theWorld.playerEntities) {
            if (en != mc.thePlayer && (!AntiBot.isServerBot(en))) {
                if (!this.invisible.getValue() && en.isInvisible()) {
                    return;
                }
                int rgb = 0;
                rgb = ColorUtil.getRainbow().getRGB();
                StorageESP.drawESP((Entity)en, rgb, this.redOnDalage.getValue(), (int) 2.0);
            }
        }
    }
}
