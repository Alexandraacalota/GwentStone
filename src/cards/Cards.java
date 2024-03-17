package cards;

import java.util.ArrayList;

public abstract class Cards {
    private final int mana;
    private final String description;
    private ArrayList<String> colors = new ArrayList<>();
    private final String name;

    public Cards(final int mana, final String description, final String name) {

        this.mana = mana;
        this.description = description;
        this.name = name;
    }
    public Cards(final int mana, final String description, final ArrayList<String> colors,
                 final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    /** Returns card's mana */
    public int getMana() {
        return mana;
    }

    /** Returns card's description */
    public String getDescription() {
        return description;
    }

    /** Returns card's colors */
    public ArrayList<String> getColors() {
        return colors;
    }

    /** Returns card's name */
    public String getName() {
        return name;
    }

    /** Checks if the card is a minion or an environment one */
    public abstract int isMinionCard();

}
