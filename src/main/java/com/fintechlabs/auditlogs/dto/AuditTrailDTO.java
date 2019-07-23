package com.fintechlabs.auditlogs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditTrailDTO {

    private String className;
    private String persistedObjectId;
    private String eventName;
    private String propertyName;
    private String oldValue;
    private String newValue;

    public AuditTrailDTO(String className, String persistedObjectId, String eventName, String propertyName, String oldValue, String newValue) {
        this.className = className;
        this.persistedObjectId = persistedObjectId;
        this.eventName = eventName;
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

}
