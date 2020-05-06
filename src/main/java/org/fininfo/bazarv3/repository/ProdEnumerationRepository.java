package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.ProdEnumeration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProdEnumeration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdEnumerationRepository extends JpaRepository<ProdEnumeration, Long> {
}
