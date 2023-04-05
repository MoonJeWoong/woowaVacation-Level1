package controller;

import domain.card.ShuffleDeckGenerator;
import domain.command.DrawCommand;
import domain.dto.PrizeResultDto;
import domain.dto.UserDto;
import domain.game.BlackjackGame;
import domain.game.GameResult;
import domain.user.Dealer;
import domain.user.Name;
import domain.user.Player;
import domain.user.PlayerBets;
import domain.user.Players;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BlackjackGameController {

    private final BlackjackGame blackjackGame;
    private final PlayerBets playerBets;

    public BlackjackGameController() {
        blackjackGame = setUpBlackjackGame();
        playerBets = setUpPlayerBets();
    }

    private BlackjackGame setUpBlackjackGame() {
        return new BlackjackGame(new Players(readPlayerNames()), new ShuffleDeckGenerator());
    }

    private List<String> readPlayerNames() {
        OutputView.printInputPlayerNameMessage();
        return InputView.readPlayersName();
    }

    private PlayerBets setUpPlayerBets() {
        List<UserDto> allPlayerDtos = makeAllPlayerDtos();

        return new PlayerBets(blackjackGame.getAllPlayerNames(), readPlayerBets(allPlayerDtos));
    }

    private List<UserDto> makeAllPlayerDtos() {
        List<UserDto> allPlayerDtos = new ArrayList<>();
        List<Player> allPlayers = blackjackGame.getAllPlayers();
        allPlayers.forEach(player -> allPlayerDtos.add(new UserDto(player)));

        return allPlayerDtos;
    }

    private List<Integer> readPlayerBets(List<UserDto> allPlayerDtos) {
        return allPlayerDtos.stream()
                .map(this::readPlayerBet)
                .collect(Collectors.toList());
    }

    private int readPlayerBet(UserDto playerDto) {
        OutputView.printInputPlayerBettingMessage(playerDto);
        return InputView.readPlayerBetting();
    }

    public void run() {
        showSetUpResult();
        progressPlayersTurn();
        progressDealerTurn();
        showUserCardResults();
        showFinalProfit();
    }

    private void showSetUpResult() {
        Dealer dealer = blackjackGame.getDealer();
        UserDto setUpDealerDto = new UserDto(dealer.getName(), dealer.getScore(), dealer.getOnlyFirstCard());
        List<UserDto> allPlayerDtos = makeAllPlayerDtos();
        OutputView.printSetUpResult(setUpDealerDto, allPlayerDtos);
    }

    private void progressPlayersTurn() {
        while (blackjackGame.hasReadyPlayer()) {
            progressPlayerTurn();
        }
    }

    private void progressPlayerTurn() {
        Name playerName = blackjackGame.getReadyPlayerName();
        UserDto readyPlayerDto = new UserDto(blackjackGame.getPlayerByName(playerName));
        while (!blackjackGame.hasPlayerResult(playerName) && isPlayerInputDraw(readyPlayerDto)) {
            drawCardForPlayer(playerName);
        }
        if (!blackjackGame.hasPlayerResult(playerName)) {
            doStayForPlayer(playerName);
        }
    }

    private boolean isPlayerInputDraw(UserDto readyPlayerDto) {
        OutputView.printAskOneMoreCardMessage(readyPlayerDto);
        DrawCommand inputCommand = InputView.readDrawCommand();
        return DrawCommand.DRAW.equals(inputCommand);
    }

    private void drawCardForPlayer(Name playerName) {
        blackjackGame.drawOneMoreCardForPlayer(playerName);
        showDrawResult(new UserDto(blackjackGame.getPlayerByName(playerName)));
    }

    private void doStayForPlayer(Name playerName) {
        blackjackGame.doStay(playerName);
        showDrawResult(new UserDto(blackjackGame.getPlayerByName(playerName)));
    }

    private void showDrawResult(UserDto userDto) {
        OutputView.printPlayerDrawResult(userDto);
    }

    private void progressDealerTurn() {
        blackjackGame.drawCardUntilDealerFinished();

        if (blackjackGame.getDealerDrawCount() > 0) {
            OutputView.printDealerDrawResult(blackjackGame.getDealerDrawCount());
        }
    }

    private void showUserCardResults() {
        UserDto dealerDto = new UserDto(blackjackGame.getDealer());
        List<UserDto> allPlayerDtos = makeAllPlayerDtos();

        OutputView.printUserCardsWithScore(dealerDto);
        OutputView.printAllUserCardsWithScore(allPlayerDtos);
    }

    private void showFinalProfit() {
        GameResult gameResult = blackjackGame.calculateGameResult(playerBets);
        List<PrizeResultDto> prizeResultDtos = makePrizeResultDtos(gameResult.getUserPrizes());

        OutputView.printFinalResultHeaderMessage();
        OutputView.printPlayerPrizeResult(prizeResultDtos);
    }

    private List<PrizeResultDto> makePrizeResultDtos(Map<Name, Integer> userPrizes) {
        List<PrizeResultDto> prizeResultDtos = new ArrayList<>();

        for(Map.Entry<Name, Integer> prizeResult : userPrizes.entrySet()) {
            prizeResultDtos.add(new PrizeResultDto(prizeResult.getKey(), prizeResult.getValue()));
        }
        return prizeResultDtos;
    }
}
