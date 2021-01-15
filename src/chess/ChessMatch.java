package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private static final String YOU_DONT_CAN = "You can't put yourself in check";
	private static final String KING_NOT_FOUND = "There is no %s king on the board";
	private static final String OPPONENT_PIECE = "The chosen piece is not yours";
	private static final String CANT_TARGET = "The chosen piece can't move to target position";
	private static final String NO_POSSIBLE_MOVES = "There is no possible moves for the chosen piece";
	private static final String NO_PIECE_POSITION = "There is no piece on source position";

	private int turn;
	private ColorEnum currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = ColorEnum.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public ColorEnum getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}

		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);

		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		validateSourcePosition(source);
		validateTargetPosition(source, target);

		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException(YOU_DONT_CAN);
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		return (ChessPiece) capturedPiece;
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == ColorEnum.WHITE) ? ColorEnum.BLACK : ColorEnum.WHITE;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);

		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException(NO_PIECE_POSITION);
		}

		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException(OPPONENT_PIECE);
		}

		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException(NO_POSSIBLE_MOVES);
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException(CANT_TARGET);
		}

	}

	private ColorEnum opponent(ColorEnum color) {
		return (color == ColorEnum.WHITE) ? ColorEnum.BLACK : ColorEnum.WHITE;
	}

	private ChessPiece king(ColorEnum color) {
		List<Piece> list = filtrarPecas(color);

		for (Piece piece : list) {
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException(String.format(KING_NOT_FOUND, color));
	}

	private boolean testCheck(ColorEnum color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = filtrarPecasOponente(color);
		
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private List<Piece> filtrarPecasOponente(ColorEnum color) {
		return piecesOnTheBoard
				.stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == opponent(color))
				.collect(Collectors.toList());
	}

	private boolean testCheckMate(ColorEnum color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = filtrarPecas(color);

		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private List<Piece> filtrarPecas(ColorEnum color) {
		return piecesOnTheBoard
				.stream()
				.filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, ColorEnum.WHITE));
		placeNewPiece('b', 1, new Knight(board, ColorEnum.WHITE));
		placeNewPiece('c', 1, new Bishop(board, ColorEnum.WHITE));
		placeNewPiece('d', 1, new Queen(board, ColorEnum.WHITE));
        placeNewPiece('e', 1, new King(board, ColorEnum.WHITE));
        placeNewPiece('f', 1, new Bishop(board, ColorEnum.WHITE));
        placeNewPiece('g', 1, new Knight(board, ColorEnum.WHITE));
        placeNewPiece('h', 1, new Rook(board, ColorEnum.WHITE));
        placeNewPiece('a', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('b', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('c', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('d', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('e', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('f', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('g', 2, new Pawn(board, ColorEnum.WHITE));
        placeNewPiece('h', 2, new Pawn(board, ColorEnum.WHITE));

        placeNewPiece('a', 8, new Rook(board, ColorEnum.BLACK));
        placeNewPiece('b', 8, new Knight(board, ColorEnum.BLACK));
        placeNewPiece('c', 8, new Bishop(board, ColorEnum.BLACK));
        placeNewPiece('d', 8, new Queen(board, ColorEnum.BLACK));
        placeNewPiece('e', 8, new King(board, ColorEnum.BLACK));
        placeNewPiece('f', 8, new Bishop(board, ColorEnum.BLACK));
        placeNewPiece('g', 8, new Knight(board, ColorEnum.BLACK));
        placeNewPiece('h', 8, new Rook(board, ColorEnum.BLACK));
        placeNewPiece('a', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('b', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('c', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('d', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('e', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('f', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('g', 7, new Pawn(board, ColorEnum.BLACK));
        placeNewPiece('h', 7, new Pawn(board, ColorEnum.BLACK));
	}

}
