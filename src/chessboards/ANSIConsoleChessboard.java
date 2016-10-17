package chessboards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import game.*;

/**
 * Created by pbarbarski on 02/09/2016.
 * AbstractChessboard implementation for consoles supporting ANSI escape symbols
 */
public class ANSIConsoleChessboard implements AbstractChessboard {

    public static final String description = "Console chessboards (console supporting ANSI escape symbols recommended)";

    public void play(ChessSet chessSet) throws IOException {
        System.out.println("Please input the moves in the argebraic notation in the form: 'a1-b2'");
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            this.draw(chessSet);
            System.out.println(sideName(chessSet.whitesMove) + "'s move.");
            int[] moveSquares;
            try {
                moveSquares = getMove(br);
            } catch (Resignation e) {
                System.out.println("Resignation. " + sideName(!chessSet.whitesMove) + " won.");
                break;
            } catch (ChessRulesException e){
                System.out.println(e.getMessage());
                continue;
            }
            try {
                chessSet.move(moveSquares[0], moveSquares[1], moveSquares[2], moveSquares[3]);
            } catch (ChessRulesException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void draw(ChessSet chessSet){
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

    private static int[] getMove(BufferedReader br) throws IOException, Resignation, ChessRulesException {

        String notation = br.readLine();
        if (notation.matches("X")) {
            throw new Resignation();
        }
        if (!notation.matches("^[a-h][1-8]-[a-h][1-8]$")) {
            throw new ChessRulesException("Chess notation error.");
        }
        if (notation.charAt(0) == notation.charAt(3) && notation.charAt(1) == notation.charAt(4)){
            throw new ChessRulesException("One piece have to be moved.");
        }
        int[] moveSquares = new int[4];
        moveSquares[0] = (int) notation.charAt(0) - 97;
        moveSquares[1] = (int) notation.charAt(1) - 49;
        moveSquares[2] = (int) notation.charAt(3) - 97;
        moveSquares[3] = (int) notation.charAt(4) - 49;
        return moveSquares;
    }

    private static String sideName(boolean white){
        if (white){
            return "White";
        } else {
            return "Black";
        }
    }

}
