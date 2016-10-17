package game;

/**
 * Created by pbarbarski on 01/09/2016.
 */
class Bishop extends Piece {

    Bishop(int file, int rank, boolean white) {
        super(file, rank, white, 'B');
    }

    public void move(int file, int rank, boolean dump) throws ChessRulesException{

        if(!(this.file - this.rank == file - rank
                || this.file + this.rank == file + rank)){
            throw new ChessRulesException("A bishop moves along diagonals.");
        }
        super.move(file, rank, dump);
    }
}
