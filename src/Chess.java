import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Chess {

    public static void main(String[] args) throws IOException {

        System.out.println("Hello. It's a program for paying chess by Paweł Barbarski.\n" +
                "Please input the moves in the argebraic notation in the form: 'a1-b2'");
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ChessSet chessSet = new ChessSet();
        AbstractChessboard chessboard = new Chessboard();
        while(true) {
            chessboard.draw(chessSet);
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
