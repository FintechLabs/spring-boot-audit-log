package com.fintechlabs.auditlogs.audit;

import com.fintechlabs.auditlogs.dto.AuditTrailDTO;
import com.fintechlabs.auditlogs.service.AuditLogService;
import com.fintechlabs.auditlogs.util.ApplicationContextProvider;
import com.fintechlabs.auditlogs.util.Enums;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuditLogInterceptor extends EmptyInterceptor {

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < currentState.length; i++) {
                if (!previousState[i].equals(currentState[i])) {
                    System.out.println("Inside On Flush Dirty   ************    **************      ==>>    " + propertyNames[i]);
                    auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), id.toString(), Enums.AuditEvent.UPDATE.name(), propertyNames[i], previousState[i].toString(), currentState[i].toString()));
                }
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Save   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), id.toString(), Enums.AuditEvent.INSERT.name(), propertyNames[i], null, state[i].toString()));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof AuditAware) {
            List<AuditTrailDTO> auditTrailDTOList = new ArrayList<>();
            AuditLogService auditLogService = (AuditLogService) ApplicationContextProvider.getApplicationContext().getBean("auditLogService");
            for (int i = 0; i < propertyNames.length; i++) {
                System.out.println("Inside On Delete   ************    ************** ===>>>      " + propertyNames[i]);
                auditTrailDTOList.add(new AuditTrailDTO(entity.getClass().getCanonicalName(), id.toString(), Enums.AuditEvent.DELETE.name(), propertyNames[i], state[i].toString(), null));
            }
            auditTrailDTOList.forEach(auditLogService::save);
        }
    }

}
