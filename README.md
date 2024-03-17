Copyright 2022 Alexandra-Maria Calota
# GwentStone

## Overview
GwentStone is a strategy card game where two players battle to eliminate each other's hero. Players strategically place cards on a table, each with unique abilities and attacks, using mana. The game ends when one hero's health reaches zero.

## Gameplay
- The game involves a table with four rows, two belonging to each player.
- Cards are placed on the table using mana.
- Each card has an attack and/or ability, depending on its type.
- Players' heroes also have an ability that can be used once per round.
- Minion cards on the table can use their attacks on the enemy hero and decrease their health by their attack damage.
- Once a hero dies, the game ends, and the player with the hero still standing wins.

## Implementation Details
- **Card Types:** Implemented using multiple classes for each type of card.
- **Abstract Classes:** Utilized abstract classes for cards, heroes, minions, and environment cards.
- **Game Setup:** Initialization includes setting up player decks, hero cards, and mana allocation.
- **Action Commands:** Various commands are available for players to interact with the game state.

## Heroes' Abilities
- Empress Thorina: Destroys the card with the greatest health on the selected row.
- General Kocioraw: Increments the attack damage of all cards on the selected row by one.
- King Mudface: Increments the health of all cards on the selected allied row by one.
- Lord Royce: Freezes the card with the greatest attack damage on the selected row.

## Special Abilities of Minion Cards
- Disciple: Grows health by 2 for an allied minion.
- Miraj: Swaps his health with an enemy minion's one.
- The Cursed One: Swaps an enemy minion's health with his attack damage.
- The Ripper: Reduces the attack damage of an enemy minion.

## Environment Cards' Abilities
- Firestorm: Decreases health of all minions on the selected row by one.
- Heart Hound: Steals the enemy minion with the greatest health on the selected row.
- Winterfell: Freezes the cards on the selected row for one round.

### Available Commands
- **getCardsInHand:** Prints an ArrayList containing the cards in the hand of the selected player.
- **getPlayerDeck:** Prints the cards left in the deck of the selected player.
- **getPlayerHero:** Prints the hero card of the selected player.
- **getPlayerTurn:** Prints the index of the current player's turn.
- **getCardsOnTable:** Prints the cards on the table at a certain point in the game.
- **getCardAtPosition:** Prints the card with the given coordinates from the table at a specific time during the game.
- **getPlayerMana:** Prints the mana left for the selected player.
- **getEnvironmentCardsInHand:** Prints all the environment cards left in a player's hand.
- **getFrozenCardsOnTable:** Prints all the cards that are frozen that round on the table.
- **endPlayerTurn:** Starts a new round, each player gets a new card in hand and extra mana, and then the frozen and alreadyAttacked flags are set to 0.
- **placeCard:** Places a minion card on the table on its suitable row.
- **useEnvironmentCard:** Uses the ability of an environment card in the selected player's hand.
- **cardUsesAttack:** A selected card on the table attacks an enemy minion and decreases its health.
- **cardUsesAbility:** A minion that owns a special ability uses it on either allied minions or enemy ones.
- **useAttackHero:** A selected minion on the table that belongs to the current player uses its attack on the enemy hero and decreases its health.
- **useHeroAbility:** The current player uses his hero's ability either on his minions or on the enemy ones.
- **getPlayerOneWins:** Returns the number of games won by the first player.
- **getPlayerTwoWins:** Returns the number of games won by the second player.
- **getTotalGamesPlayed:** Returns the total number of games played.
