/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author datouxi
 */
public class EntityUtil {
    
    private final static ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
    
    public static ApplicationContext getContext(){
        return context;
    }
    
    public static ProcessEngine getProcessEngine(){
        return (ProcessEngine) context.getBean("processEngine");
    }
    
    public static RepositoryService getRepositoryService(){
        return (RepositoryService) context.getBean("repositoryService");
    }
    
    public static RuntimeService getRuntimeService(){
        return (RuntimeService) context.getBean("runtimeService");
    }
    
    public static TaskService getTaskService(){
        return (TaskService) context.getBean("taskService");
    }
    
    public static FormService getFormService(){
        return (FormService) context.getBean("formService");
    }
    
    public static HistoryService getHistoryService(){
        return (HistoryService) context.getBean("historyService");
    }
    
    public static ManagementService getManagementService(){
        return (ManagementService) context.getBean("managementService");
    }
    
    public static IdentityService getIdentityService(){
        return (IdentityService) context.getBean("identityService");
    }
}
