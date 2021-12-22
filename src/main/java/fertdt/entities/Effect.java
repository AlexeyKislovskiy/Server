package fertdt.entities;

import java.util.Map;

public class Effect{
    public static final int TARGET_CHARACTER = 1;
    public static final int TARGET_BLOCK = 2;

    public static final int TARGET_YOU = 1;
    public static final int TARGET_OPPONENT = 2;

    public static final int VALUABLE_TRUE = 1;
    public static final int VALUABLE_FALSE = 2;

    private static final Map<Integer, String> NAMES = Map.ofEntries(
            Map.entry(1, "AttackUp"),
            Map.entry(2, "AttackUpPercent"),
            Map.entry(3, "AttackDown"),
            Map.entry(4, "AttackDownPercent"),
            Map.entry(5, "DefenceUp"),
            Map.entry(6, "DefenceUpPercent"),
            Map.entry(7, "DefenceDown"),
            Map.entry(8, "DefenceDownPercent")
    );
    private static final Map<Integer, Integer> TARGETS = Map.ofEntries(
            Map.entry(1, TARGET_CHARACTER),
            Map.entry(2, TARGET_CHARACTER),
            Map.entry(3, TARGET_CHARACTER),
            Map.entry(4, TARGET_CHARACTER),
            Map.entry(5, TARGET_BLOCK),
            Map.entry(6, TARGET_BLOCK),
            Map.entry(7, TARGET_BLOCK),
            Map.entry(8, TARGET_BLOCK)
    );
    private static final Map<Integer, Integer> VALUABLE = Map.ofEntries(
            Map.entry(1, VALUABLE_TRUE),
            Map.entry(2, VALUABLE_TRUE),
            Map.entry(3, VALUABLE_TRUE),
            Map.entry(4, VALUABLE_TRUE),
            Map.entry(5, VALUABLE_TRUE),
            Map.entry(6, VALUABLE_TRUE),
            Map.entry(7, VALUABLE_TRUE),
            Map.entry(8, VALUABLE_TRUE)
    );
    private int id, targetEntity, targetPlayer;
    private String name;
    private Integer value, turns, times;

    public Effect(int id, Integer value, Integer turns, Integer times, int targetPlayer) {
        this.id = id;
        this.value = value;
        this.turns = turns;
        this.times = times;
        this.targetPlayer = targetPlayer;
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

    public Integer getTimes() {
        return times;
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
}
