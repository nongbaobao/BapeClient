package mc.bape.modules.render;

import mc.bape.Gui.MatrixClickGui.ClickUi;
import mc.bape.Gui.VapeClickGui.VapeClickGui;
import mc.bape.Gui.LuneClickGui.*;
import mc.bape.modules.blatant.Criticals;
import mc.bape.value.Mode;
import org.lwjgl.input.Keyboard;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;

public class ClickGUI extends Module {
	private Mode<Enum> mode = new Mode("Mode", "mode", (Enum[]) ClickGUI.GuiMode.values(), (Enum) ClickGUI.GuiMode.Vape);

	static enum GuiMode {
		Vape,
		Lune,
		Matrix
	}
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, ModuleType.Render,"Open ClickGui");
		this.addValues(this.mode);
		Chinese="点击GUI";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enable() {
		this.setState(false);
		mc.thePlayer.closeScreen();
		if(this.mode.getValue() == GuiMode.Vape) {
			mc.displayGuiScreen(new VapeClickGui());
		} else if(this.mode.getValue() == GuiMode.Lune){
			mc.displayGuiScreen(new Lune());
		} else if(this.mode.getValue() == GuiMode.Matrix){
			mc.displayGuiScreen(new ClickUi());
		}
	}
}
