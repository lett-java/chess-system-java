package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ColorEnum;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, ColorEnum color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		moveAbove(mat, p);
		moveBelow(mat, p);
		moveLeft(mat, p);
		moveRight(mat, p);
		moveNw(mat, p);
		moveNe(mat, p);
		moveSw(mat, p);
		moveSe(mat, p);
		
		validatorKingSideRook(mat);
		validatorQueenSideRook(mat);
		
		
		return mat;
	}

	private void validatorQueenSideRook(boolean[][] mat) {
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			Position posT1 = new Position(position.getRow(), position.getColumn() - 4);
			
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				
				if (getBoard().piece(p1) == null 
						&& getBoard().piece(p2) == null 
						&& getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
	}

	private void validatorKingSideRook(boolean[][] mat) {
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
		}
	}

	private void moveSe(boolean[][] mat, Position p) {
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveSw(boolean[][] mat, Position p) {
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveNe(boolean[][] mat, Position p) {
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveNw(boolean[][] mat, Position p) {
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveRight(boolean[][] mat, Position p) {
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveLeft(boolean[][] mat, Position p) {
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveBelow(boolean[][] mat, Position p) {
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void moveAbove(boolean[][] mat, Position p) {
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null 
				&& p instanceof Rook && p.getColor() == getColor() 
				&& p.getMoveCount() == 0;
	}

}
