package game;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public abstract class Piece {

    public int rank;
    public int file;
    public final boolean white;
    public final char symbol;

    Piece(int file, int rank, boolean white, char symbol) {
        this.file = file;
        this.rank = rank;
        this.white = white;
        this.symbol = symbol;
    }

    public void move(int file, int rank, boolean flag) throws ChessRulesException {
        this.file = file;
        this.rank = rank;
    }
}

