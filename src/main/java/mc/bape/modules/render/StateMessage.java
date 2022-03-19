package mc.bape.modules.render;

import org.lwjgl.input.Keyboard;
import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;

public class StateMessage extends Module {
    public StateMessage() {
        super("NoStateMessage", Keyboard.KEY_NONE, ModuleType.Render,"Not Show Modules State info");
        Chinese="无开关信息";
    }

    public void enable() {
        Client.MessageON = true;
    }

    public void disable(){
        Client.MessageON = false;
    }
}
