package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class ChessSet extends HashSet<Piece> {

    public boolean whitesMove;
    private Piece pieceFound;
    private Piece pieceMoved;
    private Piece pieceCaptured;
    private boolean capture;

    public ChessSet() {
        super();
        this.add(new Rook(0, 0, true));
        this.add(new Knight(1, 0, true));
        this.add(new Bishop(2, 0, true));
        this.add(new Queen(3, 0, true));
        this.add(new King(4, 0, true));
        this.add(new Bishop(5, 0, true));
        this.add(new Knight(6, 0, true));
        this.add(new Rook(7, 0, true));
        for (int i = 0; i <= 7; i++) {
            this.add(new Pawn(i, 1, true));
        }
        this.add(new Rook(0, 7, false));
        this.add(new Knight(1, 7, false));
        this.add(new Bishop(2, 7, false));
        this.add(new Queen(3, 7, false));
        this.add(new King(4, 7, false));
        this.add(new Bishop(5, 7, false));
        this.add(new Knight(6, 7, false));
        this.add(new Rook(7, 7, false));
        for (int i = 0; i <= 7; i++) {
            this.add(new Pawn(i, 6, false));
        }
        this.whitesMove = true;
    }

    public void move(int fromFile, int fromRank, int toFile, int toRank) throws ChessRulesException, IOException {

        if (fromFile == toFile && fromRank == toRank) {
            throw new ChessRulesException("One piece have to be moved.");
        }
        this.capture = false;
        if (getPiece(fromFile, fromRank)) {
            if (this.pieceFound.white == this.whitesMove) {
                this.pieceMoved = pieceFound;
            } else {
                throw new ChessRulesException("There's an opponents piece on this square.");
            }
        } else {
            throw new ChessRulesException("There's no piece on this square.");
        }
        if (getPiece(toFile, toRank)) {
            if (this.pieceFound.white == this.whitesMove) {
                throw new ChessRulesException("Capturing own piece is not permitted.");
            } else {
                this.capture = true;
                this.pieceCaptured = this.pieceFound;
            }
        }
        if (this.pieceMoved instanceof Pawn) {
            if (!this.capture) {
                this.InPassingCheck(fromFile, fromRank, toFile, toRank, this.pieceMoved.white);
                this.pieceOnPathCheck(fromFile, fromRank, toFile, toRank);
            }
            Pawn pawnMoved = (Pawn) this.pieceMoved;
            pawnMoved.move(toFile, toRank, this.capture);
            if ((this.pieceMoved.white && toRank == 7)
                    || (!this.pieceMoved.white && toRank == 0)) {
                this.pawnPromotion();
            }
        } else if (this.pieceMoved instanceof King){
            King kingMoved = (King) this.pieceMoved;
            boolean castling = this.castlingCheck(kingMoved, fromFile, fromRank, toFile, toRank);
            kingMoved.move(toFile, toRank, castling);
        } else {
            this.pieceOnPathCheck(fromFile, fromRank, toFile, toRank);
            this.pieceMoved.move(toFile, toRank, false);
        }
        Iterator<Piece> iterator = this.iterator();
        do {
            Piece piece = iterator.next();
            if (piece instanceof Pawn && piece.white == !this.whitesMove) {
                Pawn pawn = (Pawn) piece;
                pawn.twoSquareMove = false;
            }
        }
        while (iterator.hasNext());
        if (this.capture) {
            this.remove(this.pieceCaptured);
        }
        this.whitesMove = !this.whitesMove;
    }

    private boolean getPiece(int file, int rank) {

        Iterator<Piece> iterator = this.iterator();
        Piece piece;
        boolean isFound = false;
        do {
            piece = iterator.next();
            if (piece.file == file
                    && piece.rank == rank) {
                isFound = true;
                break;
            }
        }
        while (iterator.hasNext());
        if (isFound) {
            this.pieceFound = piece;
        }
        return isFound;
    }

    private void pieceOnPathCheck(int fromFile, int fromRank, int toFile, int toRank) throws ChessRulesException {

        if (fromFile == toFile) {
            for (int i = Math.min(fromRank, toRank) + 1;
                 i < Math.max(fromRank, toRank); i++) {
                if (getPiece(fromFile, i)) {
                    throw new ChessRulesException("There's another piece on the moved piece path.");
                }
            }
        }
        if (fromRank == toRank) {
            for (int i = Math.min(fromFile, toFile) + 1;
                 i < Math.max(fromFile, toFile); i++) {
                if (getPiece(i, fromRank)) {
                    throw new ChessRulesException("There's another piece on the moved piece path.");
                }
            }
        }
        if (fromFile - fromRank
                == toFile - toRank) {
            for (int i = Math.min(fromFile, toFile) + 1;
                 i < Math.max(fromFile, toFile); i++) {
                if (getPiece(i, i - fromFile + fromRank)) {
                    throw new ChessRulesException("There's another piece on the moved piece path.");
                }
            }
        }
        if (fromFile + fromRank
                == toFile + toRank) {
            for (int i = Math.min(fromFile, toFile) + 1;
                 i < Math.max(fromFile, toFile); i++) {
                if (getPiece(i, fromFile + fromRank - i)) {
                    throw new ChessRulesException("There's another piece on the moved piece path.");
                }
            }
        }
    }
    
    private void InPassingCheck(int fromFile, int fromRank, int toFile, int toRank, boolean white) {
        
        if (white) {
            if (fromRank == 4 && toRank == 5
                    && Math.abs(toFile - fromFile) == 1) {
                if (getPiece(toFile, toRank - 1)) {
                    if (!this.pieceFound.white
                            && this.pieceFound instanceof Pawn) {
                        Pawn pawn = (Pawn) pieceFound;
                        if (pawn.twoSquareMove) {
                            this.capture = true;
                            this.pieceCaptured = this.pieceFound;
                        }
                    }
                }
            }
        } else {
            if (fromRank == 3 && toRank == 2
                    && Math.abs(toFile - fromFile) == 1) {
                if (getPiece(toFile, toRank + 1)) {
                    if (this.pieceFound.white
                            && this.pieceFound instanceof Pawn) {
                        Pawn pawn = (Pawn) pieceFound;
                        if (pawn.twoSquareMove) {
                            this.capture = true;
                            this.pieceCaptured = this.pieceFound;
                        }
                    }
                }
            }
        }
    }
    
    private void pawnPromotion() throws IOException{

        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isChosen = false;
        while (!isChosen) {
            System.out.println("Pawn promoted to a piece:");
            String notation = br.readLine();
            switch (notation) {
                case ("Q"):
                    this.add(new Queen(this.pieceMoved.file, this.pieceMoved.rank, this.pieceMoved.white));
                    this.remove(this.pieceMoved);
                    isChosen = true;
                    break;
                case ("R"):
                    this.add(new Rook(this.pieceMoved.file, this.pieceMoved.rank, this.pieceMoved.white));
                    this.remove(this.pieceMoved);
                    isChosen = true;
                    break;
                case ("N"):
                    this.add(new Knight(this.pieceMoved.file, this.pieceMoved.rank, this.pieceMoved.white));
                    this.remove(this.pieceMoved);
                    isChosen = true;
                    break;
                case ("B"):
                    this.add(new Bishop(this.pieceMoved.file, this.pieceMoved.rank, this.pieceMoved.white));
                    this.remove(this.pieceMoved);
                    isChosen = true;
                    break;
                default:
                    System.out.println("A pawn can be promoted to a queen, a rook, a knight or a bishop.");
                    break;
            }
        }
    }

    private boolean castlingCheck (King king, int fromFile, int fromRank, int toFile, int toRank) throws ChessRulesException{

        boolean castling = false;
        if (!king.moved && toRank == fromRank) {
            if (toFile == fromFile + 2) {
                if (getPiece(7, fromRank)) {
                    if (this.pieceFound instanceof Rook) {
                        Rook rookMoved = (Rook) this.pieceFound;
                        if (rookMoved.white == king.white && !rookMoved.moved) {
                            this.pieceOnPathCheck(fromFile, fromRank, 7, fromRank);
                            rookMoved.move(5, toRank, false);
                            castling = true;
                        } else {
                            throw new ChessRulesException("There's no rook to make a castling.");
                        }
                    } else {
                        throw new ChessRulesException("There's no rook to make a castling.");
                    }
                } else {
                    throw new ChessRulesException("There's no rook to make a castling.");
                }
            } else if (toFile == fromFile - 2) {
                if (getPiece(0, fromRank)) {
                    if (this.pieceFound instanceof Rook) {
                        Rook rookMoved = (Rook) this.pieceFound;
                        if (rookMoved.white == king.white && !rookMoved.moved) {
                            this.pieceOnPathCheck(fromFile, fromRank, 0, fromRank);
                            rookMoved.move(3, toRank, false);
                            castling = true;
                        } else {
                            throw new ChessRulesException("There's no rook to make a castling.");
                        }
                    } else {
                        throw new ChessRulesException("There's no rook to make a castling.");
                    }
                } else {
                    throw new ChessRulesException("There's no rook to make a castling.");
                }
            }
        }
        return castling;
    }
}