package com.cts;

import java.util.List;

public class ScoreResponse
{
    private Score myHighScore;
    private List<Score> highScores;

    public static ScoreResponse from(Score myHighScore, List<Score> highScores)
    {
        ScoreResponse scoreResponse = new ScoreResponse();
        scoreResponse.setMyHighScore(myHighScore);
        scoreResponse.setHighScores(highScores);
        return scoreResponse;
    }

    public Score getMyHighScore()
    {
        return myHighScore;
    }

    public void setMyHighScore(Score myHighScore)
    {
        this.myHighScore = myHighScore;
    }

    public List<Score> getHighScores()
    {
        return highScores;
    }

    public void setHighScores(List<Score> highScores)
    {
        this.highScores = highScores;
    }

}
