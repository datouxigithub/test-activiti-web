/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author datouxi
 */
public class DTXUserEntityManagerFactory implements SessionFactory{

    @Autowired
    private DTXUserEntityManager userEntityManager;
    
    @Override
    public Class<?> getSessionType() {
        return UserEntityManager.class;
    }

    @Override
    public Session openSession() {
        return userEntityManager;
    }
    
}
