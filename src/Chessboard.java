import java.util.Iterator;

/**
 * Created by pbarbarski on 02/09/2016.
 * AbstractChessboard implementation for IntelliJ output
 */
public class Chessboard implements AbstractChessboard {

    public void draw(ChessSet chessSet){
        String[][] chessboard = new String[8][8];
        Iterator<Piece> iterator = chessSet.iterator();
        String pieceColour;
        String squareColour;
        while (iterator.hasNext()) {
            Piece piece = iterator.next();
            if (piece.white) {
                pieceColour = "34";
            } else {
                pieceColour = "30";
            }
            if (Math.abs(piece.file - piece.rank) % 2 == 0) {
                squareColour = "43";
            } else {
                squareColour = "47";
            }
            chessboard[piece.file][piece.rank] =
                    (char) 27 + "[" + pieceColour + ";" + squareColour + "m" + piece.symbol + (char) 27 + "[0m";
        }

        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j <= 7; j++) {
                if (chessboard[j][i] != null) {
                    System.out.print(chessboard[j][i]);
                } else {
                    if (Math.abs(j - i) % 2 == 0) {
                        squareColour = "43";
                    } else {
                        squareColour = "47";
                    }
                    System.out.print((char) 27 + "[37;" + squareColour + "m" + " " + (char) 27 + "[0m");
                }
            }
            System.out.println();
        }
    }
}
