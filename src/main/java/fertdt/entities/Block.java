package fertdt.entities;

import fertdt.helpers.EffectHelper;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int id, hp;
    private List<Effect> effects;

    public Block(int id, int hp) {
        this.id = id;
        this.hp = hp;
        effects = new ArrayList<>();
    }

    public int[] getArrayRepresentation() {
        List<Integer> list = new ArrayList<>();
        list.add(id);
        list.add(hp);
        for (Effect effect : effects) {
            EffectHelper.addEffect(list, effect);
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }
}

