package com.glebtik.headjar.objects.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ConnectorBlock extends Block {
    private static final AxisAlignedBB hitbox = new AxisAlignedBB(0,0,0,1,3.0d/16d, 1);
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

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return hitbox;
    }
}
