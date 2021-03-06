package game;

/**
 * Created by pbarbarski on 01/09/2016.
 */
class Knight extends Piece {

    Knight(int file, int rank, boolean white) {
        super(file, rank, white, 'N');
    }

    public void move(int file, int rank, boolean dump) throws ChessRulesException {
        if (!((Math.abs(this.file - file) == 2
                && Math.abs(this.rank - rank) == 1)
                || (Math.abs(this.file - file) == 1
                && Math.abs(this.rank - rank) == 2))) {
            throw new ChessRulesException("A knight moves along L-shape (2 squares in one direction and 1 square in a perpendicular direction).");
        }
        super.move(file, rank, dump);
    }
}
