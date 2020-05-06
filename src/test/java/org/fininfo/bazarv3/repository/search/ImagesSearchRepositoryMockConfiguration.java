package org.fininfo.bazarv3.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ImagesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ImagesSearchRepositoryMockConfiguration {

    @MockBean
    private ImagesSearchRepository mockImagesSearchRepository;

}
