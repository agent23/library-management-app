package com.app.library.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
@Data
@ConfigurationProperties(value = "spring.data.mongodb")
public class MongoDBConfig extends AbstractMongoConfiguration {

    private MongoClientURI uri;

    @Override
    protected String getDatabaseName() {
        return getUri().getDatabase();
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(uri);
    }
}
