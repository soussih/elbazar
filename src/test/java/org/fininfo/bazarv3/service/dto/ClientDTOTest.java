package org.fininfo.bazarv3.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.fininfo.bazarv3.web.rest.TestUtil;

public class ClientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDTO.class);
        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setId(1L);
        ClientDTO clientDTO2 = new ClientDTO();
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO2.setId(clientDTO1.getId());
        assertThat(clientDTO1).isEqualTo(clientDTO2);
        clientDTO2.setId(2L);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO1.setId(null);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
    }
}