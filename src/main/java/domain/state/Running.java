package domain.state;

import domain.card.Hand;
import exception.IllegalToGetProfitRatioInNotFinishedException;

public abstract class Running extends State {
    Running(Hand hand) {
        super(hand);
    }

    @Override
    public State stay() {
        return new Stay(getHand());
    }

    @Override
    public double getProfitRatio() {
        throw new IllegalToGetProfitRatioInNotFinishedException();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
