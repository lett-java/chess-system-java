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

}
