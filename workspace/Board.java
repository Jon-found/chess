//Jonathan Koushan, pd 1, 3/6/25
//makes a board and places them onto the board with vizuals, highlights moves and should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

   // Preconditions: The board object exists.No pieces are currently placed on the board.
   //Postconditions: The board is initialized with an 8x8 grid. Each square is correctly assigned a color (light/dark). Rows and columns are correctly labeled

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
     
      for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhite = (row + col) % 2 == 0; // Alternating colors
                board[row][col] = new Square(this, isWhite, row, col);
                this.add(board[row][col]); // Add square to the board
            }
        }
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
   // Initialize the board with only one type of piece (pawns)
   //Preconditions: The board has been set up correctly. No pieces have been placed yet.
   //Postconditions: Each piece is placed in its correct starting position. Both white and black sides are mirrored properly. Each piece is assigned to its correct team (white/black).

   private void initializePieces() {
    // Place white pawns on row 1
    for (int col = 0; col < 8; col++) {
        board[1][col].put(new Piece(true, RESOURCES_WPAWN_PNG)); // White pieces
    }

    //place white special knights (the bisknight)(defualt skin)
      board[0][1].put(new Piece(true,RESOURCES_WKNIGHT_PNG));board[0][6].put(new Piece(true,RESOURCES_WKNIGHT_PNG));
       //place black special knights (the bisknight)(defualt skin)
      board[7][1].put(new Piece(false,RESOURCES_BKNIGHT_PNG));board[7][6].put(new Piece(false,RESOURCES_BKNIGHT_PNG));

    // Place black pawns on row 6
    for (int col = 0; col < 8; col++) {
        board[6][col].put(new Piece(false, RESOURCES_BPAWN_PNG)); // Black pieces
    }
    //make king 
    board[0][3].put(new Piece(true,RESOURCES_WKING_PNG));board[7][3].put(new Piece(false,RESOURCES_BKING_PNG));
}

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn)
                return;
            if (currPiece.getColor() && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    
    //Preconditions: A piece has been selected, and the player attempts to move it.The destination square is determined.
    //Postconditions: If the move is legal, the piece is moved to the new square. If a piece is captured, it is removed from the board. The game state updates, including switching turns.

    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
    
        // If no piece was selected, return early
        if (currPiece == null || fromMoveSquare == null) {
            return;
        }
    
        // Enforce turn order
        if ((whiteTurn && !currPiece.getColor()) || (!whiteTurn && currPiece.getColor())) {
            // It's not this player's turn
            return;
        }
    
        // Get all legal moves for the current piece
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
    
        // Manually check if endSquare is a legal move
        boolean isLegalMove = false;
        for (int i = 0; i < legalMoves.size(); i++) {
            if (legalMoves.get(i) == endSquare) {
                isLegalMove = true;
                break;
            }
        }
    
        if (isLegalMove && (endSquare == null || !endSquare.isOccupied() || 
            endSquare.getOccupyingPiece().getColor() != currPiece.getColor())) {
            
            // Move piece to the new location
            fromMoveSquare.removePiece();
            endSquare.put(currPiece);
    
            // Switch turns
            whiteTurn = !whiteTurn;
        } else {
            // Illegal move, snap back to original square
            fromMoveSquare.put(currPiece);
        }
    
        // Reset visual indicators using a normal loop
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setBorder(null);
            }
        }
    
        // Ensure the piece remains displayed
        fromMoveSquare.setDisplay(true);
    
        // Reset selection
        currPiece = null;
        fromMoveSquare = null;
    
        repaint();
    }
    
    


    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        if(currPiece!=null){
            for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)){
                s.setBorder(BorderFactory.createLineBorder(Color.green));
            }



        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}