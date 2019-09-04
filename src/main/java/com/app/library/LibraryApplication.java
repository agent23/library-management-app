package com.app.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @RequestMapping("ping")
    public String index(){
        try {
            URI redisUri = new URI(System.getenv("REDISCLOUD_URL"));
            JedisPool pool = new JedisPool(new JedisPoolConfig(),
                    redisUri.getHost(),
                    redisUri.getPort(),
                    Protocol.DEFAULT_TIMEOUT,
                    redisUri.getUserInfo().split(":",2)[1]);
            Jedis jedis = pool.getResource();
            jedis.set("foo", "bar");
            String value = jedis.get("foo");
            System.out.println(">>> "+value);
            // return the instance to the pool when you're done
            pool.returnResource(jedis);
        } catch (URISyntaxException e) {
            // URI couldn't be parsed.
        }
        return "<h1>Library Application is Alive</h1>";
    }

}
