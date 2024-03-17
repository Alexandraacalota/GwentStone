package game;
import cards.Cards;
import cards.EnvironmentCards;
import cards.Heroes;
import cards.MinionCards;
import cards.SpecialAbilitiesMinionCards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import environmentcards.Firestorm;
import environmentcards.HeartHound;
import environmentcards.Winterfell;
import fileio.ActionsInput;
import fileio.Input;
import fileio.StartGameInput;
import fileio.CardInput;
import fileio.DecksInput;
import fileio.Coordinates;
import fileio.GameInput;

import heroes.EmpressThorina;
import heroes.GeneralKocioraw;
import heroes.KingMudface;
import heroes.LordRoyce;
import specialabilitiesminioncards.Disciple;
import specialabilitiesminioncards.Miraj;
import specialabilitiesminioncards.TheCursedOne;
import specialabilitiesminioncards.TheRipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static cards.MinionCards.createNode;

public class Game {
    private ArrayList<ArrayList<MinionCards>> table;
    private int currentGame;
    private int playerOneWins;
    private int playerTwoWins;

    /** Returns the table */
    public ArrayList<ArrayList<MinionCards>> getTable() {
        return table;
    }

    /** Sets the table */
    public void setTable(final ArrayList<ArrayList<MinionCards>> table) {
        this.table = table;
    }

    /** Returns the current game */
    private int getCurrentGame() {
        return currentGame;
    }

    /** Sets the current game */
    private void setCurrentGame(final int currentGame) {
        this.currentGame = currentGame;
    }

    /** Returns first player's wins */
    public int getPlayerOneWins() {
        return playerOneWins;
    }

