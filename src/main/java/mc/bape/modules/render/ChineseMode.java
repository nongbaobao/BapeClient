package mc.bape.modules.render;

import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import org.lwjgl.input.Keyboard;

public class ChineseMode extends Module {
    public ChineseMode() {
        super("中文", Keyboard.KEY_NONE, ModuleType.Combat,"");
        Chinese="ChineseMode";
    }


    @Override
    public void enable(){
        Client.CHINESE = true;
        this.mc.thePlayer.closeScreen();
    }

    @Override
    public void disable(){
        Client.CHINESE = false;
        this.mc.thePlayer.closeScreen();
    }

}
