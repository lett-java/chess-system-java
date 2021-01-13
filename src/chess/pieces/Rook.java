package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.ColorEnum;

public class Rook extends ChessPiece {

	public Rook(Board board, ColorEnum color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

}
