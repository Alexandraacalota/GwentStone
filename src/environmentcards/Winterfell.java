package environmentcards;

import cards.Cards;
import cards.EnvironmentCards;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCards {

    public Winterfell(final int mana, final String description, final ArrayList<String> colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    /** Implementation of Winterfell's ability
     * Freezes the cards on the selected row for one round */
    @Override
    public int ability(final ArrayList<ArrayList<MinionCards>> table, final int row, final
    ArrayList<Cards> hand, final ActionsInput action, final ObjectMapper objectMapper,
                       final ArrayNode output) {
        for (int i = 0; i < table.get(row).size(); i++) {
            table.get(row).get(i).setFrozen(1);
        }
        int playerMana = hand.get(action.getHandIdx()).getMana();
        // Remove the environment card from the player's hand
        hand.remove(action.getHandIdx());
        return playerMana;
    }
}
