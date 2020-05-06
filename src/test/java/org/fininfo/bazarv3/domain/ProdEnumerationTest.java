package org.fininfo.bazarv3.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class ProdEnumerationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdEnumeration.class);
        ProdEnumeration prodEnumeration1 = new ProdEnumeration();
        prodEnumeration1.setId(1L);
        ProdEnumeration prodEnumeration2 = new ProdEnumeration();
        prodEnumeration2.setId(prodEnumeration1.getId());
        assertThat(prodEnumeration1).isEqualTo(prodEnumeration2);
        prodEnumeration2.setId(2L);
        assertThat(prodEnumeration1).isNotEqualTo(prodEnumeration2);
        prodEnumeration1.setId(null);
        assertThat(prodEnumeration1).isNotEqualTo(prodEnumeration2);
    }
}
