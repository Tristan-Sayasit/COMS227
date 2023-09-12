package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) three cards of the same rank.
 * The number of cards required is three.
 * 
 * The name of this evaluator is "Three of a Kind".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class ThreeOfAKindEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public ThreeOfAKindEvaluator(int ranking, int handSize)
  {
    super(handSize, ranking, "Three of a Kind", 3);
  }

  public boolean canSatisfy(Card[] mainCards) {
	  if (mainCards.length == cardsRequired()) {
		  for (int i = 0; i < 2; i++) {
			  if (mainCards[i].compareToIgnoreSuit(mainCards[i + 1]) != 0) {
				  return false;
			  }
		  }
	  } else {
		  return false;
	  }
	  return true;
  }
  
  public boolean canSubsetSatisfy(Card[] allCards) {
	  if (allCards.length == cardsRequired()) {
		  int counter = 0;
		  for (int i = 0; i < allCards.length - 1; i++) {
			  if (allCards[i].compareToIgnoreSuit(allCards[i + 1]) == 0) {
				  counter++;
			  } else {
				  counter = 0;
			  }
			  if (counter == 3) {
				  return true;
			  }
		  }
	  } else {
		  return false;
	  }
	  return false;
  }
}
