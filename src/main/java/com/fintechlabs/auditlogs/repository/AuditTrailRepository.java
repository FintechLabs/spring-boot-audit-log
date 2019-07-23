package com.fintechlabs.auditlogs.repository;

import com.fintechlabs.auditlogs.model.AuditTrail;
import org.springframework.data.repository.CrudRepository;

public interface AuditTrailRepository extends CrudRepository<AuditTrail, Long> {
}
