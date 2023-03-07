package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class SalleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalleDTO.class);
        SalleDTO salleDTO1 = new SalleDTO();
        salleDTO1.setSalleId(null);
        SalleDTO salleDTO2 = new SalleDTO();
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO2.setSalleId(salleDTO1.getSalleId());
        assertThat(salleDTO1).isEqualTo(salleDTO2);
        salleDTO2.setSalleId(null);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
        salleDTO1.setSalleId(null);
        assertThat(salleDTO1).isNotEqualTo(salleDTO2);
    }
}
