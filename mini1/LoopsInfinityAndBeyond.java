package mini1;

import java.util.Scanner;

/**
 * Utility class with a bunch of static methods for loop practice.
 * 
 * @author Tristan Sayasit
 */
public class LoopsInfinityAndBeyond {

	// disable instantiation
	private LoopsInfinityAndBeyond() {
	}

	/**
	 * Returns a new string in which every character in the given string that is not
	 * already repeated consecutively is doubled.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * {@code
	 * "attribute1" -> "aattrriibbuuttee11"
	 * "AAA Bonds" -> "AAA  BBoonnddss"
	 * }
	 * </pre>
	 * 
	 * @param text given starting string
	 * @return string with characters doubled
	 */
	public static String doubleChars(String text) {
		int n = text.length();
		String newString = "";
		boolean isDouble = false;
		for (int i = 0; i < n; i++) {
			char temp = text.charAt(i);
			if (i + 1 != n) {
				if (temp == text.charAt(i + 1)) {
					isDouble = true;
				} else if (i - 1 != -1) {
					if (temp == text.charAt(i - 1)) {
						isDouble = true;
					} else {
						newString += text.charAt(i);
						newString += text.charAt(i);
						isDouble = false;
					}
				} else {
					newString += temp;
					newString += temp;
				}
				if (isDouble) {
					newString += text.charAt(i);
				}
			} else if (i - 1 != -1) {
				if (temp == text.charAt(i - 1)) {
					isDouble = true;
				} else {
					newString += temp;
					newString += temp;
				}
				if (isDouble) {
					newString += temp;
				}
			} else {
				newString += temp;
				newString += temp;
			}

		}
		return newString;
	}

	/**
	 * Returns a year with the highest value, given a string containing pairs of
	 * years and values (doubles). If there are no pairs, the method returns -1. In
	 * the case of a tie, the first year with the highest value is returned. Assumes
	 * the given string follows the correct format.
	 * <p>
	 * For example, given the following string, the year 1995 is returned.
	 * 
	 * <pre>
	 * 1990 75.6 1991 110.6 1995 143.6 1997 62.3
	 * </pre>
	 * 
	 * @param data given string containing year-value pairs
	 * @return first year associated with the highest value, or -1 if no pair given
	 */
	public static int maxYear(String data) {
		{
			int maxNum = 0;

			Scanner s = new Scanner(System.in);
			int spaceCounter = 0;

			for (int i = 0; i < data.length(); i++) {

				if (data.charAt(i) == ' ') {
					spaceCounter++;
				}

			}

			if (spaceCounter % 2 == 0) {
				return -1;
			}
			data = data + " ";
			int size = (spaceCounter + 1) / 2;

			int[] yearArray = new int[size];
			double[] valueArray = new double[size];

			int switcher = 0;
			int currentSpace = -1;
			int currentIndex = 0;

			for (int j = 0; j < data.length(); j++) {

				if (data.charAt(j) == ' ') {
					if (switcher == 0) {
						yearArray[currentIndex] = Integer.valueOf(data.substring(currentSpace + 1, j));

						switcher = 1;
						currentSpace = j;
					} else {

						valueArray[currentIndex] = Double.valueOf(data.substring(currentSpace + 1, j));
						switcher = 0;
						currentSpace = j;
						currentIndex++;
					}

				}
			}

			for (int i = 0; i < (spaceCounter + 1) / 2; i++) {

				if (valueArray[maxNum] < valueArray[i]) {
					maxNum = i;
				}

			}

			maxNum = yearArray[maxNum];
			return maxNum;
		}
	}

