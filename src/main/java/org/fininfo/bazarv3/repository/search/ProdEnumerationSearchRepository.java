package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.ProdEnumeration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ProdEnumeration} entity.
 */
public interface ProdEnumerationSearchRepository extends ElasticsearchRepository<ProdEnumeration, Long> {
}
