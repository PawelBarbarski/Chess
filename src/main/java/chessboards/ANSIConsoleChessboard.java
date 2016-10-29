package chessboards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import game.*;

/**
 * Created by pbarbarski on 02/09/2016.
 * Chessboard implementation for consoles supporting ANSI escape symbols
 */
public class ANSIConsoleChessboard implements Chessboard {

    public static final String description = "Console Chessboard (console supporting ANSI escape symbols recommended)";

    public void play(ChessSet chessSet) throws IOException {
        System.out.println("Please input the moves in the argebraic notation in the form: 'a1-b2'");
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            this.draw(chessSet);
            System.out.println(Chessboard.sideName(chessSet.whitesMove) + "'s move.");
            int[] moveSquares;
            try {
                moveSquares = getMove(br);
            } catch (Resignation e) {
                System.out.println("Resignation. " + Chessboard.sideName(!chessSet.whitesMove) + " won.");
                break;
            } catch (ChessRulesException e) {
                System.out.println(e.getMessage());
                continue;
            }
            try {
                chessSet.move(moveSquares[0], moveSquares[1], moveSquares[2], moveSquares[3]);
                if (chessSet.isPawnPromotion) {
                    boolean isChosen = false;
                    while (!isChosen) {
                        System.out.println("Pawn promoted to a piece:");
                        String notation = br.readLine();
                        if ("Q".equals(notation) || "R".equals(notation) || "N".equals(notation) || "B".equals(notation)) {
                            chessSet.pawnPromotion(notation);
                            isChosen = true;
                        } else {
                            System.out.println("A pawn can be promoted to a queen, a rook, a knight, or a bishop.");
                        }
                    }
                }
            } catch (ChessRulesException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void draw(ChessSet chessSet) {
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
        int[] moveSquares = new int[4];
        moveSquares[0] = (int) notation.charAt(0) - 97;
        moveSquares[1] = (int) notation.charAt(1) - 49;
        moveSquares[2] = (int) notation.charAt(3) - 97;
        moveSquares[3] = (int) notation.charAt(4) - 49;
        return moveSquares;
    }


}
