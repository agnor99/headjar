package com.glebtik.headjar.register;

import com.glebtik.headjar.objects.items.JarItem;
import com.glebtik.headjar.util.Color;
import com.glebtik.headjar.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemInit {
    public static final JarItem JAR = new JarItem(Color.BLANK);
    public static final JarItem WHITE_JAR = new JarItem(Color.WHITE);
    public static final JarItem ORANGE_JAR = new JarItem(Color.ORANGE);
    public static final JarItem MAGENTA_JAR = new JarItem(Color.MAGENTA);
    public static final JarItem LIGHT_BLUE_JAR = new JarItem(Color.LIGHT_BLUE);
    public static final JarItem YELLOW_JAR = new JarItem(Color.YELLOW);
    public static final JarItem LIME_JAR = new JarItem(Color.LIME);
    public static final JarItem PINK_JAR = new JarItem(Color.PINK);
    public static final JarItem GRAY_JAR = new JarItem(Color.GRAY);
    public static final JarItem LIGHT_GRAY_JAR = new JarItem(Color.LIGHT_GRAY);
    public static final JarItem CYAN_JAR = new JarItem(Color.CYAN);
    public static final JarItem PURPLE_JAR = new JarItem(Color.PURPLE);
    public static final JarItem BLUE_JAR = new JarItem(Color.BLUE);
    public static final JarItem BROWN_JAR = new JarItem(Color.BROWN);
    public static final JarItem GREEN_JAR = new JarItem(Color.GREEN);
    public static final JarItem RED_JAR = new JarItem(Color.RED);
    public static final JarItem BLACK_JAR = new JarItem(Color.BLACK);

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        registerModelItem(JAR, event);
        registerModelItem(WHITE_JAR, event);
        registerModelItem(ORANGE_JAR, event);
        registerModelItem(MAGENTA_JAR, event);
        registerModelItem(LIGHT_BLUE_JAR, event);
        registerModelItem(YELLOW_JAR, event);
        registerModelItem(LIME_JAR, event);
        registerModelItem(PINK_JAR, event);
        registerModelItem(GRAY_JAR, event);
        registerModelItem(LIGHT_GRAY_JAR, event);
        registerModelItem(CYAN_JAR, event);
        registerModelItem(PURPLE_JAR, event);
        registerModelItem(BLUE_JAR, event);
        registerModelItem(BROWN_JAR, event);
        registerModelItem(GREEN_JAR, event);
        registerModelItem(RED_JAR, event);
        registerModelItem(BLACK_JAR, event);
    }

    public static void registerItem(Item item, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(item);
    }
    public static void registerModelItem(Item item, RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(item, new ItemStack(item).getMetadata(), createModelResourceLocation(item.getRegistryName().getResourcePath()));

        event.getRegistry().register(item);
    }
    private static ModelResourceLocation createModelResourceLocation(String registryName) {
        return new ModelResourceLocation(Reference.MOD_ID + ":" + registryName, "#inventory");
    }
}
