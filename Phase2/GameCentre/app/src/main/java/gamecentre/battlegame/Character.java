package gamecentre.battlegame;

import java.io.Serializable;

abstract class Character implements Serializable {

    /**
     * The initial HP of the character.
     */
    private static final int INITIAL_HP = 100;
    /**
     * The initial HP of the character.
     */
    private int hp = INITIAL_HP;
    /**
     * The initial MP of the character.
     */
    private int mp = 100;
    /**
     * The character's opponent.
     */
    private Character opponent;
    /**
     * The character's battle queue.
     */
    private BattleQueue battleQueue = new BattleQueue();

    /**
     * Return whether this character has enough Health points to perform a special attack.
     *
     * @return True if this character has enough HP to perform an attack.
     */
    abstract boolean hasAttackMp();

    /**
     * A helper for the hasAttackMp used in child classes
     *
     * @param specialMoveCost the Mp cost of the special move
     * @return true if character has enough Mp to do a special attack, false otherwise
     */
    boolean hasAttackMpHelper(int specialMoveCost) {
        return getMp() >= specialMoveCost;
    }

    /**
     * Return the Magic points for this character.
     *
     * @return The amount of magic points.
     */
    int getMp() {
        return mp;
    }

    /**
     * Return the number of Health points this character has.
     *
     * @return The amount of magic points.
     */
    int getHp() {
        return hp;
    }

    /**
     * Get the initial HP
     *
     * @return the initial HP
     */
    static int getInitialHp() {
        return INITIAL_HP;
    }

    /**
     * Set the Mp of this character to newMp.
     *
     * @param newMp the MP
     */
    void setMp(int newMp) {
        mp = newMp;
    }

    /**
     * Set the Hp of this character to newHp.
     *
     * @param newHp the HP
     */
    void setHp(int newHp) {
        hp = newHp;
    }

    /**
     * Add the opponent for this character.
     *
     * @param character the opponent for this character
     */
    void setOpponent(Character character) {
        opponent = character;
    }

    /**
     * Perform this character's regular attack on its enemy and add the Character at the end of the
     * Battle Queue.
     */
    abstract void regularMove();

    /**
     * A helper function called in the child classes that processes a regular attack
     *
     * @param regularMoveDamage the damage done by a regular move
     */
    void regularMoveHelper(int regularMoveDamage) {
        getBattleQueue().makeMove();
        getBattleQueue().updatePlayerAttributesStack(this);
        getBattleQueue().updateUndoStack(getBattleQueue().copyBq());
        getBattleQueue().removeCharacter();
        getOpponent().reduceHp(regularMoveDamage);
        getBattleQueue().add(this);
    }

    /**
     * Perform this character's special move on its opponent.
     */
    abstract void specialMove();

    /**
     * A helper function called in the child classes that processes a special attack
     *
     * @param specialMoveCost   the MP cost of the special move
     * @param specialMoveDamage the damage done by the special move
     */
    void specialMoveHelper(int specialMoveCost, int specialMoveDamage) {
        getBattleQueue().makeMove();
        getBattleQueue().updatePlayerAttributesStack(this);
        getBattleQueue().updateUndoStack(getBattleQueue().copyBq());
        reduceMp(specialMoveCost);
        getOpponent().reduceHp(specialMoveDamage);
    }

    /**
     * Process the unique special attack for a stealth character.
     * <p>
     * Unique effect: reduce this character's magic points and reduce the enemy's Health points by
     * SPECIAL_MOVE_DAMAGE. Add this character into the battle queue twice so it can attack twice in
     * the next round.
     */
    void stealthCharacterSpecial() {
        getBattleQueue().removeCharacter();
        getBattleQueue().add(this.getOpponent());
        getBattleQueue().add(this);
        getBattleQueue().add(this);
    }

    /**
     * Process the unique special attack for a fighter character.
     * <p>
     * Unique effect: reset the battle queue so that each character appears once and then add
     * itself.
     */
    void fighterCharacterSpecial() {
        BattleQueue bq = getBattleQueue();
        bq.removeCharacter();
        Character currCharacter = bq.getNextCharacter();
        while (!bq.isEmpty()) {
            bq.removeCharacter();
        }
        bq.add(currCharacter);
        bq.add(currCharacter.getOpponent());
        bq.add(this);
    }

    /**
     * Process the unique special attack for a healer character.
     * <p>
     * Unique effect: heal the healer by specialMoveDamage HP points
     *
     * @param specialMoveDamage the damage done by a special move
     */
    void healerCharacterSpecial(int specialMoveDamage) {
        getBattleQueue().removeCharacter();
        increaseHp(specialMoveDamage);
        getBattleQueue().add(this);
        if (hp > INITIAL_HP) {
            hp = INITIAL_HP;
        }
    }

    /**
     * Get the next sprite for the character to display.
     *
     * @return The name of the next sprite.
     */
    abstract String getSprite();

    /**
     * Reduce this character's MP by damage if they have enough MP, else 0.
     *
     * @param amount the amount to reduce the MP by
     */
    private void reduceMp(int amount) {
        if (mp >= amount) {
            mp = mp - amount;
        } else {
            mp = 0;
        }
    }

    /**
     * Reduce the Hp of this character by damage.
     *
     * @param damage Amount to reduce HP
     */
    private void reduceHp(int damage) {
        if (hp >= damage) {
            hp -= damage;
        } else {
            hp = 0;
        }
    }

    /**
     * Return this character's BattleQueue.
     *
     * @return Character's Battle Queue
     */
    private BattleQueue getBattleQueue() {
        return battleQueue;
    }

    /**
     * Set this character's Battle Queue.
     *
     * @param battleQueue The Battle Queue this character and its Opponent will use.
     */
    void setBattleQueue(BattleQueue battleQueue) {
        this.battleQueue = battleQueue;
    }

    /**
     * Return this Character's opponent
     *
     * @return Opponent
     */
    Character getOpponent() {
        return opponent;
    }

    /**
     * Increase the character's Hp.
     *
     * @param amountHp Amount by which to increase the Hp.
     */
    private void increaseHp(int amountHp) {
        hp += amountHp;
    }

    /**
     * Return whether this character is a dog or a cat.
     *
     * @return dog if this character is a dog or cat if this character is a cat.
     */
    public abstract String getType();
}