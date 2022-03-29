package com.cooper.springdataredis.application;

import com.cooper.springdataredis.domain.Point;
import com.cooper.springdataredis.domain.PointRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRedisRepository pointRedisRepository;

}
