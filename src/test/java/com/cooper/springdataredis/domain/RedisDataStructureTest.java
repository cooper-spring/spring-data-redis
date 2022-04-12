package com.cooper.springdataredis.domain;

import com.cooper.springdataredis.config.RedisRepositoryConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(RedisRepositoryConfig.class)
@SpringBootTest
public class RedisDataStructureTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    void init() {
        redisTemplate.delete("key");
    }

    @Test
    @DisplayName("strings 자료구조 값 주입 테스트")
    void stringTest() {
        String key = "key";
        String value = "1";

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);

        Object valueInRedis = valueOperations.get(key);
        assertThat(valueInRedis).isEqualTo(value);
    }

    @Test
    @DisplayName("포인트 객체 값 주입 테스트")
    void pointObjectTest() {
        Point point = new Point("key", 2L, LocalDateTime.now());

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(point.getId(), point);

        Point result = (Point)valueOperations.get(point.getId());
        System.out.println(result);
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo(point.getId());
    }

    @Test
    @DisplayName("setOperation 테스트")
    void setOperationTest() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();

        String setKey = "key";
        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";

        List<String> list = List.of(value1, value2, value3);

        setOperations.add(setKey, list.get(0));
        setOperations.add(setKey, list.get(1));
        setOperations.add(setKey, list.get(2));

        assertAll(
                () -> assertThat(setOperations.isMember(setKey, value1)).isTrue(),
                () -> assertThat(setOperations.isMember(setKey, value2)).isTrue(),
                () -> assertThat(setOperations.isMember(setKey, value3)).isTrue()
        );
    }

    @Test
    void sortedSetTest() {
        String key = "key";

        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        List<Point> points = new ArrayList<>();

        for (long i = 0L; i < 1000L; i++) {
            Point point = new Point(String.valueOf(i), i, LocalDateTime.now());
            points.add(point);
            zSetOperations.add(key, point, point.getAmount());
        }
        Set<Object> set = zSetOperations.range(key, 0, 1000);
        assertThat(points.size()).isEqualTo(Objects.requireNonNull(set).size());
    }

}
