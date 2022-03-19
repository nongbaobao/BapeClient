//TODO: NoCommand
package mc.bape.modules.other;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import org.lwjgl.input.Keyboard;

public class NoCommand extends Module {
    public NoCommand() {
        super("NoCommand", Keyboard.KEY_NONE, ModuleType.Other, "No command.");
        Chinese=("没有指令");
    }

}
