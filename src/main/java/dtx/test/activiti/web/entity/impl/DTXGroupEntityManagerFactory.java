/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author datouxi
 */
public class DTXGroupEntityManagerFactory implements SessionFactory{

    @Autowired
    private DTXGroupEntityManager groupEntityManager;
    
    @Override
    public Class<?> getSessionType() {
        return GroupEntityManager.class;
    }

    @Override
    public Session openSession() {
        return groupEntityManager;
    }
    
}
