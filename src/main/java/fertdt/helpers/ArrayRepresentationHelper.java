package fertdt.helpers;

import fertdt.entities.Block;
import fertdt.entities.Character;

public class ArrayRepresentationHelper {
    public static int[][] charactersArrayRepresentation(Character[] characters) {
        int[][] ans = new int[characters.length][];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = characters[i].getArrayRepresentation();
        }
        return ans;
    }

    public static int[][] blocksArrayRepresentation(Block[] blocks) {
        int[][] ans = new int[blocks.length][];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = blocks[i].getArrayRepresentation();
        }
        return ans;
    }

}
