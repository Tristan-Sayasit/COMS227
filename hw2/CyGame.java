package hw2;

/**
 * Model of a Monopoly-like game. Two players take turns rolling dice to move
 * around a board. The game ends when one of the players has at least
 * MONEY_TO_WIN money or one of the players goes bankrupt (has negative money).
 * 
 * @author TristanSayasit
 */
public class CyGame {
	/**
	 * Do nothing square type.
	 */
	public static final int DO_NOTHING = 0;
	/**
	 * Pass go square type.
	 */
	public static final int PASS_GO = 1;
	/**
	 * Cyclone square type.
	 */
	public static final int CYCLONE = 2;
	/**
	 * Pay the other player square type.
	 */
	public static final int PAY_PLAYER = 3;
	/**
	 * Get an extra turn square type.
	 */
	public static final int EXTRA_TURN = 4;
	/**
	 * Jump forward square type.
	 */
	public static final int JUMP_FORWARD = 5;
	/**
	 * Stuck square type.
	 */
	public static final int STUCK = 6;
	/**
	 * Points awarded when landing on or passing over go.
	 */
	public static final int PASS_GO_PRIZE = 200;
	/**
	 * The amount payed to the other player per unit when landing on a PAY_PLAYER
	 * square.
	 */
	public static final int PAYMENT_PER_UNIT = 20;
	/**
	 * The amount of money required to win.
	 */
	public static final int MONEY_TO_WIN = 400;
	/**
	 * The cost of one unit.
	 */
	public static final int UNIT_COST = 50;

	/**
	 * Number of square player 1 is on.
	 */
	private int player1Square;
	
	/**
	 * Amount of money player 1 currently has.
	 */
	private int player1Money;
	
	/**
	 * Number of property player 1 currently has.
	 */
	private int player1Property;

	/**
	 * Number of square player 2 is on.
	 */
	private int player2Square;
	
	/**
	 * Amount of money player 2 currently has.
	 */
	private int player2Money;
	
	/**
	 * Number of property player 2 currently has.
	 */
	private int player2Property;

	/**
	 * Total number of squares on the board.
	 */
	private int numSquares;
	
	/**
	 * Holds current players turn, either 1 or 2.
	 */
	private int playerTurn;
	
	/**
	 * Holds a number value that correlates to types of squares on the board.
	 */
	private int squareType;

	/**
	 * Constructs a game that has the given number of squares and starts both players on square 0. 
	 * Both players start with the given amount of money and 1 unit each. It is initially Player 1's turn to move.
	 * @param numSquare
	 * @param startingMoney
	 */
	public CyGame(int numSquare, int startingMoney) {
		numSquares = numSquare;
		player1Money = startingMoney;
		player2Money = startingMoney;
		playerTurn = 1;
		player1Property = 1;
		player2Property = 1;
		player1Square = 0;
		player2Square = 0;
		squareType = 0;
	}

	/**
	 * Method called to indicate the current player passes or completes their turn. 
	 * Change the current turn from Player 1 to Player 2 or vice versa.
	 */
	public void endTurn() {
		if (playerTurn == 1) {
			playerTurn = 2;
		} else if (playerTurn == 2) {
			playerTurn = 1;
		}
	}
	
	/**
	 * Get the current player.
	 * @return
	 * 1 if it is currently Player 1's turn, otherwise 2.
	 */
	public int getCurrentPlayer() {
		return playerTurn;
	}

	/**
	 * Get Player 1's money.
	 * @return
	 * Player 1's money.
	 */
	public int getPlayer1Money() {
		return player1Money;
	}

	/**
	 * Get Player 1's square.
	 * @return
	 * The square number.
	 */
	public int getPlayer1Square() {
		return player1Square;
	}

	/**
	 * Get Player 1's units.
	 * @return
	 * Player 1's units.
	 */
	public int getPlayer1Units() {
		return player1Property;
	}

	/**
	 * Get Player 2's money.
	 * @return
	 * Player 2's money.
	 */
	public int getPlayer2Money() {
		return player2Money;
	}

	/**
	 * Get Player 2's square.
	 * @return
	 * The square number.
	 */
	public int getPlayer2Square() {
		return player2Square;
	}

	/**
	 * Get Player 2's units.
	 * @return
	 * Player 2's units.
	 */
	public int getPlayer2Units() {
		return player2Property;
	}

	/**
	 * Get the type of square for the given square number. Each square is assigned a single type based on the following rules. 
	 * The rules are listed in order of highest to lowest precedence (i.e., when multiple rules match a square number, 
	 * apply the one higher on the list.
	 * @param square
	 * @return
	 * Type of square landed on by current player.
	 */
	public int getSquareType(int square) {
		if (square == 0) {
			squareType = PASS_GO;
		} else if (square == (numSquares - 1)) {
			squareType = CYCLONE;
		} else if (square % 5 == 0) {
			squareType = PAY_PLAYER;
		} else if ((square % 7 == 0) || (square % 11 == 0)) {
			squareType = EXTRA_TURN;
		} else if (square % 3 == 0) {
			squareType = STUCK;
		} else if (square % 2 == 0) {
			squareType = JUMP_FORWARD;
		} else {
			squareType = DO_NOTHING;
		}
		return squareType;
	}

