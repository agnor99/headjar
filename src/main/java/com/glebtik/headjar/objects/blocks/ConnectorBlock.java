package com.glebtik.headjar.objects.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConnectorBlock extends Block {

    public ConnectorBlock() {
        super(Material.IRON);
        setHardness(5f);
        setSoundType(SoundType.METAL);
        setUnlocalizedName("jar_connector");
        setRegistryName("jar_connector");
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);

    }
}
