package environmentcards;

import cards.Cards;
import cards.EnvironmentCards;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCards {

    public Firestorm(final int mana, final String description, final ArrayList<String> colors,
                     final String name) {
        super(mana, description, colors, name);
    }

    /** Implementation of Firestorm's ability
     * Decreases health of all minions on the selected row by one */
    @Override
    public int ability(final ArrayList<ArrayList<MinionCards>> table, final int row, final
    ArrayList<Cards> hand, final ActionsInput action, final ObjectMapper
                                   objectMapper, final ArrayNode output) {
        for (int i = 0; i < table.get(row).size(); i++) {
            table.get(row).get(i).setHealth(table.get(row).get(i).getHealth() - 1);
            // Remove the cards with no health left
            if (table.get(row).get(i).getHealth() == 0) {
                if (table.get(row).get(i) != null) {
                    table.get(row).remove(i);
                    i--;
                }
            }
            if (i == table.get(row).size()) {
                break;
            }
        }
        int playerMana = hand.get(action.getHandIdx()).getMana();
        hand.remove(action.getHandIdx());
        return playerMana;
    }

}
