package mc.bape.modules.Config;

import mc.bape.Gui.font.FontLoaders;
import mc.bape.Manager.ConfigManager;
import mc.bape.Manager.ModuleManager;
import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.util.utils.ColorUtils;
import mc.bape.util.utils.Helper;
import mc.bape.value.Value;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.input.Keyboard.KEY_N;
import static org.lwjgl.input.Keyboard.KEY_NONE;

public class SaveConfig extends Module {
    public SaveConfig() {
        super("SaveConfig", KEY_NONE, ModuleType.Config,"Save your module setting (config)");
        Chinese="保存配置";
        NoToggle=true;
    }

    public void enable() {
        String values = "";
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        ConfigManager.save(Client.config+"-values.cfg", values, false);
        String enabled = "";
        ArrayList<Module> modules = new ArrayList<>();
        for (Module m : Client.instance.moduleManager.getModules()) {
            modules.add(m);
        }
        for (Module m : modules) {
            if (m != null && m.getState() && m != this) {
                enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
            }
        }
        String content = "";
        Module m;
        for(Iterator var4 = ModuleManager.getModules().iterator(); var4.hasNext(); content = content + String.format("%s:%s%s", new Object[]{m.getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator()})) {
            m = (Module)var4.next();
        }
        ConfigManager.save(Client.config+"-binds.cfg", content, false);
        ConfigManager.save(Client.config+"-modules.cfg", enabled, false);
        ConfigManager.save(Client.config+"-client.cfg", Client.name, false);
        Helper.sendMessage("Configs Saved.");
        state=false;
    }
}
