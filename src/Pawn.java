import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public class Pawn extends Piece {

    public boolean twoSquareMove;

    public Pawn(int file, int rank, boolean white) {
        super(file, rank, white);
        this.symbol = 'p';
        this.twoSquareMove = false;
    }

    public void move(int file, int rank, boolean capture) throws ChessRulesException {

        if (capture) {
            if (!(Math.abs(this.file - file) == 1
                    && ((rank - this.rank == 1 && this.white)
                    || (this.rank - rank == 1 && !this.white)))) {
                throw new ChessRulesException("A pawn captures moving 1 square forward along diagonal (it can capture another pawn in passing).");
            }
        } else {
            if (!(this.file == file
                    && ((rank - this.rank == 1 && this.white)
                    || (this.rank - rank == 1 && !this.white)
                    || (this.rank == 1 && rank == 3 && this.white)
                    || (this.rank == 6 && rank == 4 && !this.white)))) {
                throw new ChessRulesException("A pawn moves (without capture) 1 square forward (it can move 2 squares forward if its his first move).");
            }
        }
        if ((this.rank == 1 && rank == 3 && this.white)
                || (this.rank == 6 && rank == 4 && !this.white)) {
            this.twoSquareMove = true;
        }
        super.move(file, rank, capture);
    }
}
