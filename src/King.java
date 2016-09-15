import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class King extends Piece {

    public boolean moved;

    public King(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'K';
        this.moved = false;
    }

    public void move(int file, int rank, boolean castling) throws ChessRulesException{

        if(!castling && !(Math.abs(this.file - file) <= 1
                && Math.abs(this.rank - rank) <= 1)){
            throw new ChessRulesException("A king moves one square in any direction (or it can make a castling).");
        }
        super.move(file, rank);
        this.moved = true;
    }
}
