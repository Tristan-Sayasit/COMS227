package hw4;

import java.util.ArrayList;

import api.Card;
import util.SubsetFinder;

/**
 * Evaluator for a hand in which the rank of each card is a prime number.
 * The number of cards required is equal to the hand size. 
 * 
 * The name of this evaluator is "All Primes".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class AllPrimesEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public AllPrimesEvaluator(int ranking, int handSize)
  {
    super(handSize, ranking, "All Primes", handSize);
  }
  
  public boolean canSatisfy(Card[] mainCards) {
	  if (mainCards.length == handSize()) {
		  boolean allPrime = true;
		  for (int i = 0; i < mainCards.length; i++) {
			  if (!isPrime(mainCards[i].getRank())) {
				  allPrime = false;
			  }
		  }
		  return allPrime;
	  } else {
		  return false;
	  }
  }
  
  public boolean canSubsetSatisfy(Card[] allCards) {
	  boolean allPrime = false;
	  if (allCards.length >= handSize()) {
		  ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		  for (int i = 0; i < subsets.size(); i++) {
			  Card[] test = new Card[subsets.get(i).length];
			  int testIndex = 0;
			  for (int j = 0; j < test.length; j++) {
				  test[testIndex] = allCards[subsets.get(i)[j]];
				  testIndex++;
			  }
			  if (!allPrime) {
				  allPrime = canSatisfy(test);
			  }
		  }
	  } else {
		  return false;
	  }
	  return allPrime;
  }
  
  public boolean isPrime(int num) {
	  for (int i = 2; i < num; i++) {
		  if (num % i == 0) {
			  return false;
		  }
	  }
	  return true;
  }
}