    /** Returns second player's wins */
    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    /** Sets first player's wins */
    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    /** Sets second player's wins */
    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }

    /** Method that prepares a new table for the game */
    public ArrayList<ArrayList<MinionCards>> prepareTable() {
        this.table = new ArrayList<>();
        int third = 1 + 2;
        for (int i = 0; i <= third; i++) {
            table.add(new ArrayList<>());
        }
        return this.table;
    }

    /** Method that shuffles the chosen deck and instantiates cards of their corresponding types */
    public ArrayList<Cards> prepareDeck(final DecksInput playerDecksInput, final StartGameInput
            startGameInput, final int playerDeckIdx) {
        ArrayList<CardInput> playerDeck = new ArrayList<>();
        int m = playerDecksInput.getNrCardsInDeck();

        ArrayList<CardInput> pDeck = playerDecksInput.getDecks().get(playerDeckIdx);
        for (int i = 0; i < m; i++) {
            playerDeck.add(pDeck.get(i));
        }
        Collections.shuffle(playerDeck, new Random(startGameInput.getShuffleSeed()));
        ArrayList<Cards> deck = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            switch (playerDeck.get(i).getName()) {
                case "Firestorm" -> {
                    Firestorm card = new Firestorm(playerDeck.get(i).getMana(), playerDeck.
                            get(i).getDescription(), playerDeck.get(i).getColors(), playerDeck.
                            get(i).getName());
                    deck.add(card);
                }
                case "Winterfell" -> {
                    Winterfell card = new Winterfell(playerDeck.get(i).getMana(), playerDeck.
                            get(i).getDescription(), playerDeck.get(i).getColors(), playerDeck.
                            get(i).getName());
                    deck.add(card);
                }
                case "Heart Hound" -> {
                    HeartHound card = new HeartHound(playerDeck.get(i).getMana(), playerDeck.
                            get(i).getDescription(), playerDeck.get(i).getColors(), playerDeck.
                            get(i).getName());
                    deck.add(card);
                }
                case "The Ripper" -> {
                    TheRipper card = new TheRipper(playerDeck.get(i).getMana(), playerDeck.get(i).
                            getDescription(), playerDeck.get(i).getColors(), playerDeck.get(i).
                            getName(), playerDeck.get(i).getAttackDamage(), playerDeck.get(i).
                            getHealth());
                    deck.add(card);
                }
                case "Miraj" -> {
                    Miraj card = new Miraj(playerDeck.get(i).getMana(), playerDeck.get(i).
                            getDescription(), playerDeck.get(i).getColors(), playerDeck.get(i).
                            getName(), playerDeck.get(i).getAttackDamage(), playerDeck.get(i).
                            getHealth());
                    deck.add(card);
                }
                case "The Cursed One" -> {
                    TheCursedOne card = new TheCursedOne(playerDeck.get(i).getMana(), playerDeck.
                            get(i).getDescription(), playerDeck.get(i).getColors(), playerDeck.
                            get(i).getName(), playerDeck.get(i).getAttackDamage(),
                            playerDeck.get(i).getHealth());
                    deck.add(card);
                }
                case "Disciple" -> {
                    Disciple card = new Disciple(playerDeck.get(i).getMana(), playerDeck.get(i).
                            getDescription(), playerDeck.get(i).getColors(), playerDeck.get(i).
                            getName(), playerDeck.get(i).getAttackDamage(), playerDeck.get(i).
                            getHealth());
                    deck.add(card);
                }
                default -> {
                    MinionCards card = new MinionCards(playerDeck.get(i).getMana(), playerDeck.
                            get(i).getDescription(), playerDeck.get(i).getColors(), playerDeck.
                            get(i).getName(), playerDeck.get(i).getAttackDamage(), playerDeck.
                            get(i).getHealth());
                    deck.add(card);
                }
            }
        }
        return deck;
    }

    /** Method that creates a hero according to its type */
    public Heroes createHeroCard(final CardInput playerHero, final int health) {
        return switch (playerHero.getName()) {
            case "Lord Royce" ->
                    new LordRoyce(playerHero.getMana(), playerHero.getDescription(),
                            playerHero.getColors(), playerHero.getName(), health);
            case "Empress Thorina" ->
                    new EmpressThorina(playerHero.getMana(), playerHero.getDescription(),
                            playerHero.getColors(), playerHero.getName(), health);
            case "King Mudface" ->
                    new KingMudface(playerHero.getMana(), playerHero.getDescription(),
                            playerHero.getColors(), playerHero.getName(), health);
            default -> new GeneralKocioraw(playerHero.getMana(), playerHero.getDescription(),
                    playerHero.getColors(), playerHero.getName(), health);
        };
    }

    /** Method that starts the games */
    public void startGames(final Input inputData, final ObjectMapper objectMapper,
                           final ArrayNode output) {
        for (int i = 0; i < inputData.getGames().size(); i++) {
            setCurrentGame(i);
            startGame(inputData, objectMapper, output);
        }
    }

    /** Method in which each game is implemented
     * The table and decks are set
     * The action commands are executed using a switch case */
    public void startGame(final Input inputData, final ObjectMapper objectMapper,
                          final ArrayNode output) {
        int incrementMana = 1; // the amount of mana each player gets each round
        int firstPlayerMana = 1; // first player's mana
        int secondPlayerMana = 1; // second player's mana

        GameInput gameInput = inputData.getGames().get(getCurrentGame());
        StartGameInput startGameInput = gameInput.getStartGame();

        ArrayList<ActionsInput> actions = gameInput.getActions(); // array of actions
        table = prepareTable();

        ArrayList<Cards> playerOneDeck = prepareDeck(inputData.getPlayerOneDecks(), startGameInput,
                startGameInput.getPlayerOneDeckIdx());
        ArrayList<Cards> playerTwoDeck = prepareDeck(inputData.getPlayerTwoDecks(), startGameInput,
                startGameInput.getPlayerTwoDeckIdx());

        ArrayList<Cards> playerOneHand = new ArrayList<>(0); // first player's hand
        ArrayList<Cards> playerTwoHand = new ArrayList<>(0); // second player's hand
        int playerTurn = startGameInput.getStartingPlayer();

        CardInput player1Hero = startGameInput.getPlayerOneHero();
        CardInput player2Hero = startGameInput.getPlayerTwoHero();

        // Create the hero cards with 30 health at the start of the game
        final int heroHealth = 30;
        Heroes hero1 = createHeroCard(player1Hero, heroHealth); // first player's hero
        Heroes hero2 = createHeroCard(player2Hero, heroHealth); // second player's hero

        // At the start of a round, each player gets one card from the deck into their hand
        playerOneHand.add(playerOneDeck.get(0));
        playerTwoHand.add(playerTwoDeck.get(0));
        playerOneDeck.remove(0);
        playerTwoDeck.remove(0);

        for (ActionsInput action : actions) {
            switch (action.getCommand()) {
                case "getCardsInHand": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getCardsInHand");
                    node.put("playerIdx", action.getPlayerIdx());
                    ArrayList<Cards> copyHand;
                    if (action.getPlayerIdx() == 1) {
                        copyHand = new ArrayList<>(playerOneHand);
                    } else {
                        copyHand = new ArrayList<>(playerTwoHand);
                    }
                    node.putPOJO("output", copyHand);
                    output.add(node);
                    break;
                }
                case "getPlayerDeck": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerDeck");
                    node.put("playerIdx", action.getPlayerIdx());
                    ArrayList<Cards> copyDeck;
                    if (action.getPlayerIdx() == 1) {
                        copyDeck = new ArrayList<>(playerOneDeck);
                    } else {
                        copyDeck = new ArrayList<>(playerTwoDeck);
                    }
                    node.putPOJO("output", copyDeck);
                    output.add(node);
                    break;
                }
                case "getPlayerHero": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerHero");
                    node.put("playerIdx", action.getPlayerIdx());
                    Heroes copyHero1 = createHeroCard(player1Hero, hero1.getHealth());
                    Heroes copyHero2 = createHeroCard(player2Hero, hero2.getHealth());
                    if (action.getPlayerIdx() == 1) {
                        node.putPOJO("output", copyHero1);
                    } else {
                        node.putPOJO("output", copyHero2);
                    }
                    output.add(node);
                    break;
                }
                case "getPlayerTurn": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerTurn");
                    node.put("output", playerTurn);
                    output.add(node);
                    break;
                }
                case "getCardsOnTable": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getCardsOnTable");
                    ArrayList<ArrayList<MinionCards>> copyTable = new ArrayList<>(table);
                    node.putPOJO("output", copyTable);
                    output.add(node);
                    break;
                }
                case "getCardAtPosition": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getCardAtPosition");
                    node.put("x", action.getX());
                    node.put("y", action.getY());
                    if (table.get(action.getX()).size() > action.getY()) {
                        MinionCards cardCopy = new MinionCards(table.get(action.getX()).get(action.
                                getY()).getMana(), table.get(action.getX()).get(action.getY()).
                                getDescription(), table.get(action.getX()).get(action.getY()).
                                getColors(), table.get(action.getX()).get(action.getY()).getName(),
                                table.get(action.getX()).get(action.getY()).getAttackDamage(),
                                table.get(action.getX()).get(action.getY()).getHealth());
                        node.putPOJO("output", cardCopy);
                    } else {
                        node.put("output", "No card available at that position.");
                    }
                    output.add(node);
                    break;
                }
                case "getPlayerMana": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerMana");
                    node.put("playerIdx", action.getPlayerIdx());
                    if (action.getPlayerIdx() == 1) {
                        node.put("output", firstPlayerMana);
                    } else if (action.getPlayerIdx() == 2) {
                        node.put("output", secondPlayerMana);
                    }
                    output.add(node);
                    break;
                }
                case "getEnvironmentCardsInHand": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getEnvironmentCardsInHand");
                    node.put("playerIdx", action.getPlayerIdx());
                    ArrayList<EnvironmentCards> environmentCards = new ArrayList<>();
                    if (action.getPlayerIdx() == 1) {
                        for (Cards cards : playerOneHand) {
                            if (cards.isMinionCard() == 0) {
                                environmentCards.add((EnvironmentCards) cards);
                            }
                        }
                    }
                    if (action.getPlayerIdx() == 2) {
                        for (Cards cards : playerTwoHand) {
                            if (cards.isMinionCard() == 0) {
                                environmentCards.add((EnvironmentCards) cards);
                            }
                        }
                    }
                    ArrayList<EnvironmentCards> copyEnvironmentCards = new
                            ArrayList<>(environmentCards);
                    node.putPOJO("output", copyEnvironmentCards);
                    output.add(node);
                    break;
                }
                case "getFrozenCardsOnTable": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getFrozenCardsOnTable");
                    ArrayList<MinionCards> frozenCards = new ArrayList<>();
                    final int third = 3, fourth = 4;
                    for (int l = 0; l <= third; l++) {
                        for (int j = 0; j <= fourth; j++) {
                            if (table.get(l).size() > j) {
                                if (table.get(l).get(j).getFrozen() == 1) {
                                    frozenCards.add((table.get(l).get(j)));
                                }
                            }
                        }
                    }
                    node.putPOJO("output", frozenCards);
                    output.add(node);
                    break;
                }
                case "endPlayerTurn":
                    if (playerTurn == 1) {
                        playerTurn++;
                    } else if (playerTurn == 2) {
                        playerTurn--;
                    }
                    final int ten = 10;
                    // Every time it gets to the starting player's turn, a new round begins
                    if (playerTurn == startGameInput.getStartingPlayer()) {
                        if (incrementMana <= ten) {
                            // Increment the amount of mana each player gets the current round
                            incrementMana++;
                        }
                        // Increment each player's mana
                        firstPlayerMana += incrementMana;
                        secondPlayerMana += incrementMana;
                        // Each player gets a new card in hand from deck
                        if (playerOneDeck.size() != 0 && playerTwoDeck.size() != 0) {
                            playerOneHand.add(playerOneDeck.get(0));
                            playerTwoHand.add(playerTwoDeck.get(0));
                            playerOneDeck.remove(0);
                            playerTwoDeck.remove(0);
                        }
                    }
                    // Reset the frozen and alreadyAttacked flags to 0
                    resetFrozen(playerTurn);
                    resetAttack(playerTurn, hero1, hero2);
                    break;
                case "placeCard":
                    int mana = placeCard(action, playerOneHand, playerTwoHand, objectMapper, output,
                            firstPlayerMana, secondPlayerMana, playerTurn);
                    if (playerTurn == 1) {
                        firstPlayerMana = firstPlayerMana - mana;
                    } else {
                        secondPlayerMana = secondPlayerMana - mana;
                    }
                    break;
                case "useEnvironmentCard":
                    if (playerTurn == 1) {
                        firstPlayerMana = firstPlayerMana - useEnvironmentCard(action,
                                playerOneHand, firstPlayerMana, objectMapper, output, playerTurn);
                    } else if (playerTurn == 2) {
                        secondPlayerMana = secondPlayerMana - useEnvironmentCard(action,
                                playerTwoHand, secondPlayerMana, objectMapper, output, playerTurn);
                    }
                    break;
                case "cardUsesAttack":
                    useCardAttack(action, playerTurn, objectMapper, output);
                    break;
                case "cardUsesAbility":
                    useCardAbility(action, playerTurn, objectMapper, output);
                    break;
                case "useAttackHero":
                    if (playerTurn == 1) {
                        if (useAttackHero(action, playerTurn, objectMapper, output, hero2)) {
                            setPlayerOneWins(getPlayerOneWins() + 1);
                        }
                    } else if (playerTurn == 2) {
                        if (useAttackHero(action, playerTurn, objectMapper, output, hero1)) {
                            setPlayerTwoWins(getPlayerTwoWins() + 1);
                        }
                    }
                    break;
                case "useHeroAbility":
                    if (playerTurn == 1) {
                        firstPlayerMana = firstPlayerMana - useHeroAbility(action, playerTurn,
                                objectMapper, output, hero1, firstPlayerMana);
                    } else if (playerTurn == 2) {
                        secondPlayerMana = secondPlayerMana - useHeroAbility(action, playerTurn,
                                objectMapper, output, hero2, secondPlayerMana);
                    }
                    break;
                case "getPlayerOneWins": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerOneWins");
                    node.put("output", getPlayerOneWins());
                    output.add(node);
                    break;
                }
                case "getPlayerTwoWins": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerTwoWins");
                    node.put("output", getPlayerTwoWins());
                    output.add(node);
                    break;
                }
                case "getTotalGamesPlayed": {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getTotalGamesPlayed");
                    node.put("output", getPlayerOneWins() + getPlayerTwoWins());
                    output.add(node);
                    break;
                }
                default : {
                    break;
                }
            }


        }
    }

    /** Method that resets frozen flag to 0 */
    public void resetFrozen(final int playerTurn) {
        if (playerTurn == 1) {
            // Reset the frozen flag of the second player's minions on the table
                for (int k = 0; k < table.get(0).size(); k++) {
                    table.get(0).get(k).setFrozen(0);
                }
                for (int k = 0; k < table.get(1).size(); k++) {
                    table.get(1).get(k).setFrozen(0);
                }
        } else if (playerTurn == 2) {
            // Reset the frozen flag of the first player's minions on the table
            for (int k = 0; k < table.get(2).size(); k++) {
                table.get(2).get(k).setFrozen(0);
            }
            final int third = 3;
            for (int k = 0; k < table.get(third).size(); k++) {
                table.get(third).get(k).setFrozen(0);
            }
        }
    }

    /** Method that resets the alreadyAttacked flag to 0 */
    public void resetAttack(final int playerTurn, final Heroes hero1, final Heroes hero2) {
        if (playerTurn == 1) {
            // Reset the attacks of the first player's minions on the table
            for (int k = 0; k < table.get(2).size(); k++) {
                table.get(2).get(k).setAlreadyAttacked(0);
            }
            final int third = 3;
            for (int k = 0; k < table.get(third).size(); k++) {
                table.get(third).get(k).setAlreadyAttacked(0);
            }
            hero1.setHasAttacked(0);
        } else if (playerTurn == 2) {
            // Reset the attacks of the second player's minions on the table
            for (int k = 0; k < table.get(0).size(); k++) {
                table.get(0).get(k).setAlreadyAttacked(0);
            }
            for (int k = 0; k < table.get(1).size(); k++) {
                table.get(1).get(k).setAlreadyAttacked(0);
            }
            hero2.setHasAttacked(0);
        }
    }

    /** Method that prints error messages for heroes' abilities */
    public ObjectNode heroAbilityError(final ActionsInput action,
                                        final ObjectMapper objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", action.getAffectedRow());
        return node;
    }

    /** Method that calls hero's ability */
    public int useHeroAbility(final ActionsInput action, final int playerTurn, final ObjectMapper
            objectMapper, final ArrayNode output, final Heroes hero, final int playerMana) {
        int mana = 0;
        // Check to see if the player has enough mana to use his hero's ability
        if (playerMana >= hero.getMana()) {
            // If the hero hasn't already attacked this turn, use his ability
            if (hero.getHasAttacked() == 0) {
                mana = hero.heroAbility(table, playerTurn, action.getAffectedRow(), action,
                        objectMapper, output, hero);
            } else {
                ObjectNode node = heroAbilityError(action, objectMapper);
                node.put("error", "Hero has already attacked this turn.");
                output.add(node);
            }
        } else {
            ObjectNode node = heroAbilityError(action, objectMapper);
            node.put("error", "Not enough mana to use hero's ability.");
            output.add(node);
        }
        return mana;
    }

    /** Method that prints error message in case of a hero attack fail */
    public ObjectNode attackHeroError(final ActionsInput action, final ObjectMapper
            objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useAttackHero");
        Coordinates cardAttackerCoordinates = new Coordinates();
        cardAttackerCoordinates.setX(action.getCardAttacker().getX());
        cardAttackerCoordinates.setY(action.getCardAttacker().getY());
        node.putPOJO("cardAttacker", cardAttackerCoordinates);
        return node;
    }

    /** Method that decreases the health of the hero by the attack damage of the minion card */
    public boolean attackHero(final ActionsInput action, final int playerTurn, final ObjectMapper
            objectMapper, final ArrayNode output, final Heroes hero) {
        hero.setHealth(hero.getHealth() - table.get(action.getCardAttacker().getX()).
                get(action.getCardAttacker().getY()).getAttackDamage());
        table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                setAlreadyAttacked(1);
        // In case the hero dies, print the end of game message
        if (hero.getHealth() <= 0) {
            ObjectNode node = objectMapper.createObjectNode();
            if (playerTurn == 1) {
                node.put("gameEnded", "Player one killed the enemy hero.");
            } else {
                node.put("gameEnded", "Player two killed the enemy hero.");
            }
            output.add(node);
            return true;
        }
        return false;
    }

    /** Method that prints an error message in case the selected card is not a tank
     * and there is one on the enemy's table */
    public boolean notTypeTankError(final MinionCards card, final ActionsInput action, final
    ObjectMapper objectMapper, final ArrayNode output) {
        if ((card.getName().equals("Goliath")) || (card.getName().equals("Warden"))) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("command", "useAttackHero");
            Coordinates cardAttackerCoordinates = new Coordinates();
            cardAttackerCoordinates.setX(action.getCardAttacker().getX());
            cardAttackerCoordinates.setY(action.getCardAttacker().getY());
            node.putPOJO("cardAttacker", cardAttackerCoordinates);
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
            return true;
        }
        return false;
    }

    /** Method that decreases the attacked card's health by the attacker card's attack damage */
    public boolean useAttackHero(final ActionsInput action, final int playerTurn, final
    ObjectMapper objectMapper, final ArrayNode output, final Heroes hero) {
        // Check to see if the attacker is frozen
        if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                getFrozen() == 0) {
            // Check to see whether the attacker has already attacked this turn
            if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                    getAlreadyAttacked() == 0) {
                if (playerTurn == 1) {
                    for (int i = 0; i < table.get(1).size(); i++) {
                        if (notTypeTankError(table.get(1).get(i), action, objectMapper, output)) {
                            return false;
                        }
                    }
                } else if (playerTurn == 2) {
                    for (int i = 0; i < table.get(2).size(); i++) {
                        if (notTypeTankError(table.get(2).get(i), action, objectMapper, output)) {
                            return false;
                        }
                    }
                }
                return attackHero(action, playerTurn, objectMapper, output, hero);
            } else {
                ObjectNode node = attackHeroError(action, objectMapper);
                node.put("error", "Attacker card has already attacked this turn.");
                output.add(node);
            }
        } else {
            ObjectNode node = attackHeroError(action, objectMapper);
            node.put("error", "Attacker card is frozen.");
            output.add(node);
        }
        return false;
    }

    /** Method that prints the error when the player is unable to use an environment card */
    public ObjectNode environmentCardError(final ActionsInput action, final ObjectMapper
            objectMapper) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "useEnvironmentCard");
        node.put("handIdx", action.getHandIdx());
        node.put("affectedRow", action.getAffectedRow());
        return node;
    }

    /** Method that implements the usage of an environment card */
    public int useEnvironmentCard(final ActionsInput action, final ArrayList<Cards> hand, final int
            playerMana, final ObjectMapper objectMapper, final ArrayNode output, final int
            playerTurn) {
        int mana = 0;
        final int third = 3;
        if (action.getHandIdx() < hand.size()) {
            // Check to see if the card is an environment one
            if (hand.get(action.getHandIdx()).isMinionCard() == 0) {
                // Check to see whether the player has enough mana to use the selected card
                if (playerMana >= hand.get(action.getHandIdx()).getMana()) {
                    // Check to see if the selected row belongs to the enemy
                    if ((playerTurn == 1 && (action.getAffectedRow() == 0 || action.
                            getAffectedRow() == 1)) || (playerTurn == 2 && (action.
                            getAffectedRow() == 2 || action.getAffectedRow() == third))) {
                        mana = ((EnvironmentCards) hand.get(action.getHandIdx())).
                                ability(table, action.getAffectedRow(), hand, action,
                                        objectMapper, output);
                    } else {
                        ObjectNode node = environmentCardError(action, objectMapper);
                        node.put("error", "Chosen row does not belong to the enemy.");
                        output.add(node);
                    }
                } else {
                    ObjectNode node = environmentCardError(action, objectMapper);
                    node.put("error", "Not enough mana to use environment card.");
                    output.add(node);
                }
            } else {
                ObjectNode node = environmentCardError(action, objectMapper);
                node.put("error", "Chosen card is not of type environment.");
                output.add(node);
            }
        }
        return mana;
    }

    /** Method that prints an error message in case of a failed card attack */
    public ObjectNode cardAttackError(final ActionsInput action, final ObjectMapper
            objectMapper) {
        return createNode(action, objectMapper);
    }

    /** Method that implements the usage of a card's attack on an enemy minion */
    public void useCardAttack(final ActionsInput action, final int playerTurn, final ObjectMapper
            objectMapper, final ArrayNode output) {
        final int third = 3;
        // Check to see if the selected card to be attacked belongs to the enemy
        if ((playerTurn == 1 && ((action.getCardAttacked().getX() == 0) || (action.
                getCardAttacked().getX() == 1))) || (playerTurn == 2 && ((action.getCardAttacked().
                getX() == 2) || (action.getCardAttacked().getX() == third)))) {
            // Check to see if the selected minion on the table has already used its ability the
            // current round
            if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                    getAlreadyAttacked() == 0) {
                // Check to see if the attacker is frozen
                if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().
                        getY()).getFrozen() == 0) {
                    // Check if there is a tank card on the enemy's table and whether it was
                    // selected for the attack
                    if (!(table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().
                            getY()).lookForTankForAttack(action, table, playerTurn, objectMapper,
                            output))) {
                        if (action.getCardAttacked().getY() < table.get(action.getCardAttacked().
                                getX()).size()) {
                            table.get(action.getCardAttacked().getX()).get(action.
                                    getCardAttacked().getY()).setHealth(table.get(action.
                                    getCardAttacked().getX()).get(action.getCardAttacked().getY())
                                    .getHealth() - table.get(action.getCardAttacker().getX()).
                                    get(action.getCardAttacker().getY()).getAttackDamage());
                            table.get(action.getCardAttacker().getX()).get(action.
                                    getCardAttacker().getY()).setAlreadyAttacked(1);
                            if (action.getCardAttacked().getY() < table.get(action.
                                    getCardAttacked().getX()).size()) {
                                if (table.get(action.getCardAttacked().getX()).get(action.
                                        getCardAttacked().getY()).getHealth() <= 0) {
                                    table.get(action.getCardAttacked().getX()).remove(action.
                                            getCardAttacked().getY());
                                }
                            }
                        }
                    }
                } else {
                    ObjectNode node = cardAttackError(action, objectMapper);
                    node.put("error", "Attacker card is frozen.");
                    output.add(node);
                }
            } else {
                ObjectNode node = cardAttackError(action, objectMapper);
                node.put("error", "Attacker card has already attacked this turn.");
                output.add(node);
            }
        } else {
            ObjectNode node = cardAttackError(action, objectMapper);
            node.put("error", "Attacked card does not belong to the enemy.");
            output.add(node);
        }
    }

    /** Method that implements the usage of a card's ability */
    public void useCardAbility(final ActionsInput action, final int playerTurn, final ObjectMapper
            objectMapper, final ArrayNode output) {
        if (action.getCardAttacker().getY() < table.get(action.getCardAttacker().getX()).size()) {
            // Check to see if the minion card has a special ability
            if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY()).
                    hasSpecialAbility() == 1) {
                // Check to see if the attacker is frozen
                if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().
                        getY()).getFrozen() == 0) {
                    // Check to see whether the selected card on the table has already attacked
                    // this turn
                    if (table.get(action.getCardAttacker().getX()).get(action.getCardAttacker().
                            getY()).getAlreadyAttacked() == 0) {
                        if (action.getCardAttacked().getY() < table.get(action.getCardAttacked().
                                getX()).size()) {
                            ((SpecialAbilitiesMinionCards) table.get(action.getCardAttacker().
                                    getX()).get(action.getCardAttacker().getY())).
                                    ability(action, table, objectMapper, output, playerTurn);
                            if (action.getCardAttacked().getY() < table.get(action.
                                    getCardAttacked().getX()).size()) {
                                // Remove the attacked cards that are left with no health
                                if (table.get(action.getCardAttacked().getX()).get(action.
                                        getCardAttacked().getY()).getHealth() <= 0) {
                                    table.get(action.getCardAttacked().getX()).remove(action.
                                            getCardAttacked().getY());
                                }
                            }
                        }
                    } else {
                        ObjectNode node = table.get(action.getCardAttacker().getX()).get(action.
                                getCardAttacker().getY()).errorMessage(action, objectMapper);
                        node.put("error", "Attacker card has already attacked this turn.");
                        output.add(node);
                    }
                } else {
                    ObjectNode node = table.get(action.getCardAttacker().getX()).get(action.
                            getCardAttacker().getY()).errorMessage(action, objectMapper);
                    node.put("error", "Attacker card is frozen.");
                    output.add(node);
                }
            }
        }
    }

    /** Method that creates a minion card of its type */
    public MinionCards createNewMinionCard(final MinionCards minionCards) {
        MinionCards card = new MinionCards(minionCards.getMana(), minionCards.getDescription(),
                minionCards.getColors(), minionCards.getName(), minionCards.getAttackDamage(),
                minionCards.getHealth());
        if (minionCards.hasSpecialAbility() == 1) {
            if (minionCards.getName().equals("The Ripper")) {
                TheRipper placedCard = new TheRipper(minionCards.getMana(), minionCards.
                        getDescription(), minionCards.getColors(), minionCards.getName(),
                        minionCards.getAttackDamage(), minionCards.getHealth());
                return placedCard;
            } else if (minionCards.getName().equals("Miraj")) {
                Miraj placedCard = new Miraj(minionCards.getMana(), minionCards.getDescription(),
                        minionCards.getColors(), minionCards.getName(), minionCards.
                        getAttackDamage(), minionCards.getHealth());
                return placedCard;
            } else if (minionCards.getName().equals("The Cursed One")) {
                TheCursedOne placedCard = new TheCursedOne(minionCards.getMana(), minionCards.
                        getDescription(), minionCards.getColors(),  minionCards.getName(),
                        minionCards.getAttackDamage(), minionCards.getHealth());
                return placedCard;
            } else if (minionCards.getName().equals("Disciple")) {
                Disciple placedCard = new Disciple(minionCards.getMana(), minionCards.
                        getDescription(), minionCards.getColors(), minionCards.getName(),
                        minionCards.getAttackDamage(), minionCards.getHealth());
                return placedCard;
            }
        } else {
            MinionCards placedCard = new MinionCards(minionCards.getMana(), minionCards.
                    getDescription(), minionCards.getColors(), minionCards.getName(), minionCards.
                    getAttackDamage(), minionCards.getHealth());
            return placedCard;
        }
        return card;
    }

    /** Method that implements placing a card on the table from each player's hand */
    public int placeCard(final ActionsInput action, final ArrayList<Cards> playerOneHand, final
    ArrayList<Cards> playerTwoHand, final ObjectMapper objectMapper, final ArrayNode output,
                         final int firstPlayerMana, final int secondPlayerMana,
                         final int playerTurn) {
        int mana = 0;
        final int three = 3, four = 4, five = 5;
        MinionCards minionCards;
        if (playerTurn == 1) {
            if (action.getHandIdx() < playerOneHand.size()) {
                // Check to see if the card is an environment or a minion one
                if (playerOneHand.get(action.getHandIdx()).isMinionCard() == 0) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "placeCard");
                    node.put("error", "Cannot place environment card on table.");
                    node.put("handIdx", action.getHandIdx());
                    output.add(node);
                } else if (playerOneHand.get(action.getHandIdx()).isMinionCard() == 1) {
                    minionCards = (MinionCards) playerOneHand.get(action.getHandIdx());
                    MinionCards placedCard = createNewMinionCard(minionCards);
                    // Checks if the player has enough mana to place the card on the table
                    if (placedCard.getMana() <= firstPlayerMana) {
                        if (playerOneHand.get(action.getHandIdx()).getName().equals("The Ripper")
                                || playerOneHand.get(action.getHandIdx()).getName().equals("Miraj")
                                || playerOneHand.get(action.getHandIdx()).getName().
                                equals("Goliath") || playerOneHand.get(action.getHandIdx()).
                                getName().equals("Warden")) {
                            if (table.get(2).size() > four) {
                                ObjectNode node = objectMapper.createObjectNode();
                                node.put("command", "placeCard");
                                node.put("error", "Cannot place card on table since row is full.");
                                node.put("handIdx", action.getHandIdx());
                                output.add(node);
                            } else {
                                // Place The Ripper Miraj Goliath and Warden on the front row
                                table.get(2).add(placedCard);
                                mana = placedCard.getMana();
                                playerOneHand.remove(action.getHandIdx());
                            }
                        } else {
                            if (table.get(three).size() > four) {
                                ObjectNode node = objectMapper.createObjectNode();
                                node.put("command", "placeCard");
                                node.put("error", "Cannot place card on table since row is full.");
                                node.put("handIdx", action.getHandIdx());
                                output.add(node);
                            } else {
                                // Place the other four minions on the back row of the table
                                table.get(three).add(placedCard);
                                mana = placedCard.getMana();
                                playerOneHand.remove(action.getHandIdx());
                            }
                        }
                    } else {
                        ObjectNode node = objectMapper.createObjectNode();
                        node.put("command", "placeCard");
                        node.put("error", "Not enough mana to place card on table.");
                        node.put("handIdx", action.getHandIdx());
                        output.add(node);
                    }
                }
            }
        } else if (playerTurn == 2) {
            if (action.getHandIdx() < playerTwoHand.size()) {
                // Check to see if the selected card is an environment one
                if (playerTwoHand.get(action.getHandIdx()).isMinionCard() == 0) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "placeCard");
                    node.put("error", "Cannot place environment card on table.");
                    node.put("handIdx", action.getHandIdx());
                    output.add(node);
                } else {
                    minionCards = (MinionCards) playerTwoHand.get(action.getHandIdx());
                    MinionCards placedCard = createNewMinionCard(minionCards);
                    // Check to see if the second player has enough mana to place the card
                    // on the table
                    if (placedCard.getMana() <= secondPlayerMana) {
                        if (playerTwoHand.get(action.getHandIdx()).getName().equals("The Ripper")
                                || playerTwoHand.get(action.getHandIdx()).getName().equals("Miraj")
                                || playerTwoHand.get(action.getHandIdx()).getName().
                                equals("Goliath") || playerTwoHand.get(action.getHandIdx()).
                                getName().equals("Warden")) {
                            if (table.get(1).size() == five) {
                                ObjectNode node = objectMapper.createObjectNode();
                                node.put("command", "placeCard");
                                node.put("error", "Cannot place card on table since row is full.");
                                node.put("handIdx", action.getHandIdx());
                                output.add(node);
                            } else {
                                // Place The Ripper Miraj Goliath and Warden on the front row
                                table.get(1).add(placedCard);
                                mana = placedCard.getMana();
                                playerTwoHand.remove(action.getHandIdx());
                            }
                        } else {
                            if (table.get(0).size() == five) {
                                ObjectNode node = objectMapper.createObjectNode();
                                node.put("command", "placeCard");
                                node.put("error", "Cannot place card on table since row is full.");
                                node.put("handIdx", action.getHandIdx());
                                output.add(node);
                            } else {
                                // Place the other four minions on the back row of the table
                                table.get(0).add(placedCard);
                                mana = placedCard.getMana();
                                playerTwoHand.remove(action.getHandIdx());
                            }
                        }
                    } else {
                        ObjectNode node = objectMapper.createObjectNode();
                        node.put("command", "placeCard");
                        node.put("error", "Not enough mana to place card on table.");
                        node.put("handIdx", action.getHandIdx());
                        output.add(node);
                    }
                }
            }
        }
        return mana;
    }

}
