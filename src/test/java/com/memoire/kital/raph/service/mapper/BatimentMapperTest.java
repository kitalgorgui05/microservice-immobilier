package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BatimentMapperTest {

    private BatimentMapper batimentMapper;

    @BeforeEach
    public void setUp() {
        batimentMapper = new BatimentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(batimentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(batimentMapper.fromId(null)).isNull();
    }
}
