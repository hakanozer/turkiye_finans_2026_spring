package com.works.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String username) {

        return buckets.computeIfAbsent(username, key ->
                Bucket.builder()
                .addLimit(
                        Bandwidth.classic(
                                10,
                                Refill.intervally(1, Duration.ofSeconds(1))
                        )
                )
                .build()
        );
    }
}
