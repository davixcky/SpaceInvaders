package com.uninorte.base.game.highscore;

import com.uninorte.base.game.entities.creatures.Player;

import java.util.HashMap;

public class HighScoreManager {

    private HashMap<Player, HighScore> usersHighScores;

    public HighScoreManager() {
        usersHighScores = new HashMap<>();
    }

    public void registerPlayer(Player p) {
        if (!usersHighScores.containsKey(p))
            usersHighScores.put(p, new HighScore());
    }

    public void addPointsToPlayer(Player p, int points) {
        if (usersHighScores.containsKey(p))
            usersHighScores.get(p).addMorePoints(points);

        System.out.println( usersHighScores.get(p).getTotalPoints());
    }

    public int getPlayerPoints(Player p) {
        if (usersHighScores.containsKey(p))
            return usersHighScores.get(p).getTotalPoints();

        return 0;
    }

}
