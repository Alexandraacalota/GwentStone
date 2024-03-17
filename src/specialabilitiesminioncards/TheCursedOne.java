package specialabilitiesminioncards;

import cards.MinionCards;
import cards.SpecialAbilitiesMinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class TheCursedOne extends SpecialAbilitiesMinionCards {

    public TheCursedOne(final int mana, final String description, final ArrayList<String> colors,
                        final String name, final int attackDamage, final int health) {
        super(mana, description, colors, name, attackDamage, health);
    }

    /** Implementation of The Cursed One's ability */
    @Override
    public void ability(final ActionsInput action, final ArrayList<ArrayList<MinionCards>> table,
                        final ObjectMapper objectMapper, final ArrayNode output,
                        final int playerTurn) {
        final int three = 3;
        // Check to see if the card selected to be attacked belongs to the enemy
        if ((playerTurn == 1 && ((action.getCardAttacked().getX() == 0) || (action.
                getCardAttacked().getX() == 1))) || (playerTurn == 2 && ((action.
                getCardAttacked().getX() == 2) || (action.getCardAttacked().getX() == three)))) {
            // Check to see if the selected card is a tank
            if ((table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                    getName().equals("Goliath")) || (table.get(action.getCardAttacked().getX()).
                    get(action.getCardAttacked().getY()).getName().equals("Warden"))) {
                theCursedOneAbility(action, table);
            } else {
                // Check to see if a tank of the enemy is found on the table
                if (action.getCardAttacker().getX() == three) {
                    for (int i = 0; i < table.get(1).size(); i++) {
                        if (notTypeTankError(table.get(1).get(i), action, objectMapper, output)) {
                            return;
                        }
                    }
                } else if (action.getCardAttacker().getX() == 0) {
                    for (int i = 0; i < table.get(2).size(); i++) {
                        if (notTypeTankError(table.get(2).get(i), action, objectMapper, output)) {
                            return;
                        }
                    }
                }
                theCursedOneAbility(action, table);
            }
        } else {
            ObjectNode node = errorMessage(action, objectMapper);
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
        }
    }

    /** Swaps an enemy minion's health with his attack damage */
    public void theCursedOneAbility(final ActionsInput action, final
    ArrayList<ArrayList<MinionCards>> table) {
        int health = table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().
                getY()).getHealth();
        table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                setHealth(table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().
                        getY()).getAttackDamage());
        table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                setAttackDamage(health);
        if (table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                getHealth() <= 0) {
            if (action.getCardAttacked().getY() < table.get(action.getCardAttacked().
                    getX()).size()) {
                table.get(action.getCardAttacked().getX()).remove(action.getCardAttacked().getY());
            }
        }
        table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                setAlreadyAttacked(1);
    }
}
