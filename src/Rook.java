import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Rook extends Piece {

    public boolean moved;

    public Rook(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'R';
        this.moved = false;
    }

    public void move(int file, int rank) throws ChessRulesException{

        if(!(this.rank == rank || this.file == file)){
            throw new ChessRulesException("A rook moves along files or ranks.");
        }
        super.move(file, rank);
        this.moved = true;
    }
}
