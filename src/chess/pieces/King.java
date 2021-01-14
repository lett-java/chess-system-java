package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.ColorEnum;

public class King extends ChessPiece {

	public King(Board board, ColorEnum color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}

}
