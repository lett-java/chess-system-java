package boardgame;

public class Board {

	private static final String ERROR_CREATING_BOARD = "Error creating board: there must be at least 1 row and 1 column";
	private static final String PIECE_POSITION = "There is already a piece on position ";
	private static final String POSITION = "Position not on the board";
	
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		validatorRowAndColumn(rows, columns);
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException(POSITION);
		}
		
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		validatorPosition(position);
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException(PIECE_POSITION + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
		
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		validatorPosition(position);
		return piece(position) != null;
	}
	
	public Piece removePiece(Position position) {
		validatorPosition(position);
		if (piece(position) == null) {
			return null;
		}
		
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		
		return aux;
		
		
		
	}
	
	private void validatorPosition(Position position) {
		if (!positionExists(position)) {
			throw new BoardException(POSITION);
		}
	}
	
	private void validatorRowAndColumn(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException(ERROR_CREATING_BOARD);
		}
	}

}
