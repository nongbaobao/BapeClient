package mc.bape.modules.other;

import mc.bape.value.Option;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.input.Keyboard;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.util.utils.Helper;

import java.util.Random;

import static org.lwjgl.input.Keyboard.KEY_G;
import static org.lwjgl.input.Keyboard.KEY_P;

public class AutoGG extends Module {
//    private final String[] 足智多谋 = new String[]{"OK兄弟们", "全体目光向我看齐", "看我看我", "我宣布个事", "我是个{AWESOME}！"};
//    private final String[] 聪明绝顶 = new String[]{"傻逼","圣杯", "失败", "鼠标", "设备", "识别", "手表", "上班", "书包", "手臂", "顺便", "沙包", "商标", "手办", "水泵", "社保", "水杯", "手柄", "书本", "纱布", "扫把", "水杯", "烧饼"};
    private int counter = 0;
    private boolean warnAlready = false;


    static String[] GGs = {
            "GG",
            "gg",
            "Gg",
            "gG"
            };
    public AutoGG(){
        super("AutoGG", Keyboard.KEY_NONE, ModuleType.Other,"send GG when you press G");
        Chinese="半自动GG";
    }

    @Override
    public void enable() {
        Helper.sendMessage("Press G to send GG");
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        if(this.state) {
            if (Keyboard.isKeyDown(KEY_G)) {
                Random r = new Random();
                mc.thePlayer.sendChatMessage(GGs[r.nextInt(GGs.length)]);
            }
        }
    }

}
