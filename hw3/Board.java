package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Direction;
import api.Move;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left corner of the board.
	 */
	private Cell[][] grid;
	private Cell[][] originalGrid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory;
	
	private int grabbedBlock;
	private int row;
	private int col;

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		this.grid = grid;
		this.blocks = blocks;
		originalGrid = new Cell[grid.length][grid[0].length];
		this.moveHistory = new ArrayList<Move>();
	
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				for (int k = 0; k < blocks.size(); k++) {
					if (blocks.get(k).getFirstCol() == j && blocks.get(k).getFirstRow() == i) {
						if (blocks.get(k).getOrientation() == HORIZONTAL) {
							for (int l = j; l < blocks.get(k).getLength() + j; l++) {
								grid[i][l].setBlock(blocks.get(k));
							} 
						} else if (blocks.get(k).getOrientation() == VERTICAL){
							for (int m = i; m < blocks.get(k).getLength() + i; m++) {
								grid[m][j].setBlock(blocks.get(k));
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isExit()) {
					originalGrid[i][j] = new Cell(CellType.EXIT, i , j);
				}else if (grid[i][j].isFloor()) {
					originalGrid[i][j] = new Cell(CellType.FLOOR, i ,j);
				}else {
					originalGrid[i][j] = new Cell(CellType.WALL, i , j);
				}
				if (grid[i][j].hasBlock()) {
					originalGrid[i][j].setBlock(grid[i][j].getBlock());
				}
			}
		}
	}

	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		
		this.row = row;
		this.col = col;
		
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).getFirstCol() == col && blocks.get(i).getFirstRow() == row) {
				grabbedBlock = i;
			}
		}
	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		getGrabbedBlock().equals(null);
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		return blocks.get(grabbedBlock);
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {
		return grid[row][col];
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		if (grid[row][col].isFloor() || grid[row][col].isExit()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		return moveHistory.size();
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid[0].length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return grid.length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j].isExit()) {
					if (grid[i][j].hasBlock()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		
		Block b = getGrabbedBlock();
		
		if (b.getOrientation() == HORIZONTAL) {
			if (dir.equals(LEFT)) {
				if (canPlaceBlock(b.getFirstRow(), b.getFirstCol() - 1)) {
					b.move(LEFT);
					grid[b.getFirstRow()][b.getFirstCol() + b.getLength()].clearBlock();
					grid[b.getFirstRow()][b.getFirstCol()].setBlock(b);
					moveHistory.add(new Move(b, LEFT));
				}
			}
		
			if (dir.equals(RIGHT)) {
				if (canPlaceBlock(b.getFirstRow(), b.getFirstCol() + b.getLength())) {
					b.move(RIGHT);
					grid[b.getFirstRow()][b.getFirstCol() - 1].clearBlock();
					grid[b.getFirstRow()][b.getFirstCol() + b.getLength() - 1].setBlock(b);
					moveHistory.add(new Move(b, RIGHT));
				}
			}
		}
		
		if (b.getOrientation() == VERTICAL) {
			if (dir.equals(UP)) {
				if (canPlaceBlock(b.getFirstRow() - 1, b.getFirstCol())) {
					b.move(UP);
					grid[b.getFirstRow() + b.getLength()][b.getFirstCol()].clearBlock();
					grid[b.getFirstRow()][b.getFirstCol()].setBlock(b);
					moveHistory.add(new Move(b, UP));
				}
			}
			
			if (dir.equals(DOWN)) {
				if (canPlaceBlock(b.getFirstRow() + b.getLength(), b.getFirstCol())) {
					b.move(DOWN);
					grid[b.getFirstRow() - 1][b.getFirstCol()].clearBlock();
					grid[b.getFirstRow() + b.getLength() - 1][b.getFirstCol()].setBlock(b);
					moveHistory.add(new Move(b, DOWN));
				}
			}
		}
	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	public void reset() {
		moveHistory.clear();
		
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i) != null) {
				blocks.get(i).reset();
			}
		}
		for (int i = 0; i < originalGrid.length; i++) {
			for (int j = 0; j < originalGrid[0].length; j++) {
				grid[i][j].clearBlock();
			}
		}

		for (int i = 0; i < originalGrid.length; i++) {
			for (int j = 0; j < originalGrid[0].length; j++) {
				if (originalGrid[i][j].hasBlock()) {
					grid[i][j].setBlock(originalGrid[i][j].getBlock());
				}
			}
		}
		
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {

		ArrayList<Move> moves = new ArrayList<>();
		
		if (!isGameOver()) {
			for (int i = 0; i < blocks.size(); i++) {
				if (blocks.get(i).getOrientation() == VERTICAL) {
					if (canPlaceBlock(blocks.get(i).getFirstRow() - 1, blocks.get(i).getFirstCol())) {
						moves.add(new Move(blocks.get(i), UP));
					}
					
					if (canPlaceBlock(blocks.get(i).getFirstRow() - 1, blocks.get(i).getFirstCol())) {
						moves.add(new Move(blocks.get(i), DOWN));
					}
				} else if (blocks.get(i).getOrientation() == HORIZONTAL) {
					if (canPlaceBlock(blocks.get(i).getFirstRow(), blocks.get(i).getFirstCol() + blocks.get(i).getLength())) {
						moves.add(new Move(blocks.get(i), RIGHT));
					}
					
					if (canPlaceBlock(blocks.get(i).getFirstRow(), blocks.get(i).getFirstCol() - 1)) {
						moves.add(new Move(blocks.get(i), LEFT));
					}
				}
			}
		}
		return moves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
