package heroes;

import cards.Heroes;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class EmpressThorina extends Heroes {

    public EmpressThorina(final int mana, final String description, final ArrayList<String>
            colors, final String name, final int health) {
        super(mana, description, colors, name, health);
    }

    /** Implementation of Empress Thorina's ability
     * Destroys the card with the greatest health on the selected row */
    @Override
    public int heroAbility(final ArrayList<ArrayList<MinionCards>> table, final int playerTurn,
                           final int row, final ActionsInput action, final ObjectMapper
                                       objectMapper, final ArrayNode output, final Heroes hero) {
        int mana = 0;
        if (checkForLordAndEmpressError(action, objectMapper, output, playerTurn, row)) {
            return mana;
        }
        int index = 0, maxHealth = 0;
        // Get the index of the minion with the greatest health on the row
        for (int i = 0; i < table.get(row).size(); i++) {
            if (table.get(row).get(i).getHealth() > maxHealth) {
                index = i;
            }
        }
        // Remove the card with the greatest health
        if (index < table.get(row).size()) {
            table.get(row).remove(index);
        }
        mana = hero.getMana();
        hero.setHasAttacked(1);
        return mana;
    }

}
