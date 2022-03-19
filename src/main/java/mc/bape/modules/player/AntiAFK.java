package mc.bape.modules.player;

import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.util.TimerUtil;
import mc.bape.util.utils.Helper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AntiAFK extends Module {
    public TimerUtil timer = new TimerUtil();
    public AntiAFK() {
        super("AntiAFK", Keyboard.KEY_NONE, ModuleType.Other,"Prevent you kicked of AFK");
        Chinese="AntiAFK";
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.timer.delay((long) 10.0)) {
            if(mc.thePlayer.onGround){
                mc.thePlayer.jump();
            }
            this.timer.reset();
        }
    }
}
