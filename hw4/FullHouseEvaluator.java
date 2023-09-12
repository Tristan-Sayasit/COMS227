package hw4;

import java.util.ArrayList;

import api.Card;
import util.SubsetFinder;

/**
 * Evaluator for a generalized full house. The number of required cards is equal
 * to the hand size. If the hand size is an odd number n, then there must be (n
 * / 2) + 1 cards of the matching rank and the remaining (n / 2) cards must be
 * of matching rank. In this case, when constructing a hand, the larger group
 * must be listed first even if of lower rank than the smaller group</strong>
 * (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]). If the hand size is even, then
 * half the cards must be of matching rank and the remaining half of matching
 * rank. Any group of cards, all of which are the same rank, always satisfies
 * this evaluator.
 * 
 * The name of this evaluator is "Full House".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FullHouseEvaluator extends AbstractEvaluator {
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FullHouseEvaluator(int ranking, int handSize) {
		super(handSize, ranking, "Full House", handSize);
	}

	public boolean canSatisfy(Card[] mainCards) {
		if (mainCards.length == cardsRequired()) {
			if (mainCards.length % 2 == 0) {
				for (int i = 0; i < mainCards.length / 2; i++) {
					if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) != 0) {
						return false;
					}
				}
				for (int i = mainCards.length / 2; i < mainCards.length - 1; i++) {
					if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) != 0) {
						return false;
					}
				}
				return true;
			} else {
				int counter1 = 0;
				int counter2 = 0;
				boolean check1 = false;
				boolean check2 = false;
				for (int i = 0; i < mainCards.length - 1; i++) {
					if (!(check1 && check2)) {
						if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) == 0) {
							counter1++;
							counter2++;
						} else if (counter1 == (mainCards.length / 2) - 1) {
							check1 = true;
							counter1 = 0;
							counter2 = 0;

						} else {
							if (counter2 == mainCards.length / 2) {
								check2 = true;
								counter1 = 0; 
								counter2 = 0;
							}
						}
					} else if (!check1) {
						if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) == 0) {
							counter1++;
						}
					} else {
						if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) == 0) {
							counter2++;
						}
					}
				}
				if (!check1) {
					if (counter1 == (mainCards.length / 2) - 1) {
						return true;
					} else {
						return false;
					}
				} else {
					if (counter2 == mainCards.length / 2) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			return false;
		}
	}

	public boolean canSubsetSatisfy(Card[] allCards) {
		boolean isFullHouse = false;
		if (allCards.length >= handSize()) {
			ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
			for (int i = 0; i < subsets.size(); i++) {
				Card[] test = new Card[subsets.get(i).length];
				int testIndex = 0;
				for (int j = 0; j < test.length; j++) {
					test[testIndex] = allCards[subsets.get(i)[j]];
					testIndex++;
				}
				if (!isFullHouse) {
					isFullHouse = canSatisfy(test);
				}
			}
		} else {
			return false;
		}
		return isFullHouse;
	}
}
