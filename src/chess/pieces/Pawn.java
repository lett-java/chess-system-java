package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.ColorEnum;

public class Pawn extends ChessPiece {

	public Pawn(Board board, ColorEnum color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		if (getColor() == ColorEnum.WHITE) {
			validarMovementsPawnWhite(mat, p);
		} else {
			validarMovementsPawnBlack(mat, p);
		}

		return mat;
	}

	private void validarMovementsPawnBlack(boolean[][] mat, Position p) {
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 2, position.getColumn());
		Position p2 = new Position(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
				&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private void validarMovementsPawnWhite(boolean[][] mat, Position p) {
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 2, position.getColumn());
		Position p2 = new Position(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
				&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
