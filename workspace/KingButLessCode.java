//Created by Jack Wang
//KingButLessCode
//Basically a regular King but less code than the given version
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
//you will need to implement two functions in this file.
public class KingButLessCode extends Piece
{
    
public KingButLessCode(boolean isWhite, String img_file) {
super(isWhite, img_file);
}
// TO BE IMPLEMENTED!
//return a list of every square that is "controlled" by this piece. A square is

//if the piece capture into it legally.
//Pre-condition: board is the same board that is being used and displayed,

//Post-condition: return an arraylist of all squares controlled
public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
int xPos = start.getRow();
int yPos = start.getCol();
ArrayList<Square> legalSquares = new ArrayList<Square>();
//loop the board
for (int i = -1; i <= 1; i++)
{
for (int j = -1; j <= 1; j++)
{
//check if the position values exceeded board size(s), then add

if ((xPos + i) < 8 && -1 < (xPos + i) && (yPos + j) < 8 && -1 <
(yPos + j))
{
legalSquares.add(board[xPos + i][yPos + j]);
}
}
}
//remove the square the piece is currently on
for (int i = 0; i < legalSquares.size(); i++)
{
//check if the legalSquares contain the start square
if (legalSquares.get(i) == start)
{
legalSquares.remove(i);
}
}
return legalSquares;
}
//TO BE IMPLEMENTED!
//implement the move function here
//it's up to you how the piece moves, but at the very least the rules should be

//returns an arraylist of squares which are legal to move to
//please note that your piece must have some sort of logic. Just being able to

//going to score any points.
//King: can be moved any where with in one square of reach, horizontally,

//Pre-condition: Board is the same board that is being used and displayed,

//Post-condition: return an arraylist of all squares a piece could moved to
public ArrayList<Square> getLegalMoves(Board b, Square start){
Square [][] board = b.getSquareArray();
int xPos = start.getRow();
int yPos = start.getCol();
ArrayList<Square> legalSquares = new ArrayList<Square>();
//loop the board
for (int i = -1; i <= 1; i++)
{
for (int j = -1; j <= 1; j++)
{
//check if the position values exceeded board size(s), then add

if ((xPos + i) < 8 && -1 < (xPos + i) && (yPos + j) < 8 && -1 <
(yPos + j) && board[xPos + i][yPos + j].isOccupied() == false)
{
legalSquares.add(board[xPos + i][yPos + j]);
}
//check if the position values exceeded board size(s), then add

else if ((xPos + i) < 8 && -1 < (xPos + i) && (yPos + j) < 8 && -
1 < (yPos + j) && board[xPos + i][yPos + j].isOccupied() == true &&
(start.getOccupyingPiece().isWhite() != board[xPos + i][yPos +
j].getOccupyingPiece().isWhite()))
{
legalSquares.add(board[xPos + i][yPos + j]);
}
}
}
return legalSquares;
}
public String toString()
{
return "A " + super.toString() + " King";
}
}
