package com.glebtik.headjar.objects.blocks;


import com.glebtik.headjar.register.BlockInit;
import com.glebtik.headjar.util.StructureUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

public class ConnectorBlock extends Block {
    private static final AxisAlignedBB hitbox = new AxisAlignedBB(0,0,0,1,3.0d/16d, 1);
    private static final AxisAlignedBB walking_hitbox = new AxisAlignedBB(0,0,0,1,1.0d/16d, 1);
    public ConnectorBlock() {
        super(Material.IRON);
        setHardness(5f);
        setSoundType(SoundType.METAL);
        setUnlocalizedName("jar_connector");
        setRegistryName("jar_connector");
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        StructureUtils.Structure structure = new StructureUtils.Structure(3,2,3).appendLayer().append(" i ").append(" i ").build().appendLayer().append("iii").append("iii").build().appendLayer().append("   ").append(" c ").build().withAnchor('c').withBlockFor(' ', Blocks.AIR).withBlockFor('i', Blocks.IRON_BLOCK).withBlockFor('c', BlockInit.CONNECTOR);

        super.onBlockAdded(worldIn, pos, state);
        if(worldIn instanceof WorldServer) {
            StructureUtils.StructurePredicate.StructureMatchData matcher = structure.match((WorldServer) worldIn, pos);
            if(matcher.correct) {
                for (int x = matcher.corner.getX(); x <= matcher.corner2.getX(); x++) {
                    for (int y = matcher.corner.getY(); y <= matcher.corner2.getY(); y++) {
                        for (int z = matcher.corner.getZ(); z <= matcher.corner2.getZ(); z++) {
                            worldIn.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
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

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return walking_hitbox;
    }
}
