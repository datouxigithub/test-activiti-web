/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author datouxi
 */
public class DTXMembershipEntityManagerFactory implements SessionFactory{

    private DTXMembershipEntityManager membershipEntityManager;
    
    @Override
    public Class<?> getSessionType() {
        return MembershipIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return membershipEntityManager;
    }

    /**
     * @param membershipEntityManager the membershipEntityManager to set
     */
    public void setMembershipEntityManager(DTXMembershipEntityManager membershipEntityManager) {
        this.membershipEntityManager = membershipEntityManager;
    }
    
}
