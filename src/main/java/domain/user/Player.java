package domain.user;

import domain.card.Card;
import domain.state.UserReady;
import exception.UnsupportedOperationAtPlayerException;

import java.util.List;

public final class Player extends User {
    public Player(String name) {
        super(new Name(name), new UserReady());
    }

    @Override
    public double getProfitRatio() {
        return getState().getProfitRatio();
    }

    @Override
    public int drawCount() {
        throw new UnsupportedOperationAtPlayerException();
    }

    @Override
    public List<Card> getOnlyFirstCard() {
        throw new UnsupportedOperationAtPlayerException();
    }
}
