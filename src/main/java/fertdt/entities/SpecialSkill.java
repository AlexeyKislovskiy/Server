package fertdt.entities;

public class SpecialSkill{
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
}
