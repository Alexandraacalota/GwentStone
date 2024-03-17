package environmentcards;

import cards.Cards;
import cards.EnvironmentCards;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class HeartHound extends EnvironmentCards {

    public HeartHound(final int mana, final String description, final ArrayList<String> colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    /** Implementation of Heart Hound's ability
     * Steals the enemy minion with the greatest health on the selected row */
    @Override
    public int ability(final ArrayList<ArrayList<MinionCards>> table, final int row, final
    ArrayList<Cards> hand, final ActionsInput action, final ObjectMapper
                                   objectMapper, final ArrayNode output) {
        int index = 0, maxHealth = 0;
        final int third = 3, fourth = 4;
        for (int i = 0; i < table.get(row).size(); i++) {
            if (table.get(row).get(i).getHealth() > maxHealth) {
                index = i;
            }
        }
        int playerMana = 0;
        MinionCards card = new MinionCards(table.get(row).get(index).getMana(), table.get(row).
                get(index).getDescription(), table.get(row).get(index).getColors(), table.get(row).
                get(index).getName(), table.get(row).get(index).getAttackDamage(), table.get(row).
                get(index).getHealth());
        // Check if the mirrored allied row has less than 5 minions
        if (row == 0) {
            if (table.get(row + third).size() > fourth) {
                errorHeartHoundAbility(objectMapper, output, action);
                return playerMana;
            } else {
                table.get(row).remove(index);
                table.get(row + third).add(card);
            }
        } else if (row == 1) {
            if (table.get(row + 1).size() > fourth) {
                errorHeartHoundAbility(objectMapper, output, action);
                return playerMana;
            } else {
                table.get(row).remove(index);
                table.get(row + 1).add(card);
            }
        } else if (row == 2) {
            if (table.get(row - 1).size() > fourth) {
                errorHeartHoundAbility(objectMapper, output, action);
                return playerMana;
            } else {
                table.get(row).remove(index);
                table.get(row - 1).add(card);
            }
        } else if (row == third) {
            if (table.get(0).size() > fourth) {
                errorHeartHoundAbility(objectMapper, output, action);
                return playerMana;
            } else {
                table.get(row).remove(index);
                table.get(0).add(card);
            }
        }
        playerMana = hand.get(action.getHandIdx()).getMana();
        // Remove the environment card from the player's hand
        hand.remove(action.getHandIdx());
        return playerMana;
    }

    /** Prints error message in case the allied row is full */
    public void errorHeartHoundAbility(final ObjectMapper objectMapper, final ArrayNode output,
                                       final ActionsInput action) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useEnvironmentCard");
        node.put("handIdx", action.getHandIdx());
        node.put("affectedRow", action.getAffectedRow());
        node.put("error", "Cannot steal enemy card since the player's row is full.");
        output.add(node);
    }
}
