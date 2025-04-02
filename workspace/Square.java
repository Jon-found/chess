
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;


@SuppressWarnings("serial")
public class Square extends JComponent {
   
    private Board b;
  
    private final boolean color;
    
   
    private Piece occupyingPiece;
  
    private boolean dispPiece;
  
    
    private int row;
    private int col;
  
    public Square(Board b, boolean isWhite, int row, int col) {
        this.b = b;
        this.color = isWhite;
        this.dispPiece = true;
        this.row = row;
        this.col = col;
      
        this.setBorder(BorderFactory.createEmptyBorder());
    }
  
    public boolean getColor() {
        return this.color;
    }
  
    
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }
  
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
  
    public int getRow() {
        return this.row;
    }
  
    public int getCol() {
        return this.col;
    }
  
    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
  
    
    public void put(Piece p) {
        this.occupyingPiece = p;
    }
  
    public Piece removePiece() {
        Piece p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
      
        if (this.color) {
            g.setColor(new Color(221,192,127));
        } else {
            g.setColor(new Color(101,67,33));
        }
      
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
      
        if (occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g, this);
        }
    }
}
