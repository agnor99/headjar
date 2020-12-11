package com.glebtik.headjar.register;

import com.glebtik.headjar.objects.blocks.ConnectorBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockInit {

    public static final ConnectorBlock CONNECTOR = new ConnectorBlock();

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        registerBlock(event, CONNECTOR);
    }
    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        ItemInit.registerModelItem(new ItemBlock(CONNECTOR).setRegistryName("jar_connector").setUnlocalizedName("jar_connector").setCreativeTab(CreativeTabs.MISC), event);
    }

    public static void registerBlock(RegistryEvent.Register<Block> event, Block block) {
        event.getRegistry().register(block);
    }

}
