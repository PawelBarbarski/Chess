import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public abstract class Piece {

    public int rank;
    public int file;
    public final boolean white;
    public char symbol;

    public Piece(int file, int rank, boolean white) {

        this.file = file;
        this.rank = rank;
        this.white = white;
    }

    public void move(int file, int rank) throws ChessRulesException{

        this.file = file;
        this.rank = rank;
    }

    public void move(int file, int rank, boolean capture) throws ChessRulesException{

        this.file = file;
        this.rank = rank;
    }
}

