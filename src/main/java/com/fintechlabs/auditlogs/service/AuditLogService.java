package com.fintechlabs.auditlogs.service;

import com.fintechlabs.auditlogs.dto.AuditTrailDTO;
import com.fintechlabs.auditlogs.model.AuditTrail;
import com.fintechlabs.auditlogs.repository.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditTrailRepository auditTrailRepository;

    public AuditLogService(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    public void save(AuditTrailDTO auditTrailDTO){
        auditTrailRepository.save(new AuditTrail(auditTrailDTO));
    }

}
