package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.Remise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Remise} entity.
 */
public interface RemiseSearchRepository extends ElasticsearchRepository<Remise, Long> {
}
