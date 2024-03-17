package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class MinionCards extends Cards {
    private int attackDamage;
    private int health;
    private int frozen;
    private int alreadyAttacked;

    public MinionCards(final int mana, final String description, final ArrayList<String> colors,
                       final String name, final int attackDamage, final int health) {
        super(mana, description, colors, name);
        this.attackDamage = attackDamage;
        this.health = health;
    }

    /** The card is a minion */
    @Override
    public int isMinionCard() {
        return 1;
    }

    /** Simple minion cards have no ability */
    public int hasSpecialAbility() {
        return 0;
    }

    /** Returns minion's attack damage */
    public int getAttackDamage() {
        return attackDamage;
    }

    /** Returns minion's health */
    public int getHealth() {
        return health;
    }

    /** Sets minion's attack damage */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /** Sets minion's health */
    public void setHealth(final int health) {
        this.health = health;
    }

    /** Checks to see whether the minion is frozen */
    @JsonIgnore
    public int getFrozen() {
        return frozen;
    }

    /** Checks to see whether the minion has already attacked the current round */
    @JsonIgnore
    public int getAlreadyAttacked() {
        return alreadyAttacked;
    }

    /** Sets the frozen flag */
    @JsonIgnore
    public void setFrozen(final int frozen) {
        this.frozen = frozen;
    }

    /** Sets the alreadyAttacked flag */
    @JsonIgnore
    public void setAlreadyAttacked(final int alreadyAttacked) {
        this.alreadyAttacked = alreadyAttacked;
    }

    /** Helper method for error message print */
    public ObjectNode errorMessage(final ActionsInput action, final ObjectMapper objectMapper) {
        return createNode(action, objectMapper);
    }

    /** Prints error message's card coordinates */
    public static void writeError(final ActionsInput action, final ObjectNode node) {
        Coordinates cardAttackerCoordinates = new Coordinates();
        cardAttackerCoordinates.setX(action.getCardAttacker().getX());
        cardAttackerCoordinates.setY(action.getCardAttacker().getY());
        node.putPOJO("cardAttacker", cardAttackerCoordinates);
        Coordinates cardAttackedCoordinates = new Coordinates();
        cardAttackedCoordinates.setX(action.getCardAttacked().getX());
        cardAttackedCoordinates.setY(action.getCardAttacked().getY());
        node.putPOJO("cardAttacked", cardAttackedCoordinates);
    }

    /** Method that creates a node with the data about to be printed */
    public static ObjectNode createNode(final ActionsInput action, final ObjectMapper
            objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "cardUsesAttack");
        writeError(action, node);
        return node;
    }

    /** Checks to see if the card selected for attack is a tank
     * and if not, checks to see if there is another tank on the opponent's table */
    public boolean lookForTankForAttack(final ActionsInput action, final
    ArrayList<ArrayList<MinionCards>> table, final int playerTurn, final ObjectMapper objectMapper,
                                        final ArrayNode output) {
        if (action.getCardAttacked().getY() < table.get(action.getCardAttacked().getX()).size()) {
            if ((table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                    getName().equals("Goliath")) || (table.get(action.getCardAttacked().getX()).
                    get(action.getCardAttacked().getY()).getName().equals("Warden"))) {
                return false;
            } else {
                if (playerTurn == 1) {
                    for (int i = 0; i < table.get(1).size(); i++) {
                        if (table.get(1).get(i).notTypeTankError(table.get(1).get(i), action,
                                objectMapper, output)) {
                            return true;
                        }
                    }
                } else if (playerTurn == 2) {
                    for (int i = 0; i < table.get(2).size(); i++) {
                        if (table.get(2).get(i).notTypeTankError(table.get(2).get(i), action,
                                objectMapper, output)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /** Error message in case there is a tank on the table, and it hasn't been selected */
    public boolean notTypeTankError(final MinionCards card, final ActionsInput action, final
    ObjectMapper objectMapper, final ArrayNode output) {
        if ((card.getName().equals("Goliath")) || (card.getName().equals("Warden"))) {
            ObjectNode node = errorMessage(action, objectMapper);
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
            return true;
        }
        return false;
    }
}
