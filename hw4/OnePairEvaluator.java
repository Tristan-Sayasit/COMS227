package hw4;

import api.Card;
import api.Hand;
import api.IEvaluator;

/**
 * Evaluator for a hand containing (at least) two cards of the same rank.
 * The number of cards required is two.
 * 
 * The name of this evaluator is "One Pair".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class OnePairEvaluator extends AbstractEvaluator
{
	
	
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public OnePairEvaluator(int ranking, int handSize)
  {	
    super(handSize, ranking, "One Pair", 2);
  }

  public boolean canSatisfy(Card[] mainCards) {
	if (mainCards.length > cardsRequired()) {
		return false;
	}
	return mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0; 
  }
  
  public boolean canSubsetSatisfy(Card[] allCards) {
	for (int i = 0; i < allCards.length - 1; i++) {
		if (allCards[i].compareToIgnoreSuit(allCards[i + 1]) == 0) {
			return true;
		}
	}
	return false;
  }
  
}
