package org.fininfo.bazarv3.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class ZoneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZoneDTO.class);
        ZoneDTO zoneDTO1 = new ZoneDTO();
        zoneDTO1.setId(1L);
        ZoneDTO zoneDTO2 = new ZoneDTO();
        assertThat(zoneDTO1).isNotEqualTo(zoneDTO2);
        zoneDTO2.setId(zoneDTO1.getId());
        assertThat(zoneDTO1).isEqualTo(zoneDTO2);
        zoneDTO2.setId(2L);
        assertThat(zoneDTO1).isNotEqualTo(zoneDTO2);
        zoneDTO1.setId(null);
        assertThat(zoneDTO1).isNotEqualTo(zoneDTO2);
    }
}
