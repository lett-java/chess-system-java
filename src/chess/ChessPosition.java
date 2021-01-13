package chess;

import boardgame.Position;

public class ChessPosition {

	private static final String ERROR_COLUMN_AND_ROW = "Error instantiating ChessPosition. Valid values are from a1 to h8";
	private char column;
	private int row;

	public ChessPosition(char column, int row) {
		validator(column, row);
		this.column = column;
		this.row = row;
	}

	private void validator(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException(ERROR_COLUMN_AND_ROW);
		}
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}

}
