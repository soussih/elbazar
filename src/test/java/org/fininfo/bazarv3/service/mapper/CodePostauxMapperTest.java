package org.fininfo.bazarv3.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CodePostauxMapperTest {

    private CodePostauxMapper codePostauxMapper;

    @BeforeEach
    public void setUp() {
        codePostauxMapper = new CodePostauxMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(codePostauxMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(codePostauxMapper.fromId(null)).isNull();
    }
}
