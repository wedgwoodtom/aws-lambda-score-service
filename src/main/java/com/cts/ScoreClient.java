package com.cts;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreClient
{
    private DynamoDBMapper mapper;
    private Regions REGION = Regions.US_WEST_2;
    private int MAX = 10;

    public ScoreClient init()
    {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        mapper = new DynamoDBMapper(client);

        return this;
    }

    public void save(Score score)
    {
        mapper.save(score);
    }

    public Score load(String keyId)
    {
        return mapper.load(Score.class, keyId);
    }

    public void delete(String keyId)
    {
        Score toDelete = new Score();
        toDelete.setKeyId(keyId);
        mapper.delete(toDelete);
    }

    public List<Score> getTopScoresFor(String gameId)
    {
        // TODO: This is terrible, but Dynamo does not support sorting FFS
        List<Score> scores = new ArrayList<>();
        scores.addAll(paginatedListForGameId(gameId).stream()
            .sorted(Comparator.comparing(Score::getHighScore).reversed())
            .limit(MAX)
            .collect(Collectors.toList())
        );

        return scores;
    }

    public List<Score> getScoresFor(String gameId)
    {
        List<Score> scores = new ArrayList<>();
        scores.addAll(paginatedListForGameId(gameId).stream().collect(Collectors.toList()));
        return scores;
    }

    private PaginatedList<Score> paginatedListForGameId(String gameId)
    {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(gameId));

        PaginatedList<Score> list = mapper.scan(Score.class, new DynamoDBScanExpression()
            .withFilterExpression("GameId = :val1")
            .withExpressionAttributeValues(eav)
        );

        return list;
    }
}
