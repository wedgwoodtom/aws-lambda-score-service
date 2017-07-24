package com.cts;

public class ScoreRequest
{
    private String userId;
    private String gameId;
    private int score;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getScoreKey()
    {
        return getGameId()+":"+ getUserId();
    }
}
