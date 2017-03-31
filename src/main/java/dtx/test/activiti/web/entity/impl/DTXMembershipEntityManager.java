/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

/**
 *
 * @author datouxi
 */
public class DTXMembershipEntityManager extends MembershipEntityManager{

    @Override
    public void deleteMembership(String userId, String groupId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void createMembership(String userId, String groupId) {
        throw new RuntimeException("not implement method.");
    }

    
}