	/**
	 * Returns true if game is over, false otherwise. The game is over when either player has at least MONEY_TO_WIN money or 
	 * either player has a negative amount of money.
	 * @return
	 * True if the game is over, false otherwise.
	 */
	public boolean isGameEnded() {
		if (player1Money >= MONEY_TO_WIN || player2Money >= MONEY_TO_WIN || player1Money < 0 || player2Money < 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method called to indicate the dice has been rolled. Advance the current player forward by a number of squares determined by the number rolled. 
	 * EXCEPTION: If the player is currently on a STUCK square they only move forward if the value rolled is even, otherwise their turn is over. 
	 * If the player passes over the PASS_GO square add PASS_GO_PRIZE to the player's money. Then apply the action of the square the player lands on. 
	 * This method does nothing if the game has ended.
	 * @param value
	 */
	public void roll(int value) {

		if (!isGameEnded()) {

			if (playerTurn == 1) {
				if (getSquareType(player1Square) == STUCK && value % 2 != 0) {
					endTurn();
				} else {
					player1Square += value;
					if (player1Square >= numSquares) {
						player1Square -= numSquares;
						if (player1Square != 0) {
							player1Money += PASS_GO_PRIZE;
						}
					}
				}
				
				squareType = getSquareType(player1Square);
				if (squareType == PASS_GO) {
					player1Money += PASS_GO_PRIZE;
				}
				else if (squareType == CYCLONE) {
					player1Square = player2Square;
				}
				else if (squareType == PAY_PLAYER) {
					player2Money += PAYMENT_PER_UNIT * player2Property;
					player1Money -= PAYMENT_PER_UNIT * player2Property;
				}
				else if (squareType == EXTRA_TURN) {
					endTurn();
				}
				else if (squareType == JUMP_FORWARD) {
					player1Square += 4;
					if (player1Square > numSquares) {
						player1Square -= numSquares;
						player1Money += PASS_GO_PRIZE;
					}
				}
				else if (squareType == DO_NOTHING) {

				}
			}

			if (playerTurn == 2) {
				if (getSquareType(player2Square) == STUCK && value % 2 != 0) {
					endTurn();
				} else {
					player2Square += value;
					if (player2Square >= numSquares) {
						player2Square -= numSquares;
						if (player2Square != 0) {
							player2Money += PASS_GO_PRIZE;
						}
					}
				}
				squareType = getSquareType(player2Square);
				if (squareType == PASS_GO) {
					player2Money += PASS_GO_PRIZE;
				}
				else if (squareType == CYCLONE) {
					player2Square = player1Square;
				}
				else if (squareType == PAY_PLAYER) {
					player1Money += PAYMENT_PER_UNIT * player1Property;
					player2Money -= PAYMENT_PER_UNIT * player1Property;
				}
				else if (squareType == EXTRA_TURN) {
					endTurn();
				}
				else if (squareType == JUMP_FORWARD) {
					player2Square += 4;
					if (player2Square > numSquares) {
						player2Square -= numSquares;
						player2Money += PASS_GO_PRIZE;
					}
				}
				else if (squareType == DO_NOTHING) {

				}
			}
			
			endTurn();
		}
	}
	
	/**
	 * Method called to indicate the current player attempts to buy one unit. 
	 * The purchase is only allowed if the player is currently on a square type of DO_NOTHING and 
	 * has sufficient money to buy one unit at UNIT_COST. If allowed, subtract the cost from the player's 
	 * money and increment the player's number of units by one. If the current player successfully buys a unit their 
	 * turn has ends (they are not allowed to take any further action or roll the dice). Update the turn to the other player. 
	 * This method does nothing if the game has ended.
	 */
	public void buyUnit() {
		if (!isGameEnded()) {
			if (playerTurn == 1) {
				if (getSquareType(player1Square) == DO_NOTHING) {
					if (player1Money >= UNIT_COST) {
						player1Money -= UNIT_COST;
						player1Property++;
						endTurn();
					}
				}
			} else if (playerTurn == 2) {
				if (getSquareType(player2Square) == DO_NOTHING) {
					if (player2Money >= UNIT_COST) {
						player2Money -= UNIT_COST;
						player2Property++;
						endTurn();
					}
				}
			}
		}
	}

	/**
	 * Method called to indicate the current player attempts to sell one unit. 
	 * The sale is only allowed only if the player has at least one unit to sell. 
	 * If allowed, pay the current player UNIT_COST and decrease the player's number of units by one. 
	 * If the current player successfully sells a unit their turn ends (they are not allowed to take any 
	 * further action or roll the dice). Update the turn to the other player. This method does nothing if the game has ended.
	 */
	public void sellUnit() {
		if (!isGameEnded()) {
			if (playerTurn == 1) {
				if (player1Property > 0) {
					player1Money += UNIT_COST;
					player1Property--;
					endTurn();
				}
			} else if (playerTurn == 2) {
				if (player2Property > 0) {
					player2Money += UNIT_COST;
					player2Property--;
					endTurn();
				}
			}
		}
	}

	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player 1*: (0, 0, $0) Player 2: (0, 0, $0)</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which players turn it is.
	 * The numbers (0, 0, $0) indicate which square the player is on, how many units
	 * the player has, and how much money the player has respectively.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
		String player1Turn = "";
		String player2Turn = "";
		if (getCurrentPlayer() == 1) {
			player1Turn = "*";
		} else {
			player2Turn = "*";
		}
		return String.format(fmt, player1Turn, getPlayer1Square(), getPlayer1Units(), getPlayer1Money(), player2Turn,
				getPlayer2Square(), getPlayer2Units(), getPlayer2Money());
	}
}
