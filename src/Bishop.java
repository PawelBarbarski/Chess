import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Bishop extends Piece {

    public Bishop(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'B';
    }

    public void move(int file, int rank) throws ChessRulesException{

        if(!(this.file - this.rank == file - rank
                || this.file + this.rank == file + rank)){
            throw new ChessRulesException("A bishop moves along diagonals.");
        }
        super.move(file, rank);
    }
}
