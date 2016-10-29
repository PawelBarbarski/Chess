package game;

/**
 * Created by pbarbarski on 01/09/2016.
 */
class Queen extends Piece {

    Queen(int file, int rank, boolean white) {
        super(file, rank, white, 'Q');
    }

    public void move(int file, int rank, boolean dump) throws ChessRulesException {
        if (!(this.file - this.rank == file - rank
                || this.file + this.rank == file + rank
                || this.rank == rank || this.file == file)) {
            throw new ChessRulesException("A queen moves along files, ranks or diagonals.");
        }
        super.move(file, rank, dump);
    }
}
