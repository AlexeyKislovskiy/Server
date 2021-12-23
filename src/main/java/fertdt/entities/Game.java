package fertdt.entities;

public class Game {
    public static final int RANDOM_ROOM_ID = 0;

    public static final int GAME_DURATION = 20;

    public static final int AWAIT = 1;
    public static final int CHARACTERS_SELECTING = 2;
    public static final int IN_PROGRESS = 3;
    public static final int FINISHED = 4;

    private Integer roomId, firstPlayer, secondPlayer, status, currentTurn, firstTurns, secondTurns;
    private Field firstField, secondField;
    private Character[] firstCharacters, secondCharacters;
    private PassiveSkill[] firstSkills, secondSkills;
    private int[] firstPoints, secondPoints;

    public Game(Integer roomId, Integer firstPlayer, Integer status) {
        this.roomId = roomId;
        this.firstPlayer = firstPlayer;
        this.status = status;
        firstTurns = 1;
        secondTurns = 1;
        firstPoints = new int[]{0, 0};
        secondPoints = new int[]{0, 0};
    }

    public void setFirstPlayer(Integer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(Integer secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getFirstPlayer() {
        return firstPlayer;
    }

    public Integer getSecondPlayer() {
        return secondPlayer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Field getFirstField() {
        return firstField;
    }

    public void setFirstField(Field firstField) {
        this.firstField = firstField;
    }

    public Field getSecondField() {
        return secondField;
    }

    public void setSecondField(Field secondField) {
        this.secondField = secondField;
    }

    public Character[] getFirstCharacters() {
        return firstCharacters;
    }

    public void setFirstCharacters(Character[] firstCharacters) {
        this.firstCharacters = firstCharacters;
    }

    public Character[] getSecondCharacters() {
        return secondCharacters;
    }

    public void setSecondCharacters(Character[] secondCharacters) {
        this.secondCharacters = secondCharacters;
    }

    public PassiveSkill[] getFirstSkills() {
        return firstSkills;
    }

    public void setFirstSkills(PassiveSkill[] firstSkills) {
        this.firstSkills = firstSkills;
    }

    public PassiveSkill[] getSecondSkills() {
        return secondSkills;
    }

    public void setSecondSkills(PassiveSkill[] secondSkills) {
        this.secondSkills = secondSkills;
    }

    public Integer getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Integer currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Integer getFirstTurns() {
        return firstTurns;
    }

    public void setFirstTurns(Integer firstTurns) {
        this.firstTurns = firstTurns;
    }

    public Integer getSecondTurns() {
        return secondTurns;
    }

    public void setSecondTurns(Integer secondTurns) {
        this.secondTurns = secondTurns;
    }

    public int[] getFirstPoints() {
        return firstPoints;
    }

    public void setFirstPoints(int[] firstPoints) {
        this.firstPoints = firstPoints;
    }

    public int[] getSecondPoints() {
        return secondPoints;
    }

    public void setSecondPoints(int[] secondPoints) {
        this.secondPoints = secondPoints;
    }
}
