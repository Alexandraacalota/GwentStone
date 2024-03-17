package heroes;

import cards.Heroes;
import cards.MinionCards;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class KingMudface extends Heroes {

    public KingMudface(final int mana, final String description, final ArrayList<String> colors,
                       final String name, final int health) {
        super(mana, description, colors, name, health);
    }

    /** Implementation of King Mudface's ability
     * Increments the health of all cards on the selected allied row by one */
    @Override
    public int heroAbility(final ArrayList<ArrayList<MinionCards>> table, final int playerTurn,
                           final int row, final ActionsInput action, final ObjectMapper
                                       objectMapper, final ArrayNode output, final Heroes hero) {
        int mana = 0;
        if (checkForGeneralAndKingError(action, objectMapper, output, playerTurn, row)) {
            return mana;
        }
        for (int i = 0; i < table.get(row).size(); i++) {
            table.get(row).get(i).setHealth(table.get(row).get(i).getHealth() + 1);
        }
        mana = hero.getMana();
        hero.setHasAttacked(1);
        return mana;
    }

}
