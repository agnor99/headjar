package com.glebtik.headjar.util;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.*;

public class StructureUtils {
    private StructureUtils() {}

    public static class Structure {
        char anchor = ' ';
        List<StructureLayer> layers = new ArrayList<>();
        Map<Character, Block> predicates = new HashMap<>();
        int width;
        int depth;
        int height;

        public Structure(int width, int depth, int height) {
            this.width = width;
            this.depth = depth;
            this.height = height;
        }
        public StructureLayer appendLayer() {
            return new StructureLayer(this);
        }
        private Structure append(StructureLayer layer) {
            if(layer.lines.size() != depth) {
                throw new IllegalArgumentException("the amount of lines in layer " + layers.size() + " does not match the structure defined depth of " + depth);
            }
            layers.add(layer);
            return this;
        }
        public Structure withAnchor(char anchor) {
            if(this.anchor != ' ') {
                throw new IllegalArgumentException("anchor already defined as '" + anchor + "'");
            }
            int numAnchors = 0;
            for(StructureLayer layer: layers) {
                for(StructureLine line: layer.lines) {
                    for(char character: line.blocks) {
                        if(character == anchor) {
                            numAnchors ++;
                        }
                    }
                }
            }
            if(numAnchors == 0) {
                throw new IllegalArgumentException("no character found matching anchor '" + anchor + "'");
            }
            if(numAnchors > 1) {
                throw new IllegalArgumentException("to many characters found matching anchor + '" + anchor + "'. Expected: 1, Found: " + numAnchors);
            }
            this.anchor = anchor;
            return this;
        }
        public StructurePredicate.StructureMatchData match(WorldServer world, BlockPos pos) {
            validate();
            return new StructurePredicate(world, pos).test(this);
        }
        private void validate() {
            if(layers.size() != height) {
                throw new IllegalArgumentException("the amount of layers does not match the structure defined height of " + height);
            }
            if(anchor == ' ') {
                throw new IllegalArgumentException("anchor is not defined");
            }

            List<Character> usedCharacters = new ArrayList<>();
            for(StructureLayer layer: layers) {
                for(StructureLine lines: layer.lines) {
                    for(Character character: lines.blocks) {
                        if(!usedCharacters.contains(character)) {
                            usedCharacters.add(character);
                        }
                    }
                }
            }
            for(Character character: predicates.keySet()) {
                usedCharacters.remove(character);
            }
            if(!usedCharacters.isEmpty()) {
                throw new IllegalArgumentException("The following keys didn't get resolved: " +  usedCharacters.toString());
            }
        }
        public Structure withBlockFor(Character character, Block block) {
            if(predicates.containsKey(character)) {
                throw new IllegalArgumentException("character already used as predicate");
            }
            predicates.put(character, block);
            return this;
        }
        private char[][][] createCharArray() {
            char[][][] blocks = new char[depth][height][width];
            for(int y = 0; y < height; y++) {
                StructureLayer layer = layers.get(y);
                for(int x = 0; x < layer.lines.size(); x++) {
                    StructureLine line = layer.lines.get(x);
                    blocks[x][y] = Arrays.copyOf(line.blocks, width);
                }
            }
            return blocks;
        }
    }

    public static class StructureLayer {
        List<StructureLine> lines = new ArrayList<>();
        Structure structure;
        private StructureLayer(Structure structure) {
            this.structure = structure;
        }
        public StructureLayer append(String line) {
            return append(new StructureLine(line));
        }
        private StructureLayer append(StructureLine line) {
            if(structure.width != line.blocks.length) {
                throw new IllegalArgumentException("the amount of blocks in line " + Arrays.toString(line.blocks) + "does not match the structure defined width of " + structure.width);
            }
            lines.add(line);
            return this;
        }
        public Structure build() {
            return structure.append(this);
        }
    }
    public static class StructureLine {
        char[] blocks;
        private StructureLine(String blocks) {
            this.blocks = blocks.toCharArray();
        }
    }

    public static class StructurePredicate {
        WorldServer world;
        BlockPos pos;
        Structure structure;

        private StructurePredicate(WorldServer world, BlockPos pos) {
            this.world = world;
            this.pos = pos;
        }

        public StructureMatchData test(Structure structure) {
            this.structure = structure;
            char[][][] blocks = structure.createCharArray();
            for(int i = 0; i < 2; i++) {
                StructureMatchData data = test(blocks);
                if(data.correct) {
                    data.facing = EnumFacing.getHorizontal(1+i);
                    return data;
                }
                data = test(mirrorCharArray(blocks));
                if(data.correct) {
                    data.facing = EnumFacing.getHorizontal(3+i);
                    return data;
                }

                blocks = rotateCharArray(blocks);
            }
            return new StructureMatchData();
        }
        private StructureMatchData test(char[][][] blocks) {
            StructureMatchData match = new StructureMatchData();
            BlockPos anchorInCharArray = offsetAnchorInCharArray(blocks);
            for(int x = 0; x < blocks.length; x++) {
                for(int y = 0; y < blocks[0].length; y++) {
                    for(int z = 0; z < blocks[0][0].length; z++) {
                        BlockPos posToCheck = new BlockPos(x,y,z);
                        posToCheck = posToCheck.add(anchorInCharArray).add(this.pos);
                        if(world.getBlockState(posToCheck).getBlock() != structure.predicates.get(blocks[x][y][z])) {
                            return match;
                        }
                    }
                }
            }
            match.correct = true;
            match.corner = pos.add(anchorInCharArray);
            match.corner2 = pos.add(anchorInCharArray).add(blocks.length-1, blocks[0].length-1, blocks[0][0].length-1);
            return match;
        }
        private BlockPos offsetAnchorInCharArray(char[][][] blocks) {
            for(int x = 0; x < blocks.length; x++) {
                for(int y = 0; y < blocks[0].length; y++) {
                    for(int z = 0; z < blocks[0][0].length; z++) {
                        if(blocks[x][y][z] == structure.anchor) {
                            return new BlockPos(-x,-y,-z);
                        }
                    }
                }
            }
            return new BlockPos(0,0,0); // should never get called, but I don't want any runtime exceptions while checking the structure
        }
        private char[][][] rotateCharArray(char[][][] blocks) {
            char[][][] newBlocks = new char[blocks[0][0].length][blocks[0].length][blocks.length];
            for(int x = 0; x < blocks.length; x++) {
                for(int y = 0; y < blocks[0].length; y++) {
                    for(int z = 0; z < blocks[0][0].length; z++) {
                        newBlocks[z][y][x] = blocks[x][y][z];
                    }
                }
            }
            return newBlocks;
        }
        private char[][][] mirrorCharArray(char[][][] blocks) {
            char[][][] newBlocks = new char[blocks.length][blocks[0].length][blocks[0][0].length];
            for(int x = 0; x < blocks.length; x++) {
                for(int y = 0; y < blocks[0].length; y++) {
                    for(int z = 0; z < blocks[0][0].length; z++) {
                        newBlocks[blocks.length - x -1][y][blocks[0][0].length - z - 1] = blocks[x][y][z];
                    }
                }
            }
            return newBlocks;
        }
        public static class StructureMatchData {
            public boolean correct;
            public BlockPos corner;
            public BlockPos corner2; //including
            public EnumFacing facing;
        }
    }
}
