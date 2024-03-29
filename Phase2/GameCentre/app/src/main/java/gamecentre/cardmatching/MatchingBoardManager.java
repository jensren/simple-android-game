package gamecentre.cardmatching;

import android.os.Handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gamecentre.BoardManager;

/**
 * Manager for the matching board.
 */
public class MatchingBoardManager extends BoardManager implements Serializable {
    /**
     * The board being managed.
     */
    private MatchingBoard board;
    /**
     * Keeps track of number of cards flipped.
     */
    private int tilesCurrentlyFlipped = 0;

    /**
     * Keeps track of matched tiles.
     */
    int tilesMatched = 0;
    /**
     * Keeps track of the row and col indices of the two flipped tiles. Initialized as -1.
     */
    private int[] flippedTiles = new int[]{-1, -1, -1, -1};
    /**
     * Number of moves made
     */
    private int numMoves = 0;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    MatchingBoardManager(MatchingBoard board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    MatchingBoard getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    public MatchingBoardManager() {
        List<MatchingTile> tiles = new ArrayList<>();
        final int numTiles = MatchingBoard.numRows * MatchingBoard.numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new MatchingTile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new MatchingBoard(tiles);
    }

    /**
     * Checks if a tap made by the player is valid.
     *
     * @param position the position the player touched
     * @return whether or not the tap is valid
     */
    @Override
    protected boolean isValidTap(int position) {
        int row = position / MatchingBoard.numCols;
        int col = position % MatchingBoard.numCols;
        if (flippedTiles[0] != -1) {
            if (board.tiles[flippedTiles[0]][flippedTiles[1]] == board.tiles[row][col]) {  //Checks if you're flipping the same tile twice
                return false;
            }
        }
        int blankId = 17;
        return board.matchingGetTile(row, col).getId() != blankId;
    }

    /**
     * Checks if you won the game by seeing if all tiles on the board have been matched.
     *
     * @return Whether you have won or not.
     */
    @Override
    protected boolean isWin() {
        return tilesMatched == board.numTiles();
    }

    /**
     * Processes a touch on the screen and flips tiles accordingly depending on how many tiles are currently flipped.
     *
     * @param position the position you touched.
     */
    @Override
    protected void touchMove(int position) {
        int row = position / MatchingBoard.numCols;
        int col = position % MatchingBoard.numCols;

        if (isValidTap(position)) {
            if (tilesCurrentlyFlipped == 0) {
                board.flipTile(row, col);
                flippedTiles[0] = row;
                flippedTiles[1] = col;
                tilesCurrentlyFlipped++;
            } else if (tilesCurrentlyFlipped == 1) {
                board.flipTile(row, col);
                flippedTiles[2] = row;
                flippedTiles[3] = col;
                if (tilesMatched != 14) {                         //This part runs as long as you're not about to win.
                    new Handler().postDelayed(new Runnable() {   //Waits 0.35 second and then checks matching and flips over accordingly
                        @Override
                        public void run() {
                            checkMatching();
                            tilesCurrentlyFlipped = 0;
                            flippedTiles = new int[]{-1, -1, -1, -1};
                        }
                    }, 350);
                } else {                                          //This part runs if you have only last two tiles left to match.
                    checkMatching();
                    tilesCurrentlyFlipped = 0;
                    flippedTiles = new int[]{-1, -1, -1, -1};
                }
            }
        }
    }

    /**
     * This function checks if the two tiles you flipped match. If they do, then remove them (ie. flip to blank) and increment
     * tilesMatched by 2. If they don't match, flip both back to question mark tiles.
     */
    private void checkMatching() {
        if (board.tiles[flippedTiles[0]][flippedTiles[1]].compareTo(board.tiles[flippedTiles[2]]
                [flippedTiles[3]]) == 0) {
            board.flipBlank(flippedTiles);
            tilesMatched += 2;
        } else {
            board.flipBack(flippedTiles[0], flippedTiles[1]);
            board.flipBack(flippedTiles[2], flippedTiles[3]);
        }
    }

    /**
     * Increments the number of moves by 1
     */
    public void updateMoves() {
        numMoves += 1;
    }

    /**
     * @return the number of moves
     */
    public int getNumMoves() {
        return numMoves;
    }
}
