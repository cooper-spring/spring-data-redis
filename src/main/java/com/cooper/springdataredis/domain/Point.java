package com.cooper.springdataredis.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Getter
@RedisHash("point")
public class Point implements Serializable {

    @Id
    private String id;

    private Long amount;

    private LocalDateTime updatedTime;

    @Builder
    public Point(String id, Long amount, LocalDateTime updatedTime) {
        this.id = id;
        this.amount = amount;
        this.updatedTime = updatedTime;
    }

    public void refresh(long amount, LocalDateTime updatedTime){
        if(updatedTime.isAfter(this.updatedTime)){
            this.amount = amount;
            this.updatedTime = updatedTime;
        }
    }

}
