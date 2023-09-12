package hw4;

import java.util.ArrayList;

import api.Card;
import api.Suit;
import util.SubsetFinder;

/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers AND the cards all
 * have the same suit.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both) To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight Flush".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightFlushEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator. Note that the maximum rank of
   * the cards to be used must be specified in order to 
   * correctly evaluate a straight with ace high.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   * @param maxCardRank
   *   largest rank of any card to be used
   */
  public StraightFlushEvaluator(int ranking, int handSize, int maxCardRank)
  {
    super(handSize, ranking, "Straight Flush", handSize);
  }
  
  public boolean canSatisfy(Card[] mainCards) {
	  if (mainCards.length == cardsRequired()) {
		  for (int i = 0; i < mainCards.length; i++) {
			  if (mainCards[i].getSuit().compareTo(mainCards[i + 1].getSuit()) != 0) {
				  return false;
			  }
		  }
		  for (int i = 0; i < mainCards.length; i++) {
			  if (mainCards[0].getRank() == 1) {
				  if (14 - mainCards[i + 1].getRank() != 1) {
					  return false;
				  }
			  } else if (mainCards[i].getRank() - mainCards[i + 1].getRank() != 1) {
				  return false;
			  }
		  }
	  } else {
		  return false;
	  }
	  return true;
  }
  
  public boolean canSubsetSatisfy(Card[] allCards) {
	  boolean isStraight = false;
	  if (allCards.length >= handSize()) {
		  ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		  for (int i = 0; i < subsets.size(); i++) {
			  Card[] test = new Card[subsets.get(i).length];
			  int testIndex = 0;
			  for (int j = 0; j < test.length; j++) {
				  test[testIndex] = allCards[subsets.get(i)[j]];
				  testIndex++;
			  }
			  if (!isStraight) {
				  isStraight = canSatisfy(test);
			  }
		  }
	  } else {
		  return false;
	  }
	  return isStraight;
  }
}
