package game;

/**
 * Created by pbarbarski on 01/09/2016.
 */
class Rook extends Piece {

    boolean moved;

    Rook(int file, int rank, boolean white) {
        super(file, rank, white, 'R');
        this.moved = false;
    }

    public void move(int file, int rank, boolean dump) throws ChessRulesException{

        if(!(this.rank == rank || this.file == file)){
            throw new ChessRulesException("A rook moves along files or ranks.");
        }
        super.move(file, rank, dump);
        this.moved = true;
    }
}
