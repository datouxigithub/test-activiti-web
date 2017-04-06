/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import dtx.oa.rbac.model.RoleTreeLeaf;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.persistence.entity.GroupEntity;

/**
 *
 * @author gg
 */
public class DtxGroupQuery implements GroupQuery{
    
    private String uuid,roleName;

    @Override
    public GroupQuery groupId(String groupId) {
        this.uuid=groupId;
        return this;
    }

    @Override
    public GroupQuery groupName(String groupName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery groupNameLike(String groupNameLike) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery groupType(String groupType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery groupMember(String groupMemberUserId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery potentialStarter(String procDefId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery orderByGroupId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery orderByGroupName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery orderByGroupType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery asc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupQuery desc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count() {
        return IDaoFactory.iRoleDao().getByStatus(true).size();
    }
    
    private Group covertGroup(Role role){
        if(role==null)return null;
        Group group=new GroupEntity();
        group.setId(role.getUuid());
        group.setName(role.getRoleName());
        return group;
    }

    @Override
    public Group singleResult() {
        return covertGroup((uuid!=null) ? IDaoFactory.iRoleDao().getRoleById(uuid):null);
    }

    @Override
    public List<Group> list() {
        List<RoleTreeLeaf> roles=IDaoFactory.iRoleDao().getAllRoles().toList();
        List<Group> result=new ArrayList<>();
        for(RoleTreeLeaf leaf:roles)
            result.add(covertGroup(leaf.getEntity()));
        return result;
    }

    @Override
    public List<Group> listPage(int firstResult, int maxResults) {
        List<RoleTreeLeaf> roles=IDaoFactory.iRoleDao().getAllRoles().toList();
        List<Group> result=new ArrayList<>();
        for(int i=firstResult;i<maxResults;i++)
            result.add(covertGroup(roles.get(i).getEntity()));
        return result;
    }
    
}
