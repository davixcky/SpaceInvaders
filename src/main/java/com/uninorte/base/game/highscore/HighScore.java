package com.uninorte.base.game.highscore;

public class HighScore {

    private int totalPoints;

    public HighScore() {
        totalPoints = 0;
    }

    void addMorePoints(int points) {
        totalPoints += points;
    }

    int getTotalPoints() {
        return totalPoints;
    }
}
