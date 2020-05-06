package org.fininfo.bazarv3.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RemiseMapperTest {

    private RemiseMapper remiseMapper;

    @BeforeEach
    public void setUp() {
        remiseMapper = new RemiseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(remiseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(remiseMapper.fromId(null)).isNull();
    }
}
