package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ColorEnum;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, ColorEnum color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
		
		if (position.getRow() == 4) {
			Position left = new Position(position.getRow(), position.getColumn() - 1);
			if(getBoard().positionExists(left) 
					&& isThereOpponentPiece(left) 
					&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
				mat[left.getRow() + 1][left.getColumn()] = true;
			}
			
			Position right = new Position(position.getRow(), position.getColumn() + 1);
			if(getBoard().positionExists(right) 
					&& isThereOpponentPiece(right) 
					&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
				mat[right.getRow() + 1][right.getColumn()] = true;
			}
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

		if (position.getRow() == 3) {
			Position left = new Position(position.getRow(), position.getColumn() - 1);
			if (getBoard().positionExists(left) && isThereOpponentPiece(left)
					&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
				mat[left.getRow() - 1][left.getColumn()] = true;
			}

			Position right = new Position(position.getRow(), position.getColumn() + 1);
			if (getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
				mat[right.getRow() - 1][right.getColumn()] = true;
			}
		}
	}

	@Override
	public String toString() {
		return "P";
	}

}
