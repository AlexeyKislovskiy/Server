package fertdt.entities;

import java.util.Arrays;
import java.util.Objects;

public class Game {
    public static final int RANDOM_ROOM_ID = 0;

    public static final int GAME_DURATION = 20;

    public static final int AWAIT = 1;
    public static final int CHARACTERS_SELECTING = 2;
    public static final int IN_PROGRESS = 3;
    public static final int FINISHED = 4;

    private Integer roomId, firstPlayer, secondPlayer, status, currentTurn, firstTurns, secondTurns, usedSkills;
    private Field firstField, secondField;
    private Character[] firstCharacters, secondCharacters;
    private AdditionalSkill[] firstSkills, secondSkills;
    private int[] firstPoints, secondPoints;

    public Game(Integer roomId, Integer firstPlayer, Integer status) {
        this.roomId = roomId;
        this.firstPlayer = firstPlayer;
        this.status = status;
        firstTurns = 0;
        secondTurns = 0;
        firstPoints = new int[]{0, 0};
        secondPoints = new int[]{0, 0};
        usedSkills = 0;
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

    public AdditionalSkill[] getFirstSkills() {
        return firstSkills;
    }

    public void setFirstSkills(AdditionalSkill[] firstSkills) {
        this.firstSkills = firstSkills;
    }

    public AdditionalSkill[] getSecondSkills() {
        return secondSkills;
    }

    public void setSecondSkills(AdditionalSkill[] secondSkills) {
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

    public Integer getUsedSkills() {
        return usedSkills;
    }

    public void setUsedSkills(Integer usedSkills) {
        this.usedSkills = usedSkills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return Objects.equals(roomId, game.roomId) && Objects.equals(firstPlayer, game.firstPlayer) && Objects.equals(secondPlayer, game.secondPlayer) && Objects.equals(status, game.status) && Objects.equals(currentTurn, game.currentTurn) && Objects.equals(firstTurns, game.firstTurns) && Objects.equals(secondTurns, game.secondTurns) && Objects.equals(usedSkills, game.usedSkills) && Objects.equals(firstField, game.firstField) && Objects.equals(secondField, game.secondField) && Arrays.equals(firstCharacters, game.firstCharacters) && Arrays.equals(secondCharacters, game.secondCharacters) && Arrays.equals(firstSkills, game.firstSkills) && Arrays.equals(secondSkills, game.secondSkills) && Arrays.equals(firstPoints, game.firstPoints) && Arrays.equals(secondPoints, game.secondPoints);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(roomId, firstPlayer, secondPlayer, status, currentTurn, firstTurns, secondTurns, usedSkills, firstField, secondField);
        result = 31 * result + Arrays.hashCode(firstCharacters);
        result = 31 * result + Arrays.hashCode(secondCharacters);
        result = 31 * result + Arrays.hashCode(firstSkills);
        result = 31 * result + Arrays.hashCode(secondSkills);
        result = 31 * result + Arrays.hashCode(firstPoints);
        result = 31 * result + Arrays.hashCode(secondPoints);
        return result;
    }
}
