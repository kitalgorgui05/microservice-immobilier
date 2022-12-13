package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SalleMapperTest {

    private SalleMapper salleMapper;

    @BeforeEach
    public void setUp() {
        salleMapper = new SalleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(salleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(salleMapper.fromId(null)).isNull();
    }
}
