package org.fininfo.bazarv3.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class AdresseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdresseDTO.class);
        AdresseDTO adresseDTO1 = new AdresseDTO();
        adresseDTO1.setId(1L);
        AdresseDTO adresseDTO2 = new AdresseDTO();
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO2.setId(adresseDTO1.getId());
        assertThat(adresseDTO1).isEqualTo(adresseDTO2);
        adresseDTO2.setId(2L);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
        adresseDTO1.setId(null);
        assertThat(adresseDTO1).isNotEqualTo(adresseDTO2);
    }
}
