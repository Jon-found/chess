import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        int direction = isWhite() ? 1 : -1;

        if (col > 0) {
            controlledSquares.add(board[row + direction][col - 1]);
        }
        if (col < 7) {
            controlledSquares.add(board[row + direction][col + 1]);
        }
        return controlledSquares;
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int row = start.getRow();
        int col = start.getCol();
        int direction = isWhite() ? 1 : -1;

        // One step forward
        if (board[row + direction][col] != null && !board[row + direction][col].isOccupied()) {
            legalMoves.add(board[row + direction][col]);
        }
       
        // Two steps forward from start position
        if ((isWhite() && row == 1) || (!isWhite() && row == 6)) {
            if (!board[row + direction][col].isOccupied() && !board[row + 2 * direction][col].isOccupied()) {
                legalMoves.add(board[row + 2 * direction][col]);
            }
        }

        // Capturing diagonally
        if (col > 0 && board[row + direction][col - 1].isOccupied() && board[row + direction][col - 1].getOccupyingPiece().isWhite() != isWhite()) {
            legalMoves.add(board[row + direction][col - 1]);
        }
        if (col < 7 && board[row + direction][col + 1].isOccupied() && board[row + direction][col + 1].getOccupyingPiece().isWhite() != isWhite()) {
            legalMoves.add(board[row + direction][col + 1]);
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return (isWhite() ? "White" : "Black") + " Pawn";
    }
}