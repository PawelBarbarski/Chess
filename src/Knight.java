import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Knight extends Piece {

    public Knight(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'N';
    }

    public void move(int file, int rank) throws ChessRulesException{

        if(!((Math.abs(this.file - file) == 2
                && Math.abs(this.rank - rank) == 1)
                || (Math.abs(this.file - file) == 1
                && Math.abs(this.rank - rank) == 2))){
            throw new ChessRulesException("A knight moves along L-shape (2 squares in one direction and 1 square in a perpendicular direction).");
        }
        super.move(file, rank);
    }
}
