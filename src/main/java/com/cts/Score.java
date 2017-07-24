package com.cts;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Score")
public class Score
{
    private String keyId;
    private String userId;
    private String gameId;
    private int numberOfGames;
    private int highScore;

    public static Score scoreFrom(String userId, String gameId)
    {
        Score score = new Score();
        score.setUserId(userId);
        score.setGameId(gameId);
        score.setKeyId(gameId + ":" + userId);
        return score;
    }

    @DynamoDBAttribute(attributeName = "UserId")
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "GameId")
    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    @DynamoDBAttribute(attributeName = "TotalNumberOfGames")
    public int getNumberOfGames()
    {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames)
    {
        this.numberOfGames = numberOfGames;
    }

    @DynamoDBAttribute(attributeName = "HighScore")
//    @DynamoDBRangeKey(attributeName = "HighScore")
    public int getHighScore()
    {
        return highScore;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

    @DynamoDBHashKey(attributeName = "KeyId")
    public String getKeyId()
    {
        return keyId;
    }

    public void setKeyId(String keyId)
    {
        this.keyId = keyId;
    }
}
