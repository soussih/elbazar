package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.CommandeLignes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CommandeLignes} entity.
 */
public interface CommandeLignesSearchRepository extends ElasticsearchRepository<CommandeLignes, Long> {
}
