package org.wekeepinmind.clients.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamoDBMapperService {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamodbEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.dynamodb.accessKey}")
    private String dynamodbAccessKey;

    @Value("${aws.dynamodb.secretKey}")
    private String dynamodbSecretKey;

    private final AmazonDynamoDB dynamoDBClient;
    private final DynamoDBMapper dynamoDBMapper;

    public DynamoDBMapperService() {
       this.dynamoDBClient = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                dynamodbEndpoint,
                                awsRegion
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        dynamodbAccessKey,
                                        dynamodbSecretKey
                                )
                        )
                )
                .build();

        this.dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
    }

    public <T> void saveItem(T object) {
        try {
            // Save the item using DynamoDBMapper
            dynamoDBMapper.save(object);
            System.out.println("Item saved successfully!");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            System.out.println("Failed to save item.");
        }
    }

    public <T> List<T> getItemsByIndex(
            final String indexName,
            final String attributeName,
            final Object value,
            Class<T> clazz) {
        // Create the query expression
        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withIndexName(indexName)
                .withConsistentRead(false)
                .withKeyConditionExpression(attributeName + " = :val")
                .withExpressionAttributeValues(getExpressionAttributeValues(value));

        // Use DynamoDBMapper to map the results to the desired object class
        return dynamoDBMapper.query(clazz, queryExpression);
    }

    // Helper method to create the expression attribute values based on the value type
    private Map<String, AttributeValue> getExpressionAttributeValues(Object value) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        if (value instanceof String) {
            expressionAttributeValues.put(":val", new AttributeValue().withS((String) value));
        } else if (value instanceof Integer) {
            expressionAttributeValues.put(":val", new AttributeValue().withN(value.toString()));
        } else if (value instanceof Long) {
            expressionAttributeValues.put(":val", new AttributeValue().withN(value.toString()));
        } else {
            throw new IllegalArgumentException("Unsupported attribute value type");
        }
        return expressionAttributeValues;
    }

    public <T> T getItemByPrimaryKey(
            final Object primaryKeyValue,
            Class<T> clazz) {
        return dynamoDBMapper.load(clazz, primaryKeyValue);
    }

}
