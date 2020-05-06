package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.ProduitUnite;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProduitUnite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitUniteRepository extends JpaRepository<ProduitUnite, Long> {
}
