package fertdt.entities;

import com.google.gson.Gson;
import fertdt.helpers.EffectHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Character {
    public static final int NUM_OF_CHARACTERS = 3;
    public static final Map<Integer, Integer> BASIC_DAMAGE = Map.ofEntries(
            Map.entry(1, 30),
            Map.entry(2, 40),
            Map.entry(3, 50)
    );
    public static final Map<Integer, Skill> NORMAL_SKILLS = Map.ofEntries(
            Map.entry(1, new Skill(new Effect[]{new Effect(7, 10, 5, 0, Effect.TARGET_YOU)}, 5)),
            Map.entry(2, new Skill(new Effect[]{new Effect(1, 10, 5, 0, Effect.TARGET_YOU)}, 5)),
            Map.entry(3, new Skill(new Effect[]{new Effect(1, 10, 5, 0, Effect.TARGET_YOU)}, 5))
    );
    public static final Map<Integer, SpecialSkill> SPECIAL_SKILLS = Map.ofEntries(
            Map.entry(1, new SpecialSkill(new Effect[]{new Effect(1, 20, 5, 0, Effect.TARGET_YOU)}, 25)),
            Map.entry(2, new SpecialSkill(new Effect[]{new Effect(1, 20, 5, 0, Effect.TARGET_YOU)}, 25)),
            Map.entry(3, new SpecialSkill(new Effect[]{new Effect(1, 20, 5, 0, Effect.TARGET_YOU)}, 25))
    );
    private int id, basicDamage;
    private Skill normalSkill;
    private SpecialSkill specialSkill;
    private List<Effect> effects;
    private boolean madeMove;

    public Character(int id) {
        this.id = id;
        this.basicDamage = BASIC_DAMAGE.get(id);
        Gson gson = new Gson();
        normalSkill = gson.fromJson(gson.toJson(NORMAL_SKILLS.get(id)), Skill.class);
        specialSkill = gson.fromJson(gson.toJson(SPECIAL_SKILLS.get(id)), SpecialSkill.class);
        effects = new ArrayList<>();
        madeMove = false;
    }


    public int[] getArrayRepresentation() {
        List<Integer> list = new ArrayList<>();
        list.add(normalSkill.getCurrentCooldown());
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

    public int getBasicDamage() {
        return basicDamage;
    }

    public Skill getNormalSkill() {
        return normalSkill;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public boolean isMadeMove() {
        return madeMove;
    }

    public void setMadeMove(boolean madeMove) {
        this.madeMove = madeMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return id == character.id && basicDamage == character.basicDamage && madeMove == character.madeMove && Objects.equals(normalSkill, character.normalSkill) && Objects.equals(specialSkill, character.specialSkill) && Objects.equals(effects, character.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basicDamage, normalSkill, specialSkill, effects, madeMove);
    }
}
