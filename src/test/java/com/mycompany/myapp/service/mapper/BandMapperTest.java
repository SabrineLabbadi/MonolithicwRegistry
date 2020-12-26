package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BandMapperTest {

    private BandMapper bandMapper;

    @BeforeEach
    public void setUp() {
        bandMapper = new BandMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bandMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bandMapper.fromId(null)).isNull();
    }
}
