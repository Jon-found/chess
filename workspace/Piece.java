

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public  class Piece {
    private boolean isWhite;
    protected String pieceType;
    private BufferedImage image;


    public Piece(boolean isWhite, String imgFile) {
        this.isWhite = isWhite;
        try {
            image = ImageIO.read(getClass().getResource(imgFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isWhite() {
        return isWhite;
    }


    public BufferedImage getImage() {
        return image;
    }


    public  ArrayList<Square> getControlledSquares(Square [][] arr, Square start){
        return null;

    }


    public  ArrayList<Square> getLegalMoves(Board board, Square start){
        return null;
    }


    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.image, x, y, null);
        }


    @Override
    /**
    * Precondition: None.
    * Postcondition: Returns a string describing the Bisknight's color and type.
    */
   
    public String toString() {
        String color;
        if (isWhite) {
            color = "white";
        } else {
            color = "black";
        }
        return color;

    }
}
