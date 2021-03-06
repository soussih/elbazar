package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.Produit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
