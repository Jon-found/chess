//pawn bishop
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;

    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();

        g.drawImage(this.img, x, y, null);
    }
//Preconditions: The board and pieces are initialized. The function is given a piece and its current position.
//Postconditions: The function returns a list of all squares controlled by the piece. This includes squares attacked by the piece even if moving there isn’t allowed. Board edges and blocking pieces are considered.

    // Returns a list of every square controlled by this piece (where it can capture)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();

        // Knight-like control (L-shaped moves)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int i = 0; i < knightMoves.length; i++) {
            int newRow = row + knightMoves[i][0];
            int newCol = col + knightMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                controlledSquares.add(board[newRow][newCol]); 
            }
        }

        // Backward bishop-like control (diagonal backward moves)
        int[] rowMoves = {-1, -1, 1, 1}; 
        int[] colMoves = {-1, 1, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newRow = row + rowMoves[i];
            int newCol = col + colMoves[i];

            // Prevent forward bishop moves
            boolean isWhite = this.color;
            if ((isWhite && rowMoves[i] > 0) || (!isWhite && rowMoves[i] < 0)) {
                continue;
            }

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                controlledSquares.add(board[newRow][newCol]);

                if (board[newRow][newCol].isOccupied()) {
                    break; // Stop if another piece is blocking
                }

                newRow += rowMoves[i];
                newCol += colMoves[i];
            }
        }

        return controlledSquares;
    }
    //Preconditions: The board and pieces are correctly initialized. The function is given a piece and its current position.
//Postconditions: The function returns all valid moves for the piece. Illegal moves (out of bounds, moving through pieces incorrectly, etc.) are excluded. Special moves (castling, en passant, pawn promotion) are handled if applicable.

    // Returns an arraylist of legal moves for the bisknight
    //the "bisknight" can move like a normnal knight all the time but it also has the ability to move backwards like a bishop additonally 
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] board = b.getSquareArray();

        // Knight Moves (L-shape)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int i = 0; i < knightMoves.length; i++) {
            int newRow = row + knightMoves[i][0];
            int newCol = col + knightMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Square sq = board[newRow][newCol];
                if (!sq.isOccupied() || sq.getOccupyingPiece().getColor() != this.color) {
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

            boolean isWhite = this.color;
            if ((isWhite && rowMoves[i] > 0) || (!isWhite && rowMoves[i] < 0)) {
                continue;
            }

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Square sq = board[newRow][newCol];

                if (sq.isOccupied()) {
                    if (sq.getOccupyingPiece().getColor() != this.color) {
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
}
