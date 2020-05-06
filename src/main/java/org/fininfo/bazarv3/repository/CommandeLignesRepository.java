package org.fininfo.bazarv3.repository;

import org.fininfo.bazarv3.domain.CommandeLignes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommandeLignes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeLignesRepository extends JpaRepository<CommandeLignes, Long> {
}
