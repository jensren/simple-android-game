package gamecentre;

import java.io.Serializable;

public abstract class Board implements Serializable {

    /**
     * The listener that will notify when the board got updated.
     */
    transient private BoardUpdateListener boardUpdateListener = null;

    /**
     * Sets the board update listener.
     *
     * @param boardUpdateListener the board update listener being set.
     */
    public void setBoardUpdateListener(BoardUpdateListener boardUpdateListener){
        this.boardUpdateListener = boardUpdateListener;
    }

    /**
     * Calls listener's on board changed as long as there is a listener.
     */
    public void listenerUpdate(){
        if (boardUpdateListener != null){
            boardUpdateListener.onBoardChanged();
        }
    }
}
