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

    public static final int VALUABLE_TRUE = 1;
    public static final int VALUABLE_FALSE = 2;

    public static final Map<Integer, String> NAMES = Map.ofEntries(
            Map.entry(1, "AttackUp"),
            Map.entry(2, "AttackUpPercent"),
            Map.entry(3, "AttackDown"),
            Map.entry(4, "AttackDownPercent"),
            Map.entry(5, "DefenceUp"),
            Map.entry(6, "DefenceUpPercent"),
            Map.entry(7, "DefenceDown"),
            Map.entry(8, "DefenceDownPercent")
    );
    public static final Map<Integer, Integer> TARGETS = Map.ofEntries(
            Map.entry(1, TARGET_CHARACTER),
            Map.entry(2, TARGET_CHARACTER),
            Map.entry(3, TARGET_CHARACTER),
            Map.entry(4, TARGET_CHARACTER),
            Map.entry(5, TARGET_BLOCK),
            Map.entry(6, TARGET_BLOCK),
            Map.entry(7, TARGET_BLOCK),
            Map.entry(8, TARGET_BLOCK)
    );
    public static final Map<Integer, Integer> VALUABLE = Map.ofEntries(
            Map.entry(1, VALUABLE_TRUE),
            Map.entry(2, VALUABLE_TRUE),
            Map.entry(3, VALUABLE_TRUE),
            Map.entry(4, VALUABLE_TRUE),
            Map.entry(5, VALUABLE_TRUE),
            Map.entry(6, VALUABLE_TRUE),
            Map.entry(7, VALUABLE_TRUE),
            Map.entry(8, VALUABLE_TRUE)
    );
    private int id, targetEntity, targetPlayer, targetQuantity, player;
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
