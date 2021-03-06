/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.entity.impl;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.RoleUser;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.AbstractQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.UserEntity;

/**
 *
 * @author gg
 */
public class DtxUserQuery extends AbstractQuery<UserQuery, User> implements UserQuery{
    
    private String uuid,account,roleId;

    @Override
    public UserQuery userId(String id) {
        this.uuid=id;
        return this;
    }

    @Override
    public UserQuery userFirstName(String firstName) {
        this.account=firstName;
        return this;
    }

    @Override
    public UserQuery userFirstNameLike(String firstNameLike) {
        return userFirstName(firstNameLike);
    }

    @Override
    public UserQuery userLastName(String lastName) {
        return userFirstName(lastName);
    }

    @Override
    public UserQuery userLastNameLike(String lastNameLike) {
        return userFirstName(lastNameLike);
    }

    @Override
    public UserQuery userFullNameLike(String fullNameLike) {
        return userFirstName(fullNameLike);
    }

    @Override
    public UserQuery userEmail(String email) {
        throw new UnsupportedOperationException("userEmail---------------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery userEmailLike(String emailLike) {
        return this;
    }

    @Override
    public UserQuery memberOfGroup(String groupId) {
        this.roleId=groupId;
        return this;
    }

    @Override
    public UserQuery potentialStarter(String procDefId) {
        throw new UnsupportedOperationException("potentialStarter----------------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery orderByUserId() {
        throw new UnsupportedOperationException("orderByUserId------------------>>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery orderByUserFirstName() {
        throw new UnsupportedOperationException("orderByUserFirstName------------------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery orderByUserLastName() {
        throw new UnsupportedOperationException("orderByUserLastName----------------------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery orderByUserEmail() {
        throw new UnsupportedOperationException("orderByUserEmail---------------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserQuery asc() {
        return this;
    }

    @Override
    public UserQuery desc() {
        return this;
    }

    @Override
    public long count() {
        return IDaoFactory.iUserDao().getUsersByStatus(true).size();
    }
    
    private User covertUser(dtx.oa.rbac.model.User u){
        if(u==null)return null;
        User user=new UserEntity();
        user.setId(u.getUuid());
        user.setFirstName(u.getAccount());
        user.setLastName(u.getAccount());
        user.setEmail(u.getRemark());
        user.setPassword(u.getPassword());
        return user;
    }

    @Override
    public User singleResult() {
        return covertUser((uuid!=null) ? IDaoFactory.iUserDao().getUserById(uuid):((account!=null) ? IDaoFactory.iUserDao().getUserByAccount(account):null));
    }
    
    private List<User> getUsers(){
        List<dtx.oa.rbac.model.User> users=null;
        if(roleId!=null&&!"".equals(roleId)){
           List<RoleUser> rus=IDaoFactory.iRoleUserDao().queryByRoleId(roleId);
           users=new ArrayList<>();
           for(RoleUser ru:rus)
               users.add(IDaoFactory.iUserDao().getUserById(ru.getUserId()));
           rus=null;
        }else
            users=IDaoFactory.iUserDao().getUsersByStatus(true);
        List<User> result=new ArrayList<>();
        for(dtx.oa.rbac.model.User u:users)
            result.add(covertUser(u));
        users=null;
        return result;
    }

    @Override
    public List<User> list() {
//        List<dtx.oa.rbac.model.User> users=IDaoFactory.iUserDao().getUsersByStatus(true);
//        List<User> result=new ArrayList<>();
//        for(dtx.oa.rbac.model.User u:users)
//            result.add(covertUser(u));
//        return result;
        return getUsers();
    }

    @Override
    public List<User> listPage(int firstResult, int maxResults) {
//        List<dtx.oa.rbac.model.User> users=IDaoFactory.iUserDao().getUsersByStatus(true);
//        List<User> result=new ArrayList<>();
//        for(int i=firstResult,len=(maxResults>users.size()) ? users.size():maxResults;i<len;i++){
//            result.add(covertUser(users.get(i)));
//        }
//        return result;
        List<User> users=getUsers();
        List<User> result=new ArrayList<>();
        for(int i=0,len=(maxResults>users.size()) ? users.size():maxResults;i<len;i++)
            result.add(users.get(i));
        return result;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        throw new UnsupportedOperationException("executeCount--------->>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> executeList(CommandContext commandContext, Page page) {
        throw new UnsupportedOperationException("executeList------------>>>Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
