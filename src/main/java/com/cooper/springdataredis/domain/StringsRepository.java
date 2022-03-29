package com.cooper.springdataredis.domain;

public interface StringsRepository {

    String setIfAbsent(String key, String value);

    String getAndSet(String key, String updateValue);

    String delete(String key);

}
