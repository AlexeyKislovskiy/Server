package fertdt;

import fertdt.entities.Field;

public class TestMain {
    public static void main(String[] args) {
        Field field = Field.generateField(10, 10);
        System.out.println(field.getBlockState().length);
        int[][] g = field.getG();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                System.out.print(g[i][j] + " ");
            }
            System.out.println();
        }
    }
}
