package fertdt.entities;

import java.util.Arrays;
import java.util.Objects;

public class SpecialSkill {
    private Effect[] effects;
    private int cost;

    public SpecialSkill(Effect[] effects, int cost) {
        this.effects = effects;
        this.cost = cost;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialSkill that)) return false;
        return cost == that.cost && Arrays.equals(effects, that.effects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cost);
        result = 31 * result + Arrays.hashCode(effects);
        return result;
    }
}