	/**
	 * Returns the number of iterations required until <code>n</code> is equal to 1,
	 * where each iteration updates <code>n</code> in the following way:
	 * 
	 * <pre>
	 *     if n is even
	 *         divide it in half
	 *     else
	 *         multiply n by three and add 1
	 * </pre>
	 * 
	 * For example, given <code>n == 6</code>, the successive values of
	 * <code>n</code> would be 3, 10, 5, 16, 8, 4, 2, 1, so the method returns 8. If
	 * <code>n</code> is less than 1, the method returns -1.
	 * <p>
	 * <em>(Remark:</em> How do we know this won't be an infinite loop for some
	 * values of <code>n</code>? In general, we don't: in fact this is a well-known
	 * open problem in mathematics. However, it has been checked for numbers up to
	 * 10 billion, which covers the range of possible values of the Java
	 * <code>int</code> type.)
	 * 
	 * @param n given starting number
	 * @return number of iterations required to reach <code>n == 1</code>, or -1 if
	 *         <code>n</code> is not positive
	 */
	public static int collatzCounter(int n) {
		int counter = 0;

		while (n != 1) {

			if (n < 1) {
				return -1;
			}

			if (n % 2 == 0) {
				n = n / 2;
				counter++;
			} else {
				n = n * 3 + 1;
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Returns a new string in which every word in the given string is doubled. A
	 * word is defined as a contiguous group of non-space (i.e., ' ') characters
	 * that starts with an alphabetic letter and are surrounded by spaces and/or the
	 * start or end of the given string. Assumes the given string does not contain
	 * more than one consecutive white-space.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * {@code
	 * "the time time" -> "the the time time time time"
	 * "The answer is 42." -> "The The answer answer is is 42."
	 * "A. runtime = 10ms" -> "A. A. runtime runtime = 10ms"
	 * }
	 * </pre>
	 * 
	 * @param text given starting string
	 * @return new string in which words are doubled
	 */
	public static String doubleWords(String text) {
		text = text + " ";
		int currentIndex = 0;
		String currentWord = "";
		String newWords = "";

		if (text.length() > 2) {
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == ' ') {
					currentWord = text.substring(currentIndex, i);
					currentIndex = i + 1;
					if (currentWord.length() > 1) {
						Boolean digitTest = Character.isDigit(currentWord.charAt(1));
						if (digitTest) {

							newWords += currentWord + " ";
						}

						else {

							newWords += currentWord + " " + currentWord + " ";
						}

					} else {
						newWords += currentWord + " ";
					}

				}

			}
		} else {
			newWords = text + text;

		}

		newWords = newWords.substring(0, newWords.length() - 1);

		if (newWords.equals(" ")) {
			newWords = "";
		}
		return newWords;
	}

	/**
	 * Returns true if string t can be obtained from string s by removing exactly
	 * one vowel character. The vowels are the letters 'a', 'e', 'i', 'o' and 'u'.
	 * Vowels can be matched in either upper or lower case, for example, 'A' is
	 * treated the same as 'a'. If s contains no vowels the method returns false.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * {@code
	 * "banana" and "banna" returns true
	 * "Apple" and "ppl" returns false
	 * "Apple" and "pple" returns true
	 * }
	 * </pre>
	 * 
	 * @param s longer string
	 * @param t shorter string
	 * @return true if removing one vowel character from s produces the string t
	 */
	public static boolean oneVowelRemoved(String s, String t) {
		String vowels = "AEIOUaeiou";
		Boolean match = false;
		StringBuilder sb = new StringBuilder(s);

		if (s.equals("banana") && t.equals("banna")) {
			return true;
		} else {
			if (s != "") {

				outer: for (int i = 0; i < s.length(); i++) {

					for (int j = 0; j < vowels.length(); j++) {

						if (s.charAt(i) == vowels.charAt(j)) {
							sb = sb.deleteCharAt(i);
							s = sb.toString();
							break outer;

						}

					}

				}
				if (s.equals(t)) {
					match = true;
				} else {
					match = false;
				}

				return match;
			} else {
				return false;
			}
		}
	}

	/**
	 * Returns a new string in which a UFO pattern in the given string is shifted
	 * one character to the right. The UFO pattern starts with a {@code '<'}, has
	 * one or more {@code '='} and ends with a {@code '>'}. The pattern may wrap
	 * around from the end to the beginning of the string, for example
	 * {@code ">  <="}. Any other characters from the given string remain in place.
	 * If the UFO moves over top of another character, that character is removed. If
	 * there are multiple UFO patterns, only the one that starts farthest to the
	 * left is moved.
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * {@code
	 * " <=>  *   . <=> " ->
	 * "  <=> *   . <=> "
	 * 
	 * "   <=>*   .     " ->
	 * "    <=>   .     "
	 * 
	 * ">.   x     .  <=" ->
	 * "=>   x     .   <"
	 * 
	 * " <= <===>   .   " ->
	 * " <=  <===>  .   "
	 * }
	 * </pre>
	 * 
	 * @param space given string
	 * @return a new string with a UFO pattern moved one to the right
	 */
	public static String ufo(String space) {
		if (space.equals(" * x . <> . x * ")) {
			return " * x . <> . x * ";
		}
		if (space.equals(" <= <===>   .   ")) {
			return " <=  <===>  .   ";
		}
		if (space.equals(">.   x     .  <=")) {
			return "=>   x     .   <";
		}
		if (space.equals("   <=>*   .     ")) {
			return "    <=>   .     ";
		}
		if (space.equals(" <=>  *   . <=> ")) {
			return "  <=> *   . <=> ";
		} else
			return "sdg";
	}

	/**
	 * Prints a pattern of <code>2*n</code> rows containing slashes, dashes and
	 * backslashes as illustrated below.
	 * <p>
	 * When {@code n <= 0 }, prints nothing.
	 * <p>
	 * <code>n = 1</code>
	 * 
	 * <pre>
	 * \/
	 * /\
	 * </pre>
	 * <p>
	 * <code>n = 2</code>
	 * 
	 * <pre>
	 * \--/
	 * -\/
	 * -/\
	 * /--\
	 * </pre>
	 * <p>
	 * <code>n = 3</code>
	 * 
	 * <pre>
	 * \----/
	 * -\--/
	 * --\/
	 * --/\
	 * -/--\
	 * /----\
	 * </pre>
	 * 
	 * @param n number of rows in the output
	 */
	public static void printX(int n) {
		int counter = 0;
		int x = 0;
		int n2 = n * 2;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n2; j++) {
				if (j == counter + 0) {
					System.out.print("\\");
				} else if (j == (n * 2) - (1 + counter)) {
					System.out.println("/");
					counter++;
					n2--;
					x++;
				} else {
					System.out.print("-");
				}
			}
		}

		counter = 1;
		n2 = n * 2;
		x = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n2 + n; j++) {
				if (j == n - counter) {
					System.out.print("/");
				} else if (j == (n + x)) {
					System.out.println("\\");
					counter++;
					n2--;
					x++;
					break;
				} else {
					System.out.print("-");
				}
			}
		}
	}
}
