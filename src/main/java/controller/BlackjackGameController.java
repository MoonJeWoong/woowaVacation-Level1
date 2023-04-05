package controller;

import domain.user.PlayerBets;
import domain.card.ShuffleDeckGenerator;
import domain.command.DrawCommand;
import domain.dto.UserDto;
import domain.game.BlackjackGame;
import domain.game.GameResult;
import domain.user.Name;
import domain.user.Players;
import view.InputView;
import view.OutputView;

import java.util.List;
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

    private PlayerBets setUpPlayerBets() {
        List<UserDto> allPlayerDtos = blackjackGame.getAllPlayerDtos();
        return new PlayerBets(blackjackGame.getAllPlayerNames(), readPlayerBets(allPlayerDtos));
    }

    public void run() {
        playGame();
    }

    private List<String> readPlayerNames() {
        OutputView.printInputPlayerNameMessage();
        return InputView.readPlayersName();
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

    private void playGame() {
        showSetUpResult();
        progressPlayersTurn();
        progressDealerTurn();
        showUserCardResults();
        showFinalProfit();
    }

    private void showSetUpResult() {
        UserDto dealerSetUpData = blackjackGame.getDealerSetUpDto();
        List<UserDto> playerGameData = blackjackGame.getAllPlayerDtos();
        OutputView.printSetUpResult(dealerSetUpData, playerGameData);
    }

    private void progressPlayersTurn() {
        while (blackjackGame.hasReadyPlayer()) {
            progressPlayerTurn();
        }
    }

    private void progressPlayerTurn() {
        // Todo: blackjackGame 객체에서는 Name 객체를 반환, Dto에서 Name을 벗겨내도록 리팩토링할 것
        UserDto readyPlayerGameDataDto = blackjackGame.getReadyPlayerDto();
        Name playerName = new Name(readyPlayerGameDataDto.getName());
        while (!blackjackGame.hasPlayerResult(playerName) && isPlayerInputDraw(readyPlayerGameDataDto)) {
            drawCardForPlayer(playerName);
        }
        if (!blackjackGame.hasPlayerResult(playerName)) {
            doStayForPlayer(playerName);
        }
    }

    private boolean isPlayerInputDraw(UserDto readyPlayerGameDataDto) {
        OutputView.printAskOneMoreCardMessage(readyPlayerGameDataDto);
        DrawCommand inputCommand = InputView.readDrawCommand();
        return DrawCommand.DRAW.equals(inputCommand);
    }

    private void drawCardForPlayer(Name playerName) {
        blackjackGame.drawOneMoreCardForPlayer(playerName);
        showDrawResult(blackjackGame.getPlayerDtoByName(playerName));
    }

    private void doStayForPlayer(Name playerName) {
        blackjackGame.doStay(playerName);
        showDrawResult(blackjackGame.getPlayerDtoByName(playerName));
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
        UserDto dealerDto = blackjackGame.getDealerDto();
        List<UserDto> allPlayerDtos = blackjackGame.getAllPlayerDtos();

        OutputView.printUserCardsWithScore(dealerDto);
        OutputView.printAllUserCardsWithScore(allPlayerDtos);
    }

    private void showFinalProfit() {
        GameResult gameResult = blackjackGame.calculateGameResult(playerBets);

        OutputView.printFinalResultHeaderMessage();
        OutputView.printPlayerPrizeResult(gameResult.getPrizeResultDtosForAllUsers());
    }
}
