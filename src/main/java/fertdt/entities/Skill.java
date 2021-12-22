package fertdt.entities;

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

    public void updateCooldown() {
        this.currentCooldown = basicCooldown;
    }
}
