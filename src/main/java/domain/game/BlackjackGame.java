package domain.game;

import domain.card.DeckGenerator;
import domain.card.GameDeck;
import domain.user.Dealer;
import domain.user.Name;
import domain.user.Player;
import domain.user.PlayerBets;
import domain.user.Players;

import java.util.List;

public final class BlackjackGame {
    private final Players players;
    private final Dealer dealer;
    private final GameDeck gameDeck;

    public BlackjackGame(Players players, DeckGenerator deckGenerator) {
        this.players = players;
        this.dealer = new Dealer();
        this.gameDeck = new GameDeck(deckGenerator);

        setUp();
    }

    private void setUp() {
        dealer.receiveCards(gameDeck.drawForFirstTurn());
        players.setUpGame(gameDeck);
    }

    public void drawOneMoreCardForPlayer(Name playerName) {
        players.drawOneMoreCard(playerName, gameDeck.drawCard());
    }

    public void drawCardUntilDealerFinished() {
        boolean flag = dealer.hasResult();
        while (!flag) {
            dealer.receiveCard(gameDeck.drawCard());
            flag = dealer.hasResult();
        }
    }

    public void doStay(Name playerName) {
        players.doStay(playerName);
    }

    public boolean hasReadyPlayer() {
        return players.hasReadyPlayer();
    }

    public boolean hasPlayerResult(Name playerName) {
        return players.hasPlayerResult(playerName);
    }

    public GameResult calculateGameResult(PlayerBets playerBets) {
        GameResult gameResult = new GameResult(getDealerName(), getAllPlayerNames(), playerBets);
        gameResult.saveResults(dealer, players);
        return gameResult;
    }

    public List<Player> getAllPlayers() {
        return players.getAllPlayers();
    }

    public List<Name> getAllPlayerNames() {
        return players.getAllNames();
    }

    public Name getReadyPlayerName() {
        return players.getReadyPlayerName();
    }

    public Player getPlayerByName(Name playerName) {
        return players.getPlayerByName(playerName);
    }

    public Dealer getDealer() {
        return dealer;
    }

    private Name getDealerName() {
        return dealer.getName();
    }

    public int getDealerDrawCount() {
        return dealer.drawCount();
    }
}
