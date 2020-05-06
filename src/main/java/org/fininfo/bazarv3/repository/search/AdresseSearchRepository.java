package org.fininfo.bazarv3.repository.search;

import org.fininfo.bazarv3.domain.Adresse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Adresse} entity.
 */
public interface AdresseSearchRepository extends ElasticsearchRepository<Adresse, Long> {
}
