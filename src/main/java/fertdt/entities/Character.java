package fertdt.entities;

import com.google.gson.Gson;
import fertdt.helpers.EffectHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Character {
    public static final int NUM_OF_CHARACTERS = 6;

    public static final int NUM_OF_CHARACTERS_FOR_EACH_PLAYER = 3;

    public static final Map<Integer, Integer> BASIC_DAMAGE = Map.ofEntries(
            Map.entry(1, 30),
            Map.entry(2, 80),
            Map.entry(3, 150),
            Map.entry(4, 70),
            Map.entry(5, 120),
            Map.entry(6, 60)
    );
    //    1)  □■■■□■■■■■    2)  ■■□■■■■■■■    3)  ■■■■■■■■■■    4)  ■■■■■■■■■■    5)  ■■■■■■■■■■    6)  ■■□□■■■■■■
//            ■□■□■■■■■■        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■■□□□■■■■        ■■□□■■■■■■
//            ■■□■■■■■■■        □□□□□□□□□□        ■■■■■■■■■■        ■■■■■■■■■■        ■■■■□■■■■■        ■■□□■■■■■■
//            ■□■□■■■■■■        ■■□■■■■■■■        ■■■□□■■■■■        ■■■■□■■■■■        ■■■■□■■■■■        ■■□□■■■■■■
//            □■■■□■■■■■        ■■□■■■■■■■        ■■■□□■■■■■        ■■■□□□■■■■        ■■■■■■■■■■        ■■□□■■■■■■
//            ■■■■■□■■■■        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■□■■■■■        ■■■■■■■■■■        ■■□□■■■■■■
//            ■■■■■■□■■■        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■□□■■■■■■
//            ■■■■■■■□■■        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■□□■■■■■■
//            ■■■■■■■■□■        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■□□■■■■■■
//            ■■■■■■■■■□        ■■□■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■■■■■■■■■        ■■□□■■■■■■
    public static final Map<Integer, Skill> NORMAL_SKILLS = Map.ofEntries(
            Map.entry(1, new Skill(new Effect[]{new Effect(5, 20, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(6, 20, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(9, null, null, 1, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED)}, 5)),
            Map.entry(2, new Skill(new Effect[]{new Effect(2, 80, null, 3, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(15, null, 2, null, Effect.TARGET_YOU, Effect.TARGET_SELF)}, 3)),
            Map.entry(3, new Skill(new Effect[]{new Effect(1, 30, 5, null, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(2, 30, 5, null, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(3, 20, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED),
                    new Effect(4, 20, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED)}, 7)),
            Map.entry(4, new Skill(new Effect[]{new Effect(7, 15, 5, 5, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(2, 10, 10, null, Effect.TARGET_YOU, Effect.TARGET_LIMITED)}, 2)),
            Map.entry(5, new Skill(new Effect[]{new Effect(3, 10, 2, null, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(1, 40, null, 2, Effect.TARGET_YOU, Effect.TARGET_SELF)}, 4)),
            Map.entry(6, new Skill(new Effect[]{new Effect(9, null, null, 2, Effect.TARGET_YOU, Effect.TARGET_LIMITED),
                    new Effect(10, null, null, 2, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(8, 25, 3, null, Effect.TARGET_YOU, Effect.TARGET_ALL)}, 4))
    );
    public static final Map<Integer, SpecialSkill> SPECIAL_SKILLS = Map.ofEntries(
            Map.entry(1, new SpecialSkill(new Effect[]{new Effect(22, null, null, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(18, null, null, 1, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(12, 150, null, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED),
                    new Effect(13, null, null, 1, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED)}, 30)),
            Map.entry(2, new SpecialSkill(new Effect[]{new Effect(11, null, null, null, Effect.TARGET_YOU, Effect.TARGET_LIMITED),
                    new Effect(1, 50, null, 3, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(10, null, null, 2, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(15, null, 2, null, Effect.TARGET_YOU, Effect.TARGET_SELF)}, 60)),
            Map.entry(3, new SpecialSkill(new Effect[]{new Effect(17, null, null, 2, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(19, null, 3, 1, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED),
                    new Effect(21, null, null, null, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(23, null, null, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED)}, 50)),
            Map.entry(4, new SpecialSkill(new Effect[]{new Effect(8, 10, 5, 5, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(16, null, 2, 2, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(20, null, null, null, Effect.TARGET_YOU, Effect.TARGET_LIMITED),
                    new Effect(1, 10, 10, null, Effect.TARGET_YOU, Effect.TARGET_ALL)}, 80)),
            Map.entry(5, new SpecialSkill(new Effect[]{new Effect(14, null, 1, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(15, null, 1, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED),
                    new Effect(12, 10, null, null, Effect.TARGET_YOU, Effect.TARGET_ALL),
                    new Effect(3, 100, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED)}, 45)),
            Map.entry(6, new SpecialSkill(new Effect[]{new Effect(23, null, null, null, Effect.TARGET_YOU, Effect.TARGET_SELF),
                    new Effect(17, null, null, 1, Effect.TARGET_OPPONENT, Effect.TARGET_LIMITED),
                    new Effect(23, null, null, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL),
                    new Effect(6, 25, 3, null, Effect.TARGET_OPPONENT, Effect.TARGET_ALL)}, 55))
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
        if (!(o instanceof Character character)) return false;
        return id == character.id && basicDamage == character.basicDamage && madeMove == character.madeMove && Objects.equals(normalSkill, character.normalSkill) && Objects.equals(specialSkill, character.specialSkill) && Objects.equals(effects, character.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basicDamage, normalSkill, specialSkill, effects, madeMove);
    }
}
