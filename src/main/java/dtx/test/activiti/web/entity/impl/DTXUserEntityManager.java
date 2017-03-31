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
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

/**
 *
 * @author datouxi
 */
public class DTXUserEntityManager extends UserEntityManager{

    @Override
    public void setUserPicture(String userId, Picture picture) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public Picture getUserPicture(String userId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public boolean isNewUser(User user) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public UserQuery createNewUserQuery() {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        IRoleUserDao iru=IDaoFactory.iRoleUserDao();
        dtx.oa.rbac.model.User user=IDaoFactory.iUserDao().getUserById(userId);
        List<Role> roles=iru.getRoleByUser(user);
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
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void deleteUser(String userId) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public User findUserById(String userId) {
        dtx.oa.rbac.model.User user=IDaoFactory.iUserDao().getUserById(userId);
        UserEntity userEntity=new UserEntity();
        userEntity.setId(user.getUuid());
        userEntity.setFirstName(user.getAccount());
        userEntity.setLastName(user.getAccount());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }

    @Override
    public void updateUser(User updatedUser) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public void insertUser(User user) {
        throw new RuntimeException("not implement method.");
    }

    @Override
    public User createNewUser(String userId) {
        throw new RuntimeException("not implement method.");
    }
    
}
