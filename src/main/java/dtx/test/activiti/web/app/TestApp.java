/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.util.ReflectionUtil;
import java.util.List;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.IdentityServiceImpl;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cmd.CreateUserQueryCmd;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author datouxi
 */
public class TestApp {
    public static void main(String[] args) throws NoSuchFieldException {
        IdentityServiceImpl identityService=(IdentityServiceImpl) new ClassPathXmlApplicationContext("beans.xml").getBean("identityService");
        identityService.createUserQuery();
        CommandExecutorImpl cei=(CommandExecutorImpl) identityService.getCommandExecutor();
        UserQueryImpl uq=(UserQueryImpl) cei.execute(new CreateUserQueryCmd());
        System.out.println(ReflectionUtil.getFieldClass(cei, "first"));
    }
}
