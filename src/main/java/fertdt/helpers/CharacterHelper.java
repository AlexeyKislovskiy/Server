package fertdt.helpers;

import fertdt.entities.Character;

public class CharacterHelper {
    public static int[][] charactersArrayRepresentation(Character[] characters) {
        int[][] ans = new int[characters.length][];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = characters[i].getArrayRepresentation();
        }
        return ans;
    }

}
