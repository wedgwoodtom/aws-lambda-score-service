package com.cts;


import java.util.List;

/**
 * Created by tom.patterson on 7/21/17.
 */
public class ScoreService
{
    private ScoreClient scoreClient = new ScoreClient().init();

    public ScoreResponse submitScore(ScoreRequest scoreRequest)
    {
        Score score = scoreClient.load(scoreRequest.getScoreKey());
        if (score == null)
        {
            score = Score.scoreFrom(scoreRequest.getUserId(), scoreRequest.getGameId());
            score.setNumberOfGames(1);
            score.setHighScore(scoreRequest.getScore());
        }
        else
        {
            score.setNumberOfGames(score.getNumberOfGames() + 1);
            if (scoreRequest.getScore() > score.getHighScore())
            {
                score.setHighScore(scoreRequest.getScore());
            }
        }
        scoreClient.save(score);

        return highScores(scoreRequest);
    }

    public ScoreResponse highScores(ScoreRequest scoreRequest)
    {
        Score score = scoreClient.load(scoreRequest.getScoreKey());
        List<Score> topScores = scoreClient.getTopScoresFor(scoreRequest.getGameId());
        return ScoreResponse.from(score, topScores);
    }
}
