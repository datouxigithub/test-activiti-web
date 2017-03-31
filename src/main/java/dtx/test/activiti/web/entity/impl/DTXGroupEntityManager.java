/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import dtx.oa.rbac.idao.IRoleUserDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

/**
 *
 * @author datouxi
 */
public class DTXGroupEntityManager extends GroupEntityManager{

    @Override
    public boolean isNewGroup(Group group) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        IRoleUserDao iru=IDaoFactory.iRoleUserDao();
        List<Role> roles=iru.getRoleByUser(IDaoFactory.iUserDao().getUserById(userId));
        List<Group> groups=new ArrayList<>();
        for(Role role:roles){
            GroupEntity groupEntity=new GroupEntity();
            groupEntity.setId(role.getUuid());
            groupEntity.setName(role.getRoleName());
            groups.add(groupEntity);
        }
       return groups;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public GroupQuery createNewGroupQuery() {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void deleteGroup(String groupId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void updateGroup(Group updatedGroup) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void insertGroup(Group group) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public Group createNewGroup(String groupId) {
        throw new RuntimeException("not implement method.");
    }
    
}
