package mc.bape.modules.render;

import mc.bape.Vapu.ModuleType;
import mc.bape.util.utils.ColorUtils;
import mc.bape.Vapu.Client;
import mc.bape.modules.Module;
import mc.bape.Gui.font.FontLoaders;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;

public class HUD extends Module {
    private Option<Boolean> Health = new Option<Boolean>("Health","Health", false);
    private Option<Boolean> WaterMark = new Option<Boolean>("WaterMark","WaterMark", true);
    private Option<Boolean> ArrayList = new Option<Boolean>("ArrayList","ArrayList", true);
    private int width;
    public HUD() {
        super("HUD", Keyboard.KEY_H, ModuleType.Render,"Show " + Client.name + " HUD Screen");
        this.addValues(this.Health,this.WaterMark,this.ArrayList);
        Chinese="HUD界面";
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        ScaledResolution s = new ScaledResolution(mc);
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        int y = 1;
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiMainMenu)) return;
        if(this.WaterMark.getValue()){
            FontLoaders.C18.drawStringWithShadow(" | " + Client.name + " | " + (mc.isSingleplayer() ? "localhost:25565" : !mc.getCurrentServerData().serverIP.contains(":") ? mc.getCurrentServerData().serverIP + ":25565" : mc.getCurrentServerData().serverIP) + " | " + Minecraft.getDebugFPS() + "fps",3,4, ColorUtils.rainbow(1));
        }
        ArrayList<Module> modules = new ArrayList<>();
        for (Module m : Client.instance.moduleManager.getModules()) {
            modules.add(m);
        }
        modules.sort(new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {

                return FontLoaders.C18.getStringWidth(o2.getName()) - FontLoaders.C16.getStringWidth(o1.getName());
            }
        });
        int i = 0;
        if(this.Health.getValue()){
            if (mc.thePlayer.getHealth() >= 0.0f && mc.thePlayer.getHealth() < 10.0f) {
                this.width = 3;
            }
            if (mc.thePlayer.getHealth() >= 10.0f && mc.thePlayer.getHealth() < 100.0f) {
                this.width = 5;
            }
            mc.fontRendererObj.drawStringWithShadow("♥" + MathHelper.ceiling_float_int(mc.thePlayer.getHealth()), (float) (new ScaledResolution(mc).getScaledWidth() / 2 - this.width), (float) (new ScaledResolution(mc).getScaledHeight() / 2 - 15), -1);
        }
        for (Module m : modules) {
            if (m != null && this.ArrayList.getValue()) {
                if (m != null && m.getState()) {
                    int moduleWidth = FontLoaders.C18.getStringWidth(m.name);
                    if(Client.CHINESE){

                        FontLoaders.C18.drawString(m.Chinese, width - moduleWidth - 1, y, ColorUtils.rainbow(2)+i);
                    } else {

                        FontLoaders.C18.drawString(m.name, width - moduleWidth - 1, y, ColorUtils.rainbow(2)+i);
                    }
                    y += FontLoaders.C18.getHeight();
                }
            }
        }
    }

}
