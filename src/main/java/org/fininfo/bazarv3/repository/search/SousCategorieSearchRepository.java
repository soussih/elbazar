package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.SousCategorie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SousCategorie} entity.
 */
public interface SousCategorieSearchRepository extends ElasticsearchRepository<SousCategorie, Long> {
}
