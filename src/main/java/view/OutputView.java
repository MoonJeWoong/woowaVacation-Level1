package view;

import dto.ChessBoardStateDto;
import dto.ChessGameScoreDto;

public final class OutputView {

    private static final String START_MESSAGE = "> 체스 게임을 시작합니다.\n" +
            "> 게임 시작 : start\n" +
            "> 게임 종료 : end \n" +
            "> 게임 이동 : move source위치 target위치 - 예. move b2 b3";
    private static final String IMPROPER_SYSTEM_COMMAND_MESSAGE = "적절하지 않은 시스템 커맨드입니다.";
    private static final String IMPROPER_GAME_COMMAND_MESSAGE = "적절하지 않은 게임 커맨드입니다.";
    private static final String STATUS_RESULT_HEADER_MESSAGE = "체스판 점수 결과";
    private static final String BLACK_SCORE_MESSAGE_FORMAT = "검은색 진영 점수: %.1f%n";
    private static final String WHITE_SCORE_MESSAGE_FORMAT = "흰색 진영 점수: %.1f%n";
    private static final String BLACK_HIGHER_SCORE_MESSAGE = "검은색 진영의 점수가 더 높습니다.\n";
    private static final String WHIRE_HIGHER_SCORE_MESSAGE = "흰색 진영의 점수가 더 높습니다.\n";
    private static final String SAME_SCORE_MESSAGE = "두 진영의 점수가 동일합니다.\n";
    private static final String END_GAME_MESSAGE = "게임이 종료되었습니다.";

    private OutputView() {
    }

    public static void printStartMessage() {
        System.out.println(START_MESSAGE);
    }

    public static void printChessBoardState(ChessBoardStateDto chessBoardStateDto) {
        System.out.println();
        for (String rowState : chessBoardStateDto.getBoardState()) {
            System.out.println(rowState);
        }
        System.out.println();
    }

    public static void printNotSystemCommandMessage() {
        System.out.println(IMPROPER_SYSTEM_COMMAND_MESSAGE);
    }

    public static void printNotGameCommandMessage() {
        System.out.println(IMPROPER_GAME_COMMAND_MESSAGE);
    }

    public static void printStatusResult(ChessGameScoreDto chessGameScoreDto) {
        System.out.println(STATUS_RESULT_HEADER_MESSAGE);
        System.out.printf(BLACK_SCORE_MESSAGE_FORMAT, chessGameScoreDto.getBlackScore());
        System.out.printf(WHITE_SCORE_MESSAGE_FORMAT, chessGameScoreDto.getWhiteScore());
        printWinResult(chessGameScoreDto.getBlackScore(), chessGameScoreDto.getWhiteScore());
    }

    private static void printWinResult(double blackScore, double whiteScore) {
        if (blackScore > whiteScore) {
            System.out.println(BLACK_HIGHER_SCORE_MESSAGE);
            return;
        }
        if (blackScore < whiteScore) {
            System.out.println(WHIRE_HIGHER_SCORE_MESSAGE);
            return;
        }
        System.out.println(SAME_SCORE_MESSAGE);
    }

    public static void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public static void printEndGameMessage() {
        System.out.println(END_GAME_MESSAGE);
    }
}
