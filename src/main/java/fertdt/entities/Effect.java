package fertdt.entities;

import java.util.Map;
import java.util.Objects;

public class Effect {
    public static final int TARGET_CHARACTER = 1;
    public static final int TARGET_BLOCK = 2;

    public static final int TARGET_YOU = 1;
    public static final int TARGET_OPPONENT = 2;

    public static final int TARGET_LIMITED = 1;
    public static final int TARGET_ALL = 2;
    public static final int TARGET_SELF = 3;

    public static final int VALUABLE_TRUE = 1;
    public static final int VALUABLE_FALSE = 2;

    public static final int BUFF = 1;
    public static final int DEBUFF = 2;

    public static final Map<Integer, String> NAMES = Map.ofEntries(
            Map.entry(1, "AttackUp"),
            Map.entry(2, "AttackUpPercent"),
            Map.entry(3, "AttackDown"),
            Map.entry(4, "AttackDownPercent"),
            Map.entry(5, "DefenceUp"),
            Map.entry(6, "DefenceUpPercent"),
            Map.entry(7, "DefenceDown"),
            Map.entry(8, "DefenceDownPercent"),
            Map.entry(9, "Invisible"),
            Map.entry(10, "IgnoreInvisible"),
            Map.entry(11, "InstantKill"),
            Map.entry(12, "Heal"),
            Map.entry(13, "ReverseDamage"),
            Map.entry(14, "BlockSkillUse"),
            Map.entry(15, "BlockMove"),
            Map.entry(16, "DebuffResistBlock"),
            Map.entry(17, "DebuffResistCharacter"),
            Map.entry(18, "BuffResistBlock"),
            Map.entry(19, "BuffResistCharacter"),
            Map.entry(20, "DebuffClearBlock"),
            Map.entry(21, "DebuffClearCharacter"),
            Map.entry(22, "BuffClearBlock"),
            Map.entry(23, "BuffClearCharacter")
    );
    public static final Map<Integer, Integer> TARGETS = Map.ofEntries(
            Map.entry(1, TARGET_CHARACTER),
            Map.entry(2, TARGET_CHARACTER),
            Map.entry(3, TARGET_CHARACTER),
            Map.entry(4, TARGET_CHARACTER),
            Map.entry(5, TARGET_BLOCK),
            Map.entry(6, TARGET_BLOCK),
            Map.entry(7, TARGET_BLOCK),
            Map.entry(8, TARGET_BLOCK),
            Map.entry(9, TARGET_BLOCK),
            Map.entry(10, TARGET_CHARACTER),
            Map.entry(11, TARGET_BLOCK),
            Map.entry(12, TARGET_BLOCK),
            Map.entry(13, TARGET_BLOCK),
            Map.entry(14, TARGET_CHARACTER),
            Map.entry(15, TARGET_CHARACTER),
            Map.entry(16, TARGET_BLOCK),
            Map.entry(17, TARGET_CHARACTER),
            Map.entry(18, TARGET_BLOCK),
            Map.entry(19, TARGET_CHARACTER),
            Map.entry(20, TARGET_BLOCK),
            Map.entry(21, TARGET_CHARACTER),
            Map.entry(22, TARGET_BLOCK),
            Map.entry(23, TARGET_CHARACTER)
    );
    public static final Map<Integer, Integer> VALUABLE = Map.ofEntries(
            Map.entry(1, VALUABLE_TRUE),
            Map.entry(2, VALUABLE_TRUE),
            Map.entry(3, VALUABLE_TRUE),
            Map.entry(4, VALUABLE_TRUE),
            Map.entry(5, VALUABLE_TRUE),
            Map.entry(6, VALUABLE_TRUE),
            Map.entry(7, VALUABLE_TRUE),
            Map.entry(8, VALUABLE_TRUE),
            Map.entry(9, VALUABLE_FALSE),
            Map.entry(10, VALUABLE_FALSE),
            Map.entry(11, VALUABLE_FALSE),
            Map.entry(12, VALUABLE_TRUE),
            Map.entry(13, VALUABLE_FALSE),
            Map.entry(14, VALUABLE_FALSE),
            Map.entry(15, VALUABLE_FALSE),
            Map.entry(16, VALUABLE_FALSE),
            Map.entry(17, VALUABLE_FALSE),
            Map.entry(18, VALUABLE_FALSE),
            Map.entry(19, VALUABLE_FALSE),
            Map.entry(20, VALUABLE_FALSE),
            Map.entry(21, VALUABLE_FALSE),
            Map.entry(22, VALUABLE_FALSE),
            Map.entry(23, VALUABLE_FALSE)
    );
    public static final Map<Integer, Integer> EFFECT_STATUS = Map.ofEntries(
            Map.entry(1, BUFF),
            Map.entry(2, BUFF),
            Map.entry(3, DEBUFF),
            Map.entry(4, DEBUFF),
            Map.entry(5, DEBUFF),
            Map.entry(6, DEBUFF),
            Map.entry(7, BUFF),
            Map.entry(8, BUFF),
            Map.entry(9, DEBUFF),
            Map.entry(10, BUFF),
            Map.entry(11, BUFF),
            Map.entry(12, DEBUFF),
            Map.entry(13, DEBUFF),
            Map.entry(14, DEBUFF),
            Map.entry(15, DEBUFF),
            Map.entry(16, BUFF),
            Map.entry(17, BUFF),
            Map.entry(18, DEBUFF),
            Map.entry(19, DEBUFF),
            Map.entry(20, BUFF),
            Map.entry(21, BUFF),
            Map.entry(22, DEBUFF),
            Map.entry(23, DEBUFF)
    );
    private int id, targetEntity, targetPlayer, targetQuantity, player, effectStatus;
    private String name;
    private Integer value, turns, times;

    public Effect(int id, Integer value, Integer turns, Integer times, int targetPlayer, int targetQuantity) {
        this.id = id;
        this.value = value;
        this.turns = turns;
        this.times = times;
        this.targetPlayer = targetPlayer;
        this.targetQuantity = targetQuantity;
        this.targetEntity = TARGETS.get(id);
        this.name = NAMES.get(id);
        this.effectStatus = EFFECT_STATUS.get(id);
    }

    public int getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getTurns() {
        return turns;
    }

    public void setTurns(Integer turns) {
        this.turns = turns;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public int getTargetEntity() {
        return targetEntity;
    }

    public int getTargetPlayer() {
        return targetPlayer;
    }

    public int getTargetQuantity() {
        return targetQuantity;
    }

    public int getEffectStatus() {
        return effectStatus;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Effect effect)) return false;
        return id == effect.id && targetEntity == effect.targetEntity && targetPlayer == effect.targetPlayer && targetQuantity == effect.targetQuantity && player == effect.player && Objects.equals(name, effect.name) && Objects.equals(value, effect.value) && Objects.equals(turns, effect.turns) && Objects.equals(times, effect.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targetEntity, targetPlayer, targetQuantity, player, name, value, turns, times);
    }
}
