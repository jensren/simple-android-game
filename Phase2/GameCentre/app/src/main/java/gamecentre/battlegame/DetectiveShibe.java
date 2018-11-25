package gamecentre.battlegame;

/**
 * Representation of the Detective Shibe character.
 */
public class DetectiveShibe extends Character {

    private int regularMoveDamage = 7;
    private int specialMoveDamage = 12;
    private int specialMoveCost = 15;

    @Override
    boolean hasAttackMp() {
        return getMp() >= specialMoveCost;
    }

    @Override
    void regularMove() {
        getBattleQueue().updateUndoStack(getBattleQueue().copyBq());
        this.getOpponent().reduceHp(regularMoveDamage);
        getBattleQueue().add(this);

    }

    /**
     * Reduce this character's magic points and reduce the enemy's Health points by
     * specialMoveDamage. Add this character into the battle queue twice so it can attack twice in
     * the next round.
     */
    @Override
    void specialMove() {
        getBattleQueue().updateUndoStack(getBattleQueue().copyBq());
        reduceMp(specialMoveCost);
        getOpponent().reduceHp(specialMoveDamage);
        getBattleQueue().add(this);
        getBattleQueue().add(this);

    }

    @Override
    String getSprite() {
        return null;
    }

}
