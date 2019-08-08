package com.fintechlabs.auditlogs.audit;

import com.fintechlabs.auditlogs.dto.AuditTrailDTO;
import com.fintechlabs.auditlogs.service.AuditLogService;
import com.fintechlabs.auditlogs.util.ApplicationContextProvider;
import com.fintechlabs.auditlogs.util.Enums;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuditLogListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    @Override
    public void onPostInsert(PostInsertEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] states = event.getState();
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Save   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), event.getId().toString(), Enums.AuditEvent.INSERT.name(), propertyNames[i], null, states[i].toString()));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] currentState = event.getState();
            Object[] previousState = event.getOldState();
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < currentState.length; i++) {
                if (!previousState[i].equals(currentState[i])) {
                    System.out.println("Inside On Flush Dirty   ************    **************      ==>>    " + propertyNames[i]);
                    auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), event.getId().toString(), Enums.AuditEvent.UPDATE.name(), propertyNames[i], previousState[i].toString(), currentState[i].toString()));
                }
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Object entity = event.getEntity();
        if (entity instanceof AuditAware) {
            String[] propertyNames = event.getPersister().getPropertyNames();
            Object[] state = event.getDeletedState();
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Delete   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), event.getId().toString(), Enums.AuditEvent.DELETE.name(), propertyNames[i], state[i].toString(), null));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }
}
