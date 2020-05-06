package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.CodePostaux;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CodePostaux} entity.
 */
public interface CodePostauxSearchRepository extends ElasticsearchRepository<CodePostaux, Long> {
}
