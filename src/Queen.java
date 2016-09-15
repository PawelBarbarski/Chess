import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Queen extends Piece {

    public Queen(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'Q';
    }

    public void move(int file, int rank) throws ChessRulesException{

        if(!(this.file - this.rank == file - rank
                || this.file + this.rank == file + rank
                || this.rank == rank || this.file == file)){
            throw new ChessRulesException("A queen moves along files, ranks or diagonals.");
        }
        super.move(file, rank);
    }
}
