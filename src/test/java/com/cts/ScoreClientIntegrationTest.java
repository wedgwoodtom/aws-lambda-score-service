package com.cts;


import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.Assert;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScoreClientIntegrationTest
{
    public static final String GAME_ID = "game1";
    public static final String SCORE_KEY = GAME_ID+":user1";
    private static ScoreClient scoreClient;

    @BeforeClass
    public static void setup()
    {
        scoreClient = new ScoreClient().init();
    }

    @Test
    public void test10Save() throws Exception
    {
        Score score = Score.scoreFrom("user1", "game1");
        score.setHighScore(100);
        scoreClient.save(score);

        Score fromDb = scoreClient.load(SCORE_KEY);
        Assert.assertNotNull(fromDb);
        Assert.assertEquals(score.getKeyId(), fromDb.getKeyId());
        Assert.assertEquals(score.getGameId(), fromDb.getGameId());
        Assert.assertEquals(score.getHighScore(), fromDb.getHighScore());
        Assert.assertEquals(score.getNumberOfGames(), fromDb.getNumberOfGames());
    }

    @Test
    public void test20Update() throws Exception
    {
        Score score = Score.scoreFrom("user1", "game1");
        score.setHighScore(1000);
        scoreClient.save(score);

        Score fromDb = scoreClient.load(SCORE_KEY);
        Assert.assertNotNull(fromDb);
        Assert.assertEquals(1000, fromDb.getHighScore());
    }

    @Test
    public void test30NoKeyFound() throws Exception
    {
        Score fromDb = scoreClient.load(SCORE_KEY +"NOT");
        Assert.assertNull(fromDb);
    }

    @Test
    public void test40GetTopScoresFor() throws Exception
    {
        String userKey = "someUser";
        String gameKey = "someGame";
        String gameId = gameKey+":"+userKey;

        // create a bunch
        int n=50;
        for (int i=0; i<n; i++)
        {
            Score score = Score.scoreFrom(userKey+i, gameKey);
            score.setHighScore(100*i);
            scoreClient.save(score);
        }

        // verify top scores
        List<Score> topScores = scoreClient.getTopScoresFor(gameKey);
        Assert.assertTrue(topScores.size()==10);

        // get all
        List<Score> allScores = scoreClient.getScoresFor(gameKey);
        Assert.assertTrue(allScores.size()>=n);

        // delete them
        allScores.forEach(
            score -> scoreClient.delete(score.getKeyId())
        );
    }

    @Test
    public void test90Delete() throws Exception
    {
        scoreClient.delete(SCORE_KEY);

        Score fromDb = scoreClient.load(SCORE_KEY);
        Assert.assertNull(fromDb);
    }



}
