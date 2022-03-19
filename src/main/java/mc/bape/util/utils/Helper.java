package mc.bape.util.utils;

import mc.bape.Vapu.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Helper {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        message = "["+Client.name+"] " + message;
        new ChatUtil.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.LIGHT_PURPLE).build().displayClientSided();
    }

    public static boolean onServer(String server) {
        return !mc.isSingleplayer() && Helper.mc.getCurrentServerData().serverIP.toLowerCase().contains(server);
    }

    public static void sendMessageWithoutPrefix(String string) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
    }

}
