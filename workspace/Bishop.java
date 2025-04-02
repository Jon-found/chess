import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
//you will need to implement two functions in this file.
public class Bishop extends Piece {
public Bishop(boolean isWhite, String img_file) {
super(isWhite, img_file);
}
@Override
public String toString(){
return "A " + super.toString() + " bishop";
}
// TO BE IMPLEMENTED!
//return a list of every square that is "controlled" by this piece. A square is

//if the piece capture into it legally.
public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
ArrayList<Square> controlledSquares = new ArrayList<>();
int startRow = start.getRow();
int startCol = start.getCol();
int[] rowDirections = {-1, -1, 1, 1};
int[] colDirections = {-1, 1, -1, 1};
for (int direction = 0; direction < 4; direction++) {
int row = startRow;
int col = startCol;
while (true) {
row += rowDirections[direction];
col += colDirections[direction];
if (row < 0 || row >= 8 || col < 0 || col >= 8) break;
Square square = board[row][col];
if (square.isOccupied()) {
controlledSquares.add(square);
if (square.getOccupyingPiece().isWhite() != this.isWhite()) {
break;
}
} else {
controlledSquares.add(square);
}
}
}
return controlledSquares;
}
//TO BE IMPLEMENTED!
//implement the move function here
//it's up to you how the piece moves, but at the very least the rules should be

//going to score any points.
//This method gets the legal moves of a bishop, moving diagonal (up one square

public ArrayList<Square> getLegalMoves(Board b, Square start){
ArrayList<Square> legalMoves = new ArrayList<>();
int startRow = start.getRow();
int startCol = start.getCol();
//Up-Left Movement
for (int i = 1; startRow - i >= 0 && startCol - i >= 0; i++) {
Square sq = b.getSquareArray()[startRow - i][startCol - i];
if (sq.isOccupied()) {
if (sq.getOccupyingPiece().isWhite() != this.isWhite()) {
legalMoves.add(sq);
}
break;
}
legalMoves.add(sq);
}
//Up-Right Movement
for (int i = 1; startRow - i >= 0 && startCol + i < 8; i++) {
Square sq = b.getSquareArray()[startRow - i][startCol + i];
if (sq.isOccupied()) {
if (sq.getOccupyingPiece().isWhite() != this.isWhite()) {
legalMoves.add(sq);
}
break;
}
legalMoves.add(sq);
}
//Down-Left Movement
for (int i = 1; startRow + i < 8 && startCol - i >= 0; i++) {
Square sq = b.getSquareArray()[startRow + i][startCol - i];
if (sq.isOccupied()) {
if (sq.getOccupyingPiece().isWhite() != this.isWhite()) {
legalMoves.add(sq);
}
break;
}
legalMoves.add(sq);
}
//Down-Right Movement
for (int i = 1; startRow + i < 8 && startCol + i < 8; i++) {
Square sq = b.getSquareArray()[startRow + i][startCol + i];
if (sq.isOccupied()) {
if (sq.getOccupyingPiece().isWhite() != this.isWhite()) {
legalMoves.add(sq);
}
break;
}
legalMoves.add(sq);
}
return legalMoves;
}
}