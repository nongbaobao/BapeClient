package mc.bape.Vapu;

import mc.bape.util.Helper;

public class Debug {
    public static void sendMessage(String Message){
        if(Client.DebugMode){
            Helper.sendMessage(Message);
        }
    }
}
