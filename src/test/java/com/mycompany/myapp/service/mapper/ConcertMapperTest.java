package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConcertMapperTest {

    private ConcertMapper concertMapper;

    @BeforeEach
    public void setUp() {
        concertMapper = new ConcertMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(concertMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(concertMapper.fromId(null)).isNull();
    }
}
