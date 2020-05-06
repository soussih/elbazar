package org.fininfo.bazarv3.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ProduitSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ProduitSearchRepositoryMockConfiguration {

    @MockBean
    private ProduitSearchRepository mockProduitSearchRepository;

}
