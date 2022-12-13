package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class BatimentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Batiment.class);
        Batiment batiment1 = new Batiment();
        batiment1.setId(null);
        Batiment batiment2 = new Batiment();
        batiment2.setId(batiment1.getId());
        assertThat(batiment1).isEqualTo(batiment2);
        batiment2.setId(null);
        assertThat(batiment1).isNotEqualTo(batiment2);
        batiment1.setId(null);
        assertThat(batiment1).isNotEqualTo(batiment2);
    }
}
