package heroes;

import cards.Heroes;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class LordRoyce extends Heroes {

    public LordRoyce(final int mana, final String description, final ArrayList<String> colors,
                     final String name, final int health) {
        super(mana, description, colors, name, health);
    }

    /** Implementation of Lord Royce's ability
     * Freezes the card with the greatest attack damage on the selected row */
    @Override
    public int heroAbility(final ArrayList<ArrayList<MinionCards>> table, final int playerTurn,
                           final int row, final ActionsInput action, final ObjectMapper
                                       objectMapper, final ArrayNode output, final Heroes hero) {
        int mana = 0;
        if (checkForLordAndEmpressError(action, objectMapper, output, playerTurn, row)) {
            return mana;
        }
        int index = 0, maxAD = 0;
        for (int i = 0; i < table.get(row).size(); i++) {
            if (table.get(row).get(i).getAttackDamage() > maxAD) {
                index = i;
            }
        }
        // Sets the frozen flag to 1 for the minion with the highest attack damage
        if (index < table.get(row).size()) {
            table.get(row).get(index).setFrozen(1);
        }
        mana = hero.getMana();
        hero.setHasAttacked(1);
        return mana;
    }

}
