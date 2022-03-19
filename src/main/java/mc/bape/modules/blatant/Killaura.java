package mc.bape.modules.blatant;

import mc.bape.Manager.ModuleManager;
import mc.bape.Vapu.Client;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.modules.combat.AntiBot;
import mc.bape.modules.render.StorageESP;
import mc.bape.util.ColorUtil;
import mc.bape.util.utils.TimerUtil;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.realms.RealmsMth.sqrt;
import static net.minecraft.realms.RealmsMth.wrapDegrees;

public class Killaura extends Module {
    private final TimerUtil timer = new TimerUtil();
    public static EntityLivingBase target;
    private Numbers<Double> rangeValue = new Numbers<Double>("Range", "Range",4.2, 1.0, 6.0,1.0);
    private Numbers<Double> cps = new Numbers<Double>("Cps", "Cps",10.0, 1.0, 20.0,1.0);
    private Numbers<Double> yaw = new Numbers<Double>("Yaw", "Yaw",15.0, 1.0, 50.0,1.0);
    private Numbers<Double> pitch = new Numbers<Double>("Pitch", "Pitch",15.0, 1.0, 50.0,1.0);
    private Option<Boolean> autoblock = new Option<Boolean>("AutoBlock","AutoBlock", false);
    private Option<Boolean> swing = new Option<Boolean>("Swing","swing", true);
    private Option<Boolean> esp = new Option<Boolean>("DrawESP","DrawESP", true);
    public Killaura() {
        super("Killaura", Keyboard.KEY_NONE, ModuleType.Blatant,"Auto Attack the entity near you");
        //TODO Target
        this.addValues(this.rangeValue,this.autoblock,this.cps,this.swing,this.yaw,this.pitch);
        Chinese="杀戮光环";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        target = null;
        
        float delays = new Random().nextInt(this.cps.getValue().intValue()) + 5;
        if (timer.delay(delays * 10)) {
            if(Client.nullCheck())
                return;
            for (Entity object : getEntityList()) {
                EntityLivingBase entity;
                if (!(object instanceof EntityLivingBase) || !this.check(entity = (EntityLivingBase) object)) continue;
                target = entity;
            }
            if(target == null)
                return;
            if(AntiBot.isServerBot(target))
                return;
            if(target.getHealth()==0)
                return;

            assistFaceEntity(target, this.yaw.getValue().floatValue(), this.pitch.getValue().floatValue());
            Object[] objects = mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase && entity != mc.thePlayer && ((EntityLivingBase) entity).getHealth() > 0F && entity.getDistanceToEntity(mc.thePlayer) <= rangeValue.getValue()).sorted(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer))).toArray();
            if (objects.length > 0)
                target = (EntityLivingBase) objects[0];

            if(ModuleManager.getModule("Criticals").getState() && Criticals.canJump() && mc.thePlayer.onGround)
                mc.thePlayer.jump();
            if(this.swing.getValue()){
                mc.thePlayer.swingItem();
            }
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            if ((Boolean) this.autoblock.getValue()){
                if (mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                    return;
                }
                if (this.autoblock.getValue() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.isEntityAlive()){
                    if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && timer.delay(100)) {
                        mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                        timer.reset();
                    }
                }
            }
            target = null;
            timer.reset();
        }
    }

    public static void assistFaceEntity(Entity entity, float yaw, float pitch) {
        Client.nullCheck();
        double yDifference;
        if (entity == null) {
            return;
        }
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            yDifference = entityLivingBase.posY + (double) entityLivingBase.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            yDifference = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }
        double dist = sqrt(diffX * diffX + diffZ * diffZ);
        float rotationYaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float rotationPitch = (float) (-(Math.atan2(yDifference, dist) * 180.0 / Math.PI));
        if (yaw > 0.0f) {
            mc.thePlayer.rotationYaw = updateRotation(mc.thePlayer.rotationYaw, rotationYaw, yaw / 4.0f);
        }
        if (pitch > 0.0f) {
            mc.thePlayer.rotationPitch = updateRotation(mc.thePlayer.rotationPitch, rotationPitch, pitch / 4.0f);
        }
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = wrapDegrees(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }

    public static List<Entity> getEntityList() {
        Client.nullCheck();
        if(mc.theWorld != null){return mc.theWorld.getLoadedEntityList();} else {return null;}
    }

    public boolean check(EntityLivingBase entity) {
        Client.nullCheck();
        if (entity instanceof EntityArmorStand) {
            return false;
        }
        if (entity == mc.thePlayer) {
            return false;
        }
        if (entity.isDead) {
            return false;
        }
        if (entity.getHealth() == 0 ) {
            return false;
        }
        if(AntiBot.isServerBot(entity)){
            return false;
        }
        if(entity.getDistanceToEntity(mc.thePlayer) > this.rangeValue.getValue()){
            return false;
        }
        return mc.thePlayer.canEntityBeSeen(entity);
    }
}
