import controller.BlackjackGameController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        BlackjackGameController blackJackGameController = new BlackjackGameController();
        blackJackGameController.run();
    }
}
