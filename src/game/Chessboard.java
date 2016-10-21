package game;

import java.io.IOException;

/**
 * Created by pbarbarski on 02/09/2016.
 */
public interface Chessboard {

    void play(ChessSet chessSet) throws IOException;

    static String sideName(boolean white){
        if (white){
            return "White";
        } else {
            return "Black";
        }
    }
}
