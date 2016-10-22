package chessboards;

import game.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by pbarbarski on 20/10/2016.
 * Chessboard implementation in JavaFX
 */
public class JavaFXChessboard extends Application implements Chessboard {

    public static String description = "JavaFX Application Chessboard";

    private static ChessSet chessSet;

    private boolean isFromSquare;
    private int fromFile;
    private int fromRank;
    private Label messageLabel;

    public static void main(String[] args) {
        chessSet = new ChessSet();
        launch();
    }

    public void play(ChessSet passedChessSet) throws IOException {
        chessSet = passedChessSet;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.isFromSquare = true;
        primaryStage.setTitle("Chess");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        draw(gridPane);
        messageLabel = new Label();
        gridPane.add(messageLabel, 0, 8, 8, 1);
        Scene scene = new Scene(gridPane, 512, 512);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void draw(GridPane gridPane) {
        Node[] nodes = new Node[132];
        int n = 0;
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                nodes[n] = node;
                n++;
            }
        }
        for (Node node : nodes) {
            gridPane.getChildren().remove(node);
        }
        Image whiteSquare = new Image("/icons/sWhite.png");
        Image blackSquare = new Image("/icons/sBlack.png");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView square = new ImageView();
                square.setFitWidth(64);
                square.setPreserveRatio(true);
                if ((i + j) % 2 == 1) {
                    square.setImage(blackSquare);
                } else {
                    square.setImage(whiteSquare);
                }
                int file = i;
                int rank = 7 - j;
                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        move(gridPane, file, rank);
                    }
                });
                gridPane.add(square, i, j);
            }
        }
        for (Piece piece : chessSet) {
            Image pieceImage = new Image("/icons/" + piece.symbol + Chessboard.sideName(piece.white) + ".png");
            ImageView pieceView = new ImageView();
            pieceView.setFitWidth(64);
            pieceView.setPreserveRatio(true);
            pieceView.setImage(pieceImage);
            pieceView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    move(gridPane, piece.file, piece.rank);
                }
            });
            gridPane.add(pieceView, piece.file, 7 - piece.rank);
        }
    }

    private void move(GridPane gridPane, int file, int rank) {
        if (!chessSet.isPawnPromotion) {
            if (isFromSquare) {
                fromFile = file;
                fromRank = rank;
            } else {
                try {
                    chessSet.move(fromFile, fromRank, file, rank);
                } catch (Exception e) {
                    messageLabel.setText(e.getMessage());
                }
                draw(gridPane);
                if (chessSet.isPawnPromotion) {
                    messageLabel.setText("Pawn promoted to a piece:");
                    String[] symbols = {"Q", "R", "N", "B"};
                    int i = 0;
                    for (String symbol : symbols) {
                        Image squareImage = new Image("/icons/s" + Chessboard.sideName(i % 2 == 0) + ".png");
                        ImageView squareView = new ImageView();
                        squareView.setFitWidth(64);
                        squareView.setPreserveRatio(true);
                        squareView.setImage(squareImage);
                        squareView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                chessSet.pawnPromotion(symbol);
                                draw(gridPane);
                            }
                        });
                        gridPane.add(squareView, i, 9);
                        Image pieceImage = new Image("/icons/" + symbol + Chessboard.sideName(!chessSet.whitesMove) + ".png");
                        ImageView pieceView = new ImageView();
                        pieceView.setFitWidth(64);
                        pieceView.setPreserveRatio(true);
                        pieceView.setImage(pieceImage);
                        pieceView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                chessSet.pawnPromotion(symbol);
                                draw(gridPane);
                            }
                        });
                        gridPane.add(pieceView, i, 9);
                        i++;
                    }
                }
            }
            isFromSquare = !isFromSquare;
        } else {
            messageLabel.setText("You cannot move any piece. Please choose a piece the pawn should be promoted to first.");
        }
    }
}
