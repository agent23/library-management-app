package com.app.library.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class CacheConfig {

    private String password;
    private String host;
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redis = new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port));
        redis.setPassword(password);
        return redis;
    }

    @Bean
    protected RedisCacheConfiguration defaultRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig();
    }

    @Bean
    public CacheManager cacheManager() {

        Map<String, RedisCacheConfiguration> cacheNamesConfigurationMap = new HashMap<>();
        cacheNamesConfigurationMap.put("Registration-getUsers", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
        cacheNamesConfigurationMap.put("Book-getAllBooks", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));

        return new RedisCacheManager(redisCacheWriter(), defaultRedisCacheConfiguration(), cacheNamesConfigurationMap);
    }

    @Bean
    RedisCacheWriter redisCacheWriter() {
        return RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory());
    }
}