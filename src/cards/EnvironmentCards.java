package cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public abstract class EnvironmentCards extends Cards {

    public EnvironmentCards(final int mana, final String description,
                            final ArrayList<String> colors, final String name) {
        super(mana, description, colors, name);
    }

    /** The card is not a minion */
    @Override
    public int isMinionCard() {
        return 0;
    }

    /** Environment cards' abilities implementation */
    public abstract int ability(ArrayList<ArrayList<MinionCards>> table, int row, ArrayList<Cards>
            hand, ActionsInput action, ObjectMapper objectMapper, ArrayNode output);

}
