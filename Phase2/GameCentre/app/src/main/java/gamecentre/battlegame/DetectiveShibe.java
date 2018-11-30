package gamecentre.battlegame;

import java.io.Serializable;

/**
 * A class representing the Detective Shibe character, a stealth character.
 */
public class DetectiveShibe extends Character implements Serializable {

    private static final int REGULAR_MOVE_DAMAGE = 7;
    private static final int SPECIAL_MOVE_DAMAGE = 12;
    private static final int SPECIAL_MOVE_COST = 15;

    @Override
    boolean hasAttackMp() {
        return super.hasAttackMpHelper(SPECIAL_MOVE_COST);
    }

    @Override
    void regularMove() {
        super.regularMoveHelper(REGULAR_MOVE_DAMAGE);
    }

    /**
     * Plays Detective Shibe's special move.
     * Adds its opponent and then itself into the battle queue.
     */
    @Override
    void specialMove() {
        super.specialMoveHelper(SPECIAL_MOVE_COST, SPECIAL_MOVE_DAMAGE);
        super.stealthCharacterSpecial();
    }

    @Override
    String getSprite() {
        return "detective_shibe";
    }

    public String getType() {
        return "dog";
    }
}
