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
import javax.swing.*;


@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
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


    private final Square[][] board;
    private final GameWindow g;
    private boolean whiteTurn;


    private Piece currPiece;
    private Square fromMoveSquare;
    private int currX;
    private int currY;


    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));


        this.addMouseListener(this);
        this.addMouseMotionListener(this);


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhite = (row + col) % 2 == 0;
                board[row][col] = new Square(this, isWhite, row, col);
                this.add(board[row][col]);
            }
        }


        initializePieces();


        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));


        whiteTurn = true;
    }

/**
 * Precondition: The board is an 8x8 grid of Square objects.
 * Postcondition: Pieces are placed in their correct starting positions.
 */

    private void initializePieces() {
        

        //Bisknight
        board[0][1].put(new Bisknight(true, RESOURCES_WKNIGHT_PNG));
        board[0][6].put(new Bisknight(true, RESOURCES_WKNIGHT_PNG));
        board[7][1].put(new Bisknight(false, RESOURCES_BKNIGHT_PNG));
        board[7][6].put(new Bisknight(false, RESOURCES_BKNIGHT_PNG));

        //king
        board[7][4].put(new KingButLessCode(false, RESOURCES_BKING_PNG));
        board[0][4].put(new KingButLessCode(true, RESOURCES_WKING_PNG));
        //Queen
        board[7][3].put(new Queen(false, RESOURCES_BQUEEN_PNG));
        board[0][3].put(new Queen(true, RESOURCES_WQUEEN_PNG));
        //bishops 
        board[7][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[0][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        //rock
        board[0][0].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[7][0].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[7][7].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(true, RESOURCES_WROOK_PNG));
        //pawns 
        int n=0;
        while (n<8) {
            board[1][n].put(new Pawn(true, RESOURCES_WPAWN_PNG));
            n++;
        }
        n=0;
        while (n<8) {
            board[6][n].put(new Pawn(false, RESOURCES_BPAWN_PNG));
            n++;
        }
    
      
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
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
            }
        }
        if (currPiece != null) {
            final Image img = currPiece.getImage();
            g.drawImage(img, currX, currY, null);
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
            if (!currPiece.isWhite() && whiteTurn) return;
            if (currPiece.isWhite() && !whiteTurn) return;
            sq.setDisplay(false);
        }
        repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
    
        if (currPiece == null || fromMoveSquare == null) return;
    
        if ((whiteTurn && !currPiece.isWhite()) || (!whiteTurn && currPiece.isWhite())) return;
    
        ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
        boolean isLegalMove = false;
    
        for (Square move : legalMoves) {
            if (move == endSquare) {
                isLegalMove = true;
                break;
            }
        }
    
        Piece capturedPiece = null;
        boolean moved = false;
    
        if (isLegalMove) {
            if (endSquare.isOccupied()) {
                capturedPiece = endSquare.getOccupyingPiece();
            }
            fromMoveSquare.removePiece();
            endSquare.put(currPiece);
    
            // If move puts own king in check, undo
            if (isInCheck(currPiece.isWhite())) {
                endSquare.removePiece();
                fromMoveSquare.put(currPiece);
                if (capturedPiece != null) {
                    endSquare.put(capturedPiece);
                }
            } else {
                moved = true;
                whiteTurn = !whiteTurn;
            }
        }
    
      
    
      
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setBorder(null);
            }
        }
    
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        fromMoveSquare = null;
    
        repaint();
    }
    


    
       
// Precondition: The board is initialized and contains a king of either color
// Postcondition: Returns true if the king is in check, false otherwise
public boolean isInCheck(boolean kingColor) {
    Square[][] board = this.getSquareArray();
    Square kingSquare = null;

    // Find the king of the given color
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board[row][col].getOccupyingPiece();
            if (piece instanceof KingButLessCode && piece.isWhite() == kingColor) {
                kingSquare = board[row][col];
                break;
            }
        }
    }

    // If no king is found (shouldn't happen), return false
    if (kingSquare == null) return false;

    // Check if any opponent piece controls the king's square
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board[row][col].getOccupyingPiece();
            if (piece != null && piece.isWhite() != kingColor) {
                ArrayList<Square> controlledSquares = piece.getControlledSquares(board, board[row][col]);
                if (controlledSquares.contains(kingSquare)) {
                    return true; // King is in check
                }
            }
        }
    }

    return false; // King is not in check
}

          

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        if (currPiece != null) {
            for (Square s : currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createLineBorder(Color.green));
            }
        }
        repaint();
    }


    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
