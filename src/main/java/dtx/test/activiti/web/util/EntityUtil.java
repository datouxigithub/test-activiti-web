/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 *
 * @author datouxi
 */
public class EntityUtil {
    
    private final static ApplicationContext context=new ClassPathXmlApplicationContext("testbeans.xml");
    
    private final static List<SessionFactory> sessionFactories=new ArrayList<>();
    
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
    
    public synchronized static SessionFactory obtanSessionFactory(Class<?> entityClass) throws HibernateException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        SessionFactory sessionFactory=(SessionFactory) getContext().getBean("sessionFactory");
        
        Map<String,ClassMetadata> classMetaDataMap=sessionFactory.getAllClassMetadata();
        Set<String> keySet=classMetaDataMap.keySet();
        
        if(keySet.contains(entityClass.getName()))
            return sessionFactory;
        
        for(SessionFactory sf:sessionFactories){
            keySet=sf.getAllClassMetadata().keySet();
            if(keySet.contains(entityClass.getName())){
                return sf;
            }
        }
        
        AnnotationSessionFactoryBean sessionFactoryBean=(AnnotationSessionFactoryBean) getContext().getBean("&sessionFactory");
        Field f=LocalSessionFactoryBean.class.getDeclaredField("configTimeDataSourceHolder");
        f.setAccessible(true);
        ThreadLocal<DataSource> configTimeDataSourceHolder=(ThreadLocal<DataSource>) f.get(sessionFactoryBean);
        configTimeDataSourceHolder.set(sessionFactoryBean.getDataSource());
        Configuration config=sessionFactoryBean.getConfiguration();
        config.addAnnotatedClass(entityClass);
        SessionFactory newSessionFactory=config.buildSessionFactory();
        sessionFactories.add(newSessionFactory);
        configTimeDataSourceHolder.remove();
        return newSessionFactory;
    }
}
