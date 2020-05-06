package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.Images;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Images} entity.
 */
public interface ImagesSearchRepository extends ElasticsearchRepository<Images, Long> {
}
