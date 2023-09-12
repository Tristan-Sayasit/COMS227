package hw4;

import api.Card;

/**
 * Evaluator satisfied by any set of cards.  The number of
 * required cards is equal to the hand size.
 * 
 * The name of this evaluator is "Catch All".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class CatchAllEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public CatchAllEvaluator(int ranking, int handSize)
  {
    super(handSize, ranking, "Catch All", handSize);
  }
  
  public boolean canSatisfy(Card[] mainCards) {
	  return mainCards.length == handSize();
  }
  
  public boolean canSubsetSatisfy(Card[] allCards) {
	  return allCards.length >= handSize();
  }
}
