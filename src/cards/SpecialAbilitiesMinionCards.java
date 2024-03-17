package cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public abstract class SpecialAbilitiesMinionCards extends MinionCards {


    public SpecialAbilitiesMinionCards(final int mana, final String description, final
    ArrayList<String> colors, final String name, final int attackDamage, final int health) {
        super(mana, description, colors, name, attackDamage, health);
    }

    /** The minion card has a special ability */
    @Override
    public int hasSpecialAbility() {
        return 1;
    }

    /** Implementation of the special abilities owned by minion cards */
    public abstract void ability(ActionsInput action, ArrayList<ArrayList<MinionCards>> table,
                                 ObjectMapper objectMapper, ArrayNode output, int playerTurn);

    /** Method that prints error message's card coordinates */
    @Override
    public ObjectNode errorMessage(final ActionsInput action, final ObjectMapper objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "cardUsesAbility");
        writeError(action, node);
        return node;
    }

    /** Method that looks for a tank in case of Miraj and The Ripper's abilities usage */
    public boolean mirajAndRipperError(final ActionsInput action, final ObjectMapper objectMapper,
                                       final ArrayNode output,
                                       final ArrayList<ArrayList<MinionCards>> table) {
        if (action.getCardAttacker().getX() == 2) {
            for (int i = 0; i < table.get(1).size(); i++) {
                if (notTypeTankError(table.get(1).get(i), action, objectMapper, output)) {
                    return true;
                }
            }
        } else if (action.getCardAttacker().getX() == 1) {
            for (int i = 0; i < table.get(2).size(); i++) {
                if (notTypeTankError(table.get(2).get(i), action, objectMapper, output)) {
                    return true;
                }
            }
        }
        return false;
    }

}
