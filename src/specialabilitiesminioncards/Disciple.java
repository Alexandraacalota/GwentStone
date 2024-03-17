package specialabilitiesminioncards;

import cards.MinionCards;
import cards.SpecialAbilitiesMinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Disciple extends SpecialAbilitiesMinionCards {

    public Disciple(final int mana, final String description, final ArrayList<String> colors,
                    final String name, final int attackDamage, final int health) {
        super(mana, description, colors, name, attackDamage, health);
    }

    /** Implementation of Disciple's ability */
    @Override
    public void ability(final ActionsInput action, final ArrayList<ArrayList<MinionCards>> table,
                        final ObjectMapper objectMapper, final ArrayNode output,
                        final int playerTurn) {
        final int three = 3;
        // Check to see if the card selected to be attacked belongs to the enemy
        if ((playerTurn == 1 && ((action.getCardAttacked().getX() == 2) || (action.
                getCardAttacked().getX() == three))) || (playerTurn == 2 && ((action.
                getCardAttacked().getX() == 0) || (action.getCardAttacked().getX() == 1)))) {
            discipleAbility(action, table);
        } else {
            ObjectNode node = errorMessage(action, objectMapper);
            node.put("error", "Attacked card does not belong to the current player.");
            output.add(node);
        }
    }

    /** Grows health by 2 for an allied minion */
    public void discipleAbility(final ActionsInput action,
                                final ArrayList<ArrayList<MinionCards>> table) {
        table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY()).
                setHealth(table.get(action.getCardAttacked().getX()).get(action.getCardAttacked().
                        getY()).getHealth() + 2);
        table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                setAlreadyAttacked(1);
    }
}
