package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.MouvementStock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MouvementStock} entity.
 */
public interface MouvementStockSearchRepository extends ElasticsearchRepository<MouvementStock, Long> {
}
