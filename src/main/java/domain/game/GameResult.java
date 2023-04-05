package domain.game;

import domain.user.PlayerBets;
import domain.dto.PrizeResultDto;
import domain.user.Dealer;
import domain.user.Name;
import domain.user.Player;
import domain.user.Players;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class GameResult {
    public static final int DEALER_INITIAL_PRIZE = 0;
    public static final int PLAYER_DEFAULT_PRIZE = 0;

    private final Map<Name, Integer> userPrizes = new LinkedHashMap<>();

    public GameResult(Name dealerName, List<Name> playerNames, PlayerBets bets) {
        userPrizes.put(dealerName, DEALER_INITIAL_PRIZE);
        playerNames.forEach(name -> userPrizes.put(name, bets.getBetByName(name)));
    }

    public void saveResults(Dealer dealer, Players players) {
        judgeWinner(dealer, players);
        userPrizes.put(dealer.getName(), calculateDealerPrizes());
    }

    private void judgeWinner(Dealer dealer, Players players) {
        for (Name playerName : players.getAllNames()) {
            compareDealerStateWithPlayer(dealer, players.findPlayerByName(playerName));
        }
    }

    private void compareDealerStateWithPlayer(Dealer dealer, Player player) {
        if (dealer.isBust()) {
            userPrizes.put(player.getName(), calculatePrize(player));
            return;
        }
        if (dealer.isBlackjack() && player.isBlackjack()) {
            userPrizes.put(player.getName(), PLAYER_DEFAULT_PRIZE);
            return;
        }
        compareScore(dealer, player);
    }

    private void compareScore(Dealer dealer, Player player) {
        if (player.hasLessScore(dealer)) {
            userPrizes.put(player.getName(), userPrizes.get(player.getName()) * -1);
            return;
        }
        if (player.hasSameScore(dealer)) {
            userPrizes.put(player.getName(), PLAYER_DEFAULT_PRIZE);
            return;
        }
        userPrizes.put(player.getName(), calculatePrize(player));
    }

    public int calculateDealerPrizes() {
        return userPrizes.values().stream()
                .mapToInt(num -> num)
                .sum() * -1;
    }

    private int calculatePrize(Player player) {
        return (int) Math.ceil(userPrizes.get(player.getName()) * player.getProfitRatio());
    }

    public Map<Name, Integer> getUserPrizes() {
        return userPrizes;
    }
}
