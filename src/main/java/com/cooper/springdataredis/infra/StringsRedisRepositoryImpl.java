package com.cooper.springdataredis.infra;

import com.cooper.springdataredis.domain.StringsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@RequiredArgsConstructor
public class StringsRedisRepositoryImpl implements StringsRepository {

    private final RedisTemplate<?, ?> redisTemplate;

    @Override
    public String setIfAbsent(String key, String value) {
        ValueOperations<String, String> values = (ValueOperations<String, String>) redisTemplate.opsForValue();
        values.setIfAbsent(key, value);
        return value;
    }

    @Override
    public String getAndSet(String key, String value) {
        ValueOperations<String, String> values = (ValueOperations<String, String>) redisTemplate.opsForValue();
        return values.getAndSet(key, value);
    }

    @Override
    public String delete(String key) {
        ValueOperations<String, String> values = (ValueOperations<String, String>) redisTemplate.opsForValue();
        return values.getAndDelete(key);
    }

}
