package org.fininfo.bazarv3.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class MouvementStockDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MouvementStockDTO.class);
        MouvementStockDTO mouvementStockDTO1 = new MouvementStockDTO();
        mouvementStockDTO1.setId(1L);
        MouvementStockDTO mouvementStockDTO2 = new MouvementStockDTO();
        assertThat(mouvementStockDTO1).isNotEqualTo(mouvementStockDTO2);
        mouvementStockDTO2.setId(mouvementStockDTO1.getId());
        assertThat(mouvementStockDTO1).isEqualTo(mouvementStockDTO2);
        mouvementStockDTO2.setId(2L);
        assertThat(mouvementStockDTO1).isNotEqualTo(mouvementStockDTO2);
        mouvementStockDTO1.setId(null);
        assertThat(mouvementStockDTO1).isNotEqualTo(mouvementStockDTO2);
    }
}
