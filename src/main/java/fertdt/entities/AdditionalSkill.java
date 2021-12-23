package fertdt.entities;

import java.util.Objects;

public class AdditionalSkill {
    public static final int NUM_OF_PASSIVE_SKILLS = 3;
    private int id;

    public AdditionalSkill(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalSkill that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
