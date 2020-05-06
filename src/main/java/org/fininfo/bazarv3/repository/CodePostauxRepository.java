package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.CodePostaux;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CodePostaux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodePostauxRepository extends JpaRepository<CodePostaux, Long> {
}
