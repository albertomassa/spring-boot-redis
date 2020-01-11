package com.am.springredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class RedisMessage {

    private String value;

    @Override
    public String toString() {
        return "RedisMessage{" +
                "value='" + value + '\'' +
                '}';
    }

}
