/*
* 警告：这个类的所有东西都是抄的
* 而且有可能引起不适的极度脑溢血命名
* 请谨慎查看代码
* -Ceeyourbac
*/

package mc.bape.modules.combat;
import mc.bape.Vapu.ModuleType;
import mc.bape.modules.Module;
import mc.bape.value.Numbers;
import mc.bape.value.Option;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.client.event.MouseEvent;

import java.util.List;
import java.util.Random;
public class Reach extends Module {
    public Random rand;
    private Option<Boolean> RandomReach = new Option<Boolean>("RandomReach","RandomReach", true);
    private Option<Boolean> weaponOnly = new Option<Boolean>("WeaponOnly","weaponOnly", false);
    private Option<Boolean> movingOnly = new Option<Boolean>("MovingOnly","movingOnly", false);
    private Option<Boolean> sprintOnly = new Option<Boolean>("SprintOnly","sprintOnly", false);
    private Option<Boolean> hitThroughBlocks = new Option<Boolean>("HitThroughBlocks","sprintOnly", false);
    private Numbers<Double> MinReach = new Numbers<Double>("Reach", "Reach",3.5, 3.0, 6.0,1.0);
    public Reach() {
            super("Reach", Keyboard.KEY_NONE, ModuleType.Combat,"Make you can attack far target");
            this.addValues(this.weaponOnly,this.movingOnly,this.sprintOnly,this.hitThroughBlocks,this.MinReach);
            Chinese="长臂猿";
        }
    @SubscribeEvent
    public void onMove(final MouseEvent ev) {
        if (true) {
            if (this.weaponOnly.getValue()) {
                if (mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                    return;
                }
            }
            if (this.movingOnly.getValue() && mc.thePlayer.moveForward == 0.0 && mc.thePlayer.moveStrafing == 0.0) {
                return;
            }
            if (this.sprintOnly.getValue() && !mc.thePlayer.isSprinting()) {
                return;
            }
            if (!this.hitThroughBlocks.getValue() && mc.objectMouseOver != null) {
                final BlockPos zzzzz = mc.objectMouseOver.getBlockPos();
                if (zzzzz != null && mc.theWorld.getBlockState(zzzzz).getBlock() != Blocks.air) {
                    return;
                }
            }
            if(false){
                double zzzzzD2 = 3.0 + this.rand.nextDouble() * (this.MinReach.getValue() - 3.0);
                final Object[] zzzzzN = Reaching(zzzzzD2, 0.0, 0.0f);
                if (zzzzzN == null) {
                    return;
                }
                mc.objectMouseOver = new MovingObjectPosition((Entity)zzzzzN[0], (Vec3)zzzzzN[1]);
                mc.pointedEntity = (Entity)zzzzzN[0];
            } else {
                double Reach = this.MinReach.getValue();
                final Object[] zzzzzN = Reaching(Reach, 0.0, 0.0f);
                if (zzzzzN == null) {
                    return;
                }
                mc.objectMouseOver = new MovingObjectPosition((Entity)zzzzzN[0], (Vec3)zzzzzN[1]);
                mc.pointedEntity = (Entity)zzzzzN[0];
            }
        }
    }

    public static Object[] Reaching(final double QwQ, final double owo, final float cwc) {
        final Entity qwq = mc.getRenderViewEntity();
        Entity entity = null;
        if (qwq == null || mc.theWorld == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        final Vec3 awa = qwq.getPositionEyes(0.0f);
        final Vec3 QAQ = qwq.getLook(0.0f);
        final Vec3 ovo = awa.addVector(QAQ.xCoord * QwQ, QAQ.yCoord * QwQ, QAQ.zCoord * QwQ);
        Vec3 aWa = null;
        final float cWc = 1.0f;
        final List QaQ = mc.theWorld.getEntitiesWithinAABBExcludingEntity(qwq, qwq.getEntityBoundingBox().addCoord(QAQ.xCoord * QwQ, QAQ.yCoord * QwQ, QAQ.zCoord * QwQ).expand(1.0, 1.0, 1.0));
        double AWA = QwQ;
        for (int O_O = 0; O_O < QaQ.size(); ++O_O) {
            final Entity OvO = (Entity) QaQ.get(O_O);
            if (OvO.canBeCollidedWith()) {
                final float czfsb = OvO.getCollisionBorderSize();
                AxisAlignedBB OwO = OvO.getEntityBoundingBox().expand((double)czfsb, (double)czfsb, (double)czfsb);
                OwO = OwO.expand(owo, owo, owo);
                final MovingObjectPosition Qwq = OwO.calculateIntercept(awa, ovo);
                if (OwO.isVecInside(awa)) {
                    if (0.0 < AWA || AWA == 0.0) {
                        entity = OvO;
                        aWa = ((Qwq == null) ? awa : Qwq.hitVec);
                        AWA = 0.0;
                    }
                }
                else if (Qwq != null) {
                    final double aWa_awa = awa.distanceTo(Qwq.hitVec);
                    if (aWa_awa < AWA || AWA == 0.0) {
                        final boolean canRiderInteract = false;
                        if (OvO == qwq.ridingEntity) {
                            if (AWA == 0.0) {
                                entity = OvO;
                                aWa = Qwq.hitVec;
                            }
                        }
                        else {
                            entity = OvO;
                            aWa = Qwq.hitVec;
                            AWA = aWa_awa;
                        }
                    }
                }
            }
        }
        if (AWA < QwQ && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if (entity == null || aWa == null) {
            return null;
        }
        return new Object[] { entity, aWa };
    }

    }

