package domain.card;

import java.util.ArrayList;
import java.util.List;

public final class Hand {
    public static final Score maxDealerHit = new Score(16);

    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public Hand add(Card card) {
        List<Card> newCards = new ArrayList<>(cards);
        newCards.add(card);
        return new Hand(newCards);
    }

    public boolean hasLessScore(Hand other) {
        return calculateScore().isLessThan(other.calculateScore());
    }

    public boolean hasSameScore(Hand other) {
        return calculateScore().equals(other.calculateScore());
    }

    public boolean isBust() {
        return calculateScore().isOverMax();
    }

    public boolean isBlackjack() {
        return calculateScore().isMax() && cards.size() == 2;
    }

    public boolean isDealerHit() {
        return calculateScore().isLessThanOrEqual(maxDealerHit);
    }

    public Score calculateScore() {
        Score sumScore = sum();
        if(sumScore.isOverMax()) {
            return sumScore.calculateAceAsOne(countOfAce());
        }
        return sumScore;
    }

    private Score sum() {
        return cards.stream()
                .map(Card::getScore)
                .reduce(new Score(0), Score::add);
    }

    private int countOfAce() {
        return (int) cards.stream()
                .filter(Card::isAce)
                .count();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
