package gamecentre.cardmatching;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * An extension of GridView containing built in logic for handling swipes between buttons
 * Adapted from:
 * https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/
 * samplepuzzle/GestureDetectGridView.java
 */
public class MatchingGestureDetectGridView extends GridView {
    /**
     * The minimum swipe distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * The gesture detector
     */
    private GestureDetector gDetector;
    /**
     * The movement controller
     */
    public MatchingMovementController mController;
    /**
     * Whether fling is confirmed.
     */
    private boolean mFlingConfirmed = false;
    /**
     * The x coordinate of the touch.
     */
    private float mTouchX;
    /**
     * The y coordinate of the touch.
     */
    private float mTouchY;
    /**
     * The matching board manager.
     */
    MatchingBoardManager boardManager;

    /**
     * Constructor for MatchingGestureDetectGridView
     *
     * @param context the context
     */
    public MatchingGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor for MatchingGestureDetectGridView
     * @param context the context
     * @param attrs the attribute set
     */
    public MatchingGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Constructor for MatchingGestureDetectGridView
     * @param context the context
     * @param attrs the attribute set
     * @param defStyleAttr the style attribute
     */
    public MatchingGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialize mController and gDetector
     * @param context the context
     */
    private void init(final Context context) {
        mController = new MatchingMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = MatchingGestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Set the boardManager for this game
     *
     * @param boardManager the boardManager
     */
    public void setBoardManager(MatchingBoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}

