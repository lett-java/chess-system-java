package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {
	
	private ColorEnum color;

	public ChessPiece(Board board, ColorEnum color) {
		super(board);
		this.color = color;
	}

	public ColorEnum getColor() {
		return color;
	}
	
	

}
