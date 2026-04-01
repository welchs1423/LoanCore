package com.finance.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        org.springframework.data.redis.connection.RedisStandaloneConfiguration redisConfig = 
            new org.springframework.data.redis.connection.RedisStandaloneConfiguration("localhost", 6379);
        
        org.springframework.data.redis.connection.jedis.JedisClientConfiguration clientConfig = 
            org.springframework.data.redis.connection.jedis.JedisClientConfiguration.builder().usePooling().build();
            
        return new JedisConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(1))
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }
}