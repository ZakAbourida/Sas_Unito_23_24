package businesslogic.recipe;

import java.util.List;

public class Recipe extends ItemBook {
    private List<Preparation> preparations;

    // Getter e setter per preparations
    public List<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(List<Preparation> preparations) {
        this.preparations = preparations;
    }

    public String getName() {
        return null;
    }
}
