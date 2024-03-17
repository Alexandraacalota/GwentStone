package specialabilitiesminioncards;

import cards.MinionCards;
import cards.SpecialAbilitiesMinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Miraj extends SpecialAbilitiesMinionCards {

    public Miraj(final int mana, final String description, final ArrayList<String> colors,
                 final String name, final int attackDamage, final int health) {
        super(mana, description, colors, name, attackDamage, health);
    }

    /** Implementation of Miraj's ability */
    @Override
    public void ability(final ActionsInput action, final ArrayList<ArrayList<MinionCards>> table,
                        final ObjectMapper objectMapper, final ArrayNode output,
                        final int playerTurn) {
        final int three = 3;
        // Check to see if the card selected to be attacked belongs to the enemy
        if ((playerTurn == 1 && ((action.getCardAttacked().getX() == 0) || (action.
                getCardAttacked().getX() == 1))) || (playerTurn == 2 && ((action.getCardAttacked().
                getX() == 2) || (action.getCardAttacked().getX() == three)))) {
            // Check to see if the selected card is a tank
            if ((table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                    getName().equals("Goliath")) || (table.get(action.getCardAttacked().getX()).
                    get(action.getCardAttacked().getY()).getName().equals("Warden"))) {
                mirajAbility(action, table);
            } else {
                // Check to see if a tank of the enemy is found on the table
                if (mirajAndRipperError(action, objectMapper, output, table)) {
                    return;
                }
                mirajAbility(action, table);
            }
        } else {
            ObjectNode node = errorMessage(action, objectMapper);
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
        }
    }

    /** Swaps his health with an enemy minion's one */
    public void mirajAbility(final ActionsInput action, final ArrayList<ArrayList<MinionCards>>
            table) {
        int health = table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().
                getY()).getHealth();
        table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                setHealth(table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().
                                getY()).getHealth());
        table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                setHealth(health);
        table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                setAlreadyAttacked(1);
    }
}
