package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public abstract class Heroes {
    private int mana;
    private String description;
    private String name;
    private int health;
    private ArrayList<String> colors = new ArrayList<>();
    private int hasAttacked;


    public Heroes(final int mana, final String description, final ArrayList<String> colors,
                  final String name, final int health) {
        this.mana = mana;
        this.description = description;
        this.colors.addAll(colors);
        this.name = name;
        this.health = health;
    }

    /** Returns hero's mana */
    public int getMana() {
        return mana;
    }

    /** Returns hero's description */
    public String getDescription() {
        return description;
    }

    /** Returns hero's colors */
    public ArrayList<String> getColors() {
        return colors;
    }

    /** Returns hero's name */
    public String getName() {
        return name;
    }

    /** Returns hero's health */
    public int getHealth() {
        return health;
    }

    /** Checks to see if the hero has already attacked the current round */
    @JsonIgnore
    public int getHasAttacked() {
        return hasAttacked;
    }

    /** Sets hero's mana */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /** Sets hero's description */
    public void setDescription(final String description) {
        this.description = description;
    }

    /** Sets hero's name */
    public void setName(final String name) {
        this.name = name;
    }

    /** Sets hero's health */
    public void setHealth(final int health) {
        this.health = health;
    }

    /** Sets hero's colors */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /** Sets the HasAttacked flag after each hero attack */
    @JsonIgnore
    public void setHasAttacked(final int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /** Heroes' abilities implementation */
    public abstract int heroAbility(ArrayList<ArrayList<MinionCards>> table, int playerTurn,
                                    int row, ActionsInput action, ObjectMapper objectMapper,
                                    ArrayNode output, Heroes hero);

    /** Error message for Lord Royce and Empress Thorina */
    public void lordAndEmpressError(final ActionsInput action, final ObjectMapper objectMapper,
                                    final ArrayNode output) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", action.getAffectedRow());
        node.put("error", "Selected row does not belong to the enemy.");
        output.add(node);
    }

    /** Error message for General Kocioraw and King Mudface */
    public void generalAndKingError(final ActionsInput action, final ObjectMapper objectMapper,
                                    final ArrayNode output) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", action.getAffectedRow());
        node.put("error", "Selected row does not belong to the current player.");
        output.add(node);
    }

    /** Checks for error in the usage of Lord Royce and Empress Thorina's abilities */
    public boolean checkForLordAndEmpressError(final ActionsInput action, final ObjectMapper
            objectMapper, final ArrayNode output, final int playerTurn, final int row) {
        final int third = 3;
        if (playerTurn == 1 && (row != 0 && row != 1)) {
            lordAndEmpressError(action, objectMapper, output);
            return true;
        }
        if (playerTurn == 2 && (row != 2 && row != third)) {
            lordAndEmpressError(action, objectMapper, output);
            return true;
        }
        return false;
    }

    /** Checks for error in the usage of General Kocioraw and King Mudface's abilities */
    public boolean checkForGeneralAndKingError(final ActionsInput action, final ObjectMapper
            objectMapper, final ArrayNode output, final int playerTurn, final int row) {
        final int third = 3;
        if (playerTurn == 1 && (row != 2 && row != third)) {
            generalAndKingError(action, objectMapper, output);
            return true;
        }
        if (playerTurn == 2 && (row != 0 && row != 1)) {
            generalAndKingError(action, objectMapper, output);
            return true;
        }
        return false;
    }
}
