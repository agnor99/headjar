package com.glebtik.headjar.client.render.iron_golem;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderIronGolemForm extends RenderLivingBase<EntityLivingBase> {

    private static final ResourceLocation IRON_GOLEM_TEXTURES = new ResourceLocation("textures/entity/iron_golem.png");
    EntityPlayer player;
    public RenderIronGolemForm(RenderManager renderManagerIn, EntityPlayer player) {
        super(renderManagerIn, new ModelIronGolemForm(), 0.5f);
        this.player = player;
    }

    @Override
    public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void applyRotations(EntityLivingBase entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

        if ((double)entityLiving.limbSwingAmount >= 0.01D) {
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityLivingBase entity) {
        return IRON_GOLEM_TEXTURES;
    }
}
