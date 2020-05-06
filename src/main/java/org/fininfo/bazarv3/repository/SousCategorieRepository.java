package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.SousCategorie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SousCategorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousCategorieRepository extends JpaRepository<SousCategorie, Long> {
}
