/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.util.ApplicationContextUtil;
import dtx.test.activiti.web.entity.impl.DTXUserEntityManager;
import dtx.test.activiti.web.util.EntityUtil;
import dtx.test.activiti.web.util.ReflectionUtil;
import java.util.List;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.IdentityServiceImpl;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cmd.CreateUserQueryCmd;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.persistence.UserEntityManagerFactory;
import org.activiti.spring.SpringTransactionInterceptor;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author datouxi
 */
public class TestApp {
    public static void main(String[] args) throws NoSuchFieldException {
        IdentityServiceImpl identityService=(IdentityServiceImpl) EntityUtil.getIdentityService();
//        for(User user:userQuery.list())
//            System.out.println("--------------->>>"+user.getFirstName());
        for(Group group:identityService.createGroupQuery().list())
            System.out.println("--------------->>>"+group.getName());
//        CommandExecutorImpl cei=(CommandExecutorImpl) identityService.getCommandExecutor();
//        UserQuery userQuery=cei.execute(new CreateUserQueryCmd());
//        identityService.createUserQuery();
//        CommandExecutorImpl cei=(CommandExecutorImpl) identityService.getCommandExecutor();
//        UserQueryImpl uq=(UserQueryImpl) cei.execute(new CreateUserQueryCmd());
//        DTXUserEntityManager userEntityManager=(DTXUserEntityManager) EntityUtil.getContext().getBean("userEntityManager");
//        User user=userEntityManager.findUserById("ff80808159922a2c01599237ab430001");
//        System.out.println(user.getFirstName());
//        System.out.println(userEntityManager.createNewUserQuery());
    }
}
