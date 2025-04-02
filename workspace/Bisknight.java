// Jonathan Koushan, pd 1, 3/6/25
//BisKnight
// Implements a chess piece that moves like a knight (all the time) but can also move backward like a bishop.
import javax.imageio.ImageIO;
import java.io.IOException;


import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Precondition: isWhite and imgFile are valid inputs.
 * Postcondition: A Bisknight piece is created with the given color and image.
 */

public class Bisknight extends Piece {


    public Bisknight(boolean isWhite, String imgFile) {
        super(isWhite, imgFile);
        this.pieceType="Bisknight";
    }


    public String toString(){
        return "A "+super.toString()+" Bisknight";
    }
/**
 * Precondition: Board and starting square are valid.
 * Postcondition: Returns a list of squares controlled by the Bisknight.
 */

    @Override
    public ArrayList<Square> getControlledSquares(Square [][] squares, Square start) {

        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();


        // Knight-like control (L-shaped moves)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };


        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];


            if (isInBounds(newRow, newCol)) {
                controlledSquares.add(squares[newRow][newCol]);
            }
        }


        // Backward bishop-like control (diagonal backward moves)
        int[] rowMoves = {-1, -1, 1, 1};
        int[] colMoves = {-1, 1, -1, 1};


        for (int i = 0; i < 4; i++) {
            int newRow = row + rowMoves[i];
            int newCol = col + colMoves[i];


            // Skip forward bishop moves
            if ((isWhite() && rowMoves[i] > 0) || (!isWhite() && rowMoves[i] < 0)) {
                continue;
            }


            while (isInBounds(newRow, newCol)) {
                controlledSquares.add(squares[newRow][newCol]);


                if (squares[newRow][newCol].isOccupied()) {
                    break; // Stop if another piece is blocking
                }


                newRow += rowMoves[i];
                newCol += colMoves[i];
            }
        }


        return controlledSquares;
    }

/**
 * Precondition: Board and starting square are valid.
 * Postcondition: Returns a list of legal moves for the Bisknight.
 */

    @Override
    public ArrayList<Square> getLegalMoves(Board board, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] squares = board.getSquareArray();


        // Knight Moves (L-shape)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };


        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];


            if (isInBounds(newRow, newCol)) {
                Square sq = squares[newRow][newCol];
                if (!sq.isOccupied() || sq.getOccupyingPiece().isWhite() != isWhite()) {
                    moves.add(sq);
                }
            }
        }


        // Backward Bishop Moves
        int[] rowMoves = {-1, -1, 1, 1};
        int[] colMoves = {-1, 1, -1, 1};


        for (int i = 0; i < 4; i++) {
            int newRow = row + rowMoves[i];
            int newCol = col + colMoves[i];


            // Skip forward bishop moves
            if ((isWhite() && rowMoves[i] > 0) || (!isWhite() && rowMoves[i] < 0)) {
                continue;
            }


            while (isInBounds(newRow, newCol)) {
                Square sq = squares[newRow][newCol];


                if (sq.isOccupied()) {
                    if (sq.getOccupyingPiece().isWhite() != isWhite()) {
                        moves.add(sq);
                    }
                    break;
                }


                moves.add(sq);
                newRow += rowMoves[i];
                newCol += colMoves[i];
            }
        }


        return moves;
    }


    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }


    @Override
    public void draw(Graphics g, Square currentSquare) {
        g.drawImage(getImage(), currentSquare.getX(), currentSquare.getY(), null);
    }
}
