package hw4;

import java.util.ArrayList;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * 
 * TODO: Expand this comment with an explanation of how your class hierarchy is
 * organized.
 */
public abstract class AbstractEvaluator implements IEvaluator {
	private int handSize;
	private int ranking;
	private String name;
	private int cardsRequired;

	protected AbstractEvaluator(int handSize, int ranking, String name, int cardsRequired) {
		this.handSize = handSize;
		this.ranking = ranking;
		this.name = name;
		this.cardsRequired = cardsRequired;
	}

	public String getName() {
		return name;
	}

	public int getRanking() {
		return ranking;
	}

	public int cardsRequired() {
		return cardsRequired;
	}

	public int handSize() {
		return handSize;
	}

	public abstract boolean canSatisfy(Card[] mainCards);

	public abstract boolean canSubsetSatisfy(Card[] allCards);

	public Hand createHand(Card[] allCards, int[] subset) {
		if (allCards.length < handSize) {
			return null;
		} else if (subset.length < cardsRequired) {
			return null;
		}
		Card[] subsetCards = new Card[subset.length];
		Card[] sideHand = new Card[handSize - subset.length];
		int sideHandIndex = 0;
		for (int i = 0; i < subset.length; i++) {
			subsetCards[i] = allCards[subset[i]];
		}
		if (canSatisfy(subsetCards) == false) {
			return null;
		} else {
			for (int i = 0; i < sideHand.length; i++) {
				boolean checkI = true;
				for (int j = 0; j < subset.length; j++) {
					if (i == subset[j]) {
						checkI = false;
					}
				}
				if (checkI) {
					sideHand[sideHandIndex] = allCards[i];
					sideHandIndex++;
				}
			}
			return new Hand(subsetCards, sideHand, this);
		}
	}

	public Hand getBestHand(Card[] allCards) {
		if (allCards.length < handSize) {
			return null;
		} else {
			ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired);
			Hand bestHand = null;
			for (int i = 0; i < subsets.size(); i++) {
				Hand compare = createHand(allCards, subsets.get(i));
				if (compare != null) {
					if (bestHand == null) {
						bestHand = new Hand(compare.getMainCards(), compare.getSideCards(), this);
					} else {
						if (bestHand.compareTo(compare) > 0) {
							bestHand = new Hand(compare.getMainCards(), compare.getSideCards(), this);

						}
					}
				}
			}
			return bestHand;
		}
	}
}
