package com.glebtik.headjar.objects.items;

import com.glebtik.headjar.jars.HeadJar;
import com.glebtik.headjar.jars.NoJar;
import com.glebtik.headjar.network.SetPlayerJarMessage;
import com.glebtik.headjar.util.Color;
import com.glebtik.headjar.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static com.glebtik.headjar.capabilities.JarProvider.JAR;

import com.glebtik.headjar.network.PacketHandler;
import net.minecraftforge.client.model.ModelLoader;

public class JarItem extends Item {
    private ItemStack item;
    public final Color color;
    public JarItem(Color color) {
        item = new ItemStack(this);


        setUnlocalizedName(color.prefix+"jar");
        setRegistryName(color.prefix+"jar");

        this.setMaxDamage(0);
        this.setHasSubtypes(false);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.MISC);
        this.color = color;
    }
    public ItemStack getItem() {
        return item;
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote){
            if(playerIn.getCapability(JAR, null).getJar() instanceof HeadJar){
                NoJar newJar = new NoJar();
                playerIn.getCapability(JAR, null).setJar(newJar);

                SetPlayerJarMessage message = SetPlayerJarMessage.create((EntityPlayerMP) playerIn);
                PacketHandler.INSTANCE.sendToAll(message);

                return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
            }
            if(playerIn.getCapability(JAR, null).getJar() instanceof NoJar){
                HeadJar jar = new HeadJar();
                jar.setColor(((JarItem)itemStackIn.getItem()).color);
                playerIn.getCapability(JAR, null).setJar(jar);

                SetPlayerJarMessage message = SetPlayerJarMessage.create((EntityPlayerMP) playerIn);
                PacketHandler.INSTANCE.sendToAll(message);

                return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
            }
            return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
        }else{
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
        }
    }
}