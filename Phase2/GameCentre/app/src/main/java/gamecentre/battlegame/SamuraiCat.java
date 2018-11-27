package gamecentre.battlegame;

import java.io.Serializable;

public class SamuraiCat extends Character implements Serializable {

    private static final int REGULAR_MOVE_DAMAGE = 6;
    private static final int SPECIAL_MOVE_DAMAGE = 15;
    private static final int SPECIAL_MOVE_COST = 12;
    //BattleQueue =


    @Override
    boolean hasAttackMp() {
        return super.hasAttackMpHelper(SPECIAL_MOVE_COST);
    }

    @Override
    void regularMove() {
        super.regularMoveHelper(REGULAR_MOVE_DAMAGE);
    }

    /**
     * Perform the Samurai Cat's special attack, reduce this character's MP and reduce the HP of
     * opponent. Reset the battle queue so that each character appears once.
     */
    @Override
    void specialMove() {
        getBattleQueue().makeMove();
        getBattleQueue().updateUndoStack(getBattleQueue().copyBq());
        Character ch1 = getBattleQueue().getNextCharacter();
        BattleQueue bq = getBattleQueue();

        reduceMp(SPECIAL_MOVE_COST);
        getOpponent().reduceHp(SPECIAL_MOVE_DAMAGE);
        while (!bq.isEmpty()) { bq.removeCharacter(); }
        bq.add(ch1);
        bq.add(ch1.getOpponent());
        bq.add(this);


    }

    @Override
    String getSprite() {
        return "samurai_cat";
    }

    @Override
    public String getType() {
        return "cat";
    }

}
