package domain.user;

import domain.card.Card;
import domain.state.DealerReady;
import exception.DealerHasNoProfitRatioException;

import java.util.List;

public final class Dealer extends User {
    public Dealer() {
        super(new Name("딜러"), new DealerReady());
    }

    @Override
    public double getProfitRatio() {
        throw new DealerHasNoProfitRatioException();
    }

    @Override
    public int drawCount() {
        return getCards().size() - 2;
    }

    @Override
    public List<Card> getOnlyFirstCard() {
        return getCards().subList(0, 1);
    }
}
