package org.fininfo.bazarv3.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class CodePostauxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodePostaux.class);
        CodePostaux codePostaux1 = new CodePostaux();
        codePostaux1.setId(1L);
        CodePostaux codePostaux2 = new CodePostaux();
        codePostaux2.setId(codePostaux1.getId());
        assertThat(codePostaux1).isEqualTo(codePostaux2);
        codePostaux2.setId(2L);
        assertThat(codePostaux1).isNotEqualTo(codePostaux2);
        codePostaux1.setId(null);
        assertThat(codePostaux1).isNotEqualTo(codePostaux2);
    }
}
