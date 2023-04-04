package view;

import domain.command.DrawCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    public static final String DELIMITER = ",";
    private final Scanner scanner = new Scanner(System.in);

    public List<String> readPlayersName() {
        List<String> playersName = Arrays.asList(scanner.nextLine().split(DELIMITER));
        return playersName.stream()
                .map(String::strip)
                .collect(Collectors.toList());
    }

    public DrawCommand readDrawCommand() {
        return DrawCommand.of(scanner.nextLine());
    }

    public int readPlayerBetting() {
        return Integer.parseInt(scanner.nextLine());
    }
}
