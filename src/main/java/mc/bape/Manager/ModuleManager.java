package mc.bape.Manager;

import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Config.IGN;
import mc.bape.modules.Config.LoadConfig;
import mc.bape.modules.Config.SaveConfig;
import mc.bape.modules.Config.Uninject;
import mc.bape.modules.Module;
import mc.bape.modules.blatant.Criticals;
import mc.bape.modules.blatant.Fly;
import mc.bape.modules.blatant.Killaura;
import mc.bape.modules.blatant.Speed;
import mc.bape.modules.movement.*;
import mc.bape.modules.other.AutoGG;
import mc.bape.modules.other.NameTags;
import mc.bape.modules.other.NoCommand;
import mc.bape.modules.world.*;
import mc.bape.modules.player.*;
import mc.bape.modules.render.*;
import mc.bape.modules.combat.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {

    static ArrayList<Module> Modules = new ArrayList<Module>();

    public static ArrayList<Module> getModules() {
        return Modules;
    }

    public ModuleManager() {

    }

    public static Module getModule(String name) {
        for (Module m : Modules) {
            if (m.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase()))
                return m;
        }
        return null;
    }

    public static List<Module> getModulesInType(ModuleType t) {
        ArrayList<Module> output = new ArrayList<Module>();
        ArrayList<Module> module = new ArrayList<Module>();
        module.addAll(module);
        for (Module m : module) {
            if (m.getCategory() != t) continue;
            output.add(m);
        }
        output.sort(Comparator.comparingInt((Module o) -> Character.toLowerCase(o.getName().charAt(0))).thenComparingInt(o -> o.getName().charAt(0)));
        return output;
    }

    static {
        // 没Add的都是有问题的，不要add
        Modules.add(new InvManager());
        Modules.add(new NameTags());
        Modules.add(new Velocity());
        Modules.add(new Fly());
        Modules.add(new AntiBot());
        Modules.add(new AutoPot());
        Modules.add(new AutoGG());
        Modules.add(new Speed());
        Modules.add(new Sprint());
        Modules.add(new ClickGUI());
        Modules.add(new IGN());
        Modules.add(new StateMessage());
        Modules.add(new HUD());
        Modules.add(new FullBright());
        Modules.add(new AutoTools());
        Modules.add(new LTap());
        Modules.add(new LoadConfig());
        Modules.add(new SaveConfig());
        Modules.add(new Aimbot());
        Modules.add(new Uninject());
        Modules.add(new InvMove());
        Modules.add(new Killaura());
        Modules.add(new BowAimBot());
        Modules.add(new NoFall());
        Modules.add(new MurderMystery());
        Modules.add(new FuckServer());
        Modules.add(new Reach());
        Modules.add(new StorageESP());
        Modules.add(new ESP());
        Modules.add(new ChestStealer());
        Modules.add(new AntiAFK());
        Modules.add(new AutoClicker());
        Modules.add(new NoCommand());
        Modules.add(new Scaffold());
        Modules.add(new Criticals());
        Modules.add(new FastPlace());
    }
}
