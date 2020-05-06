package org.fininfo.bazarv3.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class CodePostauxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodePostauxDTO.class);
        CodePostauxDTO codePostauxDTO1 = new CodePostauxDTO();
        codePostauxDTO1.setId(1L);
        CodePostauxDTO codePostauxDTO2 = new CodePostauxDTO();
        assertThat(codePostauxDTO1).isNotEqualTo(codePostauxDTO2);
        codePostauxDTO2.setId(codePostauxDTO1.getId());
        assertThat(codePostauxDTO1).isEqualTo(codePostauxDTO2);
        codePostauxDTO2.setId(2L);
        assertThat(codePostauxDTO1).isNotEqualTo(codePostauxDTO2);
        codePostauxDTO1.setId(null);
        assertThat(codePostauxDTO1).isNotEqualTo(codePostauxDTO2);
    }
}
