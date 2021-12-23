package fertdt.entities;

import java.util.*;

public class Field {
    private int width, height;
    private List<List<Integer>> blockStateInitial;
    private int[][] x, y, g;
    private Block[] blocks;

    private Field(int width, int height, List<List<Integer>> blockStateInitial, int[][] x, int[][] y, int[][] g) {
        this.width = width;
        this.height = height;
        this.blockStateInitial = blockStateInitial;
        blocks = new Block[blockStateInitial.size()];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(blockStateInitial.get(i).get(0), blockStateInitial.get(i).get(1));
        }
        this.x = x;
        this.y = y;
        this.g = g;
    }

    public static Field generateField(int width, int height) {
        int[][] x = new int[width * height][2], y = new int[width * height][2], g = new int[width][height];
        int size = width * height;
        int numOfBlock = (int) (Math.random() * (size / 3 - size / 10) + size / 10);
        List<List<Integer>> blockState = new ArrayList<>();
        for (int i = 0; i < numOfBlock; i++) {
            List<Integer> block = new ArrayList<>();
            block.add(i + 1);
            int xp = (int) (Math.random() * 450 + 50);
            block.add(xp);
            blockState.add(block);
        }
        Set<Integer> initialBlockTiles = new HashSet<>();
        while (initialBlockTiles.size() < numOfBlock) {
            int el = (int) (Math.random() * size);
            initialBlockTiles.add(el);
        }
        int counter = 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (initialBlockTiles.contains(i * width + j)) {
                    g[i][j] = counter;
                    counter++;
                }
            }
        }
        while (counter <= size) {
            int el = (int) (Math.random() * size);
            if (g[el / width][el % height] == 0) {
                List<Integer> list = new ArrayList<>();
                if (el / width != 0 && g[el / width - 1][el % height] != 0) list.add(g[el / width - 1][el % height]);
                if (el % height != 0 && g[el / width][el % height - 1] != 0) list.add(g[el / width][el % height - 1]);
                if (el / width != width - 1 && g[el / width + 1][el % height] != 0)
                    list.add(g[el / width + 1][el % height]);
                if (el % height != height - 1 && g[el / width][el % height + 1] != 0)
                    list.add(g[el / width][el % height + 1]);
                if (list.isEmpty()) continue;
                g[el / width][el % height] = list.get((int) (Math.random() * list.size()));
                counter++;
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                x[i * width + j][0] = i;
                x[i * width + j][1] = g[i][j];
                y[i * width + j][0] = j;
                y[i * width + j][1] = g[i][j];
            }
        }
        return new Field(width, height, blockState, x, y, g);
    }

    public void regenerateField() {
        blocks = new Block[blockStateInitial.size()];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(blockStateInitial.get(i).get(0), blockStateInitial.get(i).get(1));
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public List<List<Integer>> getBlockStateInitial() {
        return blockStateInitial;
    }

    public int[][] getX() {
        return x;
    }

    public int[][] getY() {
        return y;
    }

    public int[][] getG() {
        return g;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return width == field.width && height == field.height && Objects.equals(blockStateInitial, field.blockStateInitial) && Arrays.deepEquals(x, field.x) && Arrays.deepEquals(y, field.y) && Arrays.deepEquals(g, field.g) && Arrays.equals(blocks, field.blocks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height, blockStateInitial);
        result = 31 * result + Arrays.deepHashCode(x);
        result = 31 * result + Arrays.deepHashCode(y);
        result = 31 * result + Arrays.deepHashCode(g);
        result = (31 * result) + Arrays.hashCode(blocks);
        return result;
    }
}
