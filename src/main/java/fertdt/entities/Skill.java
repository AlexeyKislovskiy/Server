package fertdt.entities;

import java.util.Arrays;
import java.util.Objects;

public class Skill{
    private Effect[] effects;
    private int basicCooldown, currentCooldown;

    public Skill(Effect[] effects, int basicCooldown) {
        this.effects = effects;
        this.basicCooldown = basicCooldown;
        this.currentCooldown = 0;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public int getBasicCooldown() {
        return basicCooldown;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    public void updateCooldown() {
        this.currentCooldown = basicCooldown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill skill)) return false;
        return basicCooldown == skill.basicCooldown && currentCooldown == skill.currentCooldown && Arrays.equals(effects, skill.effects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(basicCooldown, currentCooldown);
        result = 31 * result + Arrays.hashCode(effects);
        return result;
    }
}
