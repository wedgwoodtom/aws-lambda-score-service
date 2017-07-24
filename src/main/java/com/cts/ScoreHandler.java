package com.cts;

import com.amazonaws.services.lambda.runtime.Context;


/**
 * AWS Lambda Handler
 */
public class ScoreHandler
{
    private ScoreService scoreService = new ScoreService();

    /**
     * Submit latest score - the service will update the high score and keep track of the number of games played
     *
     * @param scoreRequest
     * @param context
     * @return
     */
    public ScoreResponse submitScore(ScoreRequest scoreRequest, Context context)
    {
        return scoreService.submitScore(scoreRequest);
    }

    /**
     * Return the top 10 highest scores
     *
     * @param scoreRequest
     * @param context
     * @return
     */
    public ScoreResponse highScores(ScoreRequest scoreRequest, Context context)
    {
        return scoreService.highScores(scoreRequest);
    }
}
