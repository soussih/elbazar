package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.Zone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Zone} entity.
 */
public interface ZoneSearchRepository extends ElasticsearchRepository<Zone, Long> {
}
