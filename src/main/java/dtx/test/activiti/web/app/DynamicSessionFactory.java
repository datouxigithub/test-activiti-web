/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.idao.ICustomFormClassDao;
import dtx.test.activiti.web.util.EntityUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.sql.DataSource;
import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 *
 * @author gg
 */
public class DynamicSessionFactory implements SessionFactory{
    
    private final ThreadLocal<Stack<SessionFactory>> sessionFactoryHolder=new ThreadLocal<>();
    private final ThreadLocal<Boolean> isInitCustomFormClassHolder=new ThreadLocal<>();
    
    public SessionFactory getSessionFactory(){
        if(sessionFactoryHolder.get()==null)
            sessionFactoryHolder.set(new Stack<SessionFactory>());
        if(sessionFactoryHolder.get().isEmpty())
            sessionFactoryHolder.get().push(EntityUtil.getSessionFactory());
        return sessionFactoryHolder.get().peek();
    }
    
    public void createNewSessionFactory(Class<?> entityClass) throws ReflectiveOperationException, IllegalArgumentException{
        SessionFactory sessionFactory=getSessionFactory();
        Set<String> keySet=sessionFactory.getAllClassMetadata().keySet();
        if(keySet.contains(entityClass.getName()))
            return;

        AnnotationSessionFactoryBean annotationSessionFactoryBean=(AnnotationSessionFactoryBean) EntityUtil.getContext().getBean("&sessionFactory");
        Field f=LocalSessionFactoryBean.class.getDeclaredField("configTimeDataSourceHolder");
        f.setAccessible(true);
        ThreadLocal<DataSource> configTimeDataSourceHolder=(ThreadLocal<DataSource>) f.get(annotationSessionFactoryBean);
        configTimeDataSourceHolder.set(annotationSessionFactoryBean.getDataSource());
        Configuration config=annotationSessionFactoryBean.getConfiguration();
        config.addAnnotatedClass(entityClass);
        sessionFactoryHolder.get().push(config.buildSessionFactory());
        configTimeDataSourceHolder.remove();
    }
    
    public void createNewSessionFactory(List<Class<?>> classes) throws ReflectiveOperationException, IllegalArgumentException{
        SessionFactory sessionFactory=getSessionFactory();
        Set<String> keySet=sessionFactory.getAllClassMetadata().keySet();
        
        AnnotationSessionFactoryBean annotationSessionFactoryBean=(AnnotationSessionFactoryBean) EntityUtil.getContext().getBean("&sessionFactory");
        Field f=LocalSessionFactoryBean.class.getDeclaredField("configTimeDataSourceHolder");
        f.setAccessible(true);
        ThreadLocal<DataSource> configTimeDataSourceHolder=(ThreadLocal<DataSource>) f.get(annotationSessionFactoryBean);
        configTimeDataSourceHolder.set(annotationSessionFactoryBean.getDataSource());
        Configuration config=annotationSessionFactoryBean.getConfiguration();
        for(Class clazz:classes){
            if(keySet.contains(clazz.getName()))continue;
            config.addAnnotatedClass(clazz);
        }
        sessionFactoryHolder.get().push(config.buildSessionFactory());
        configTimeDataSourceHolder.remove();
    }
    
    public void initCustomFormClasses(){
        if(isInitCustomFormClassHolder.get()==null)
            isInitCustomFormClassHolder.set(false);
        if(isInitCustomFormClassHolder.get()==true)
            return;
        ICustomFormClassDao dao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        try {
            createNewSessionFactory(new CustomUserFormClassLoader().loadClass(dao.getCustomFormClassModels()));
        } catch (Exception ex) {
        } finally{
            isInitCustomFormClassHolder.set(true);
        }
    }
    
    @Override
    public Session openSession() throws HibernateException {
        return getSessionFactory().openSession();
    }

    @Override
    public Session openSession(Interceptor interceptor) throws HibernateException {
        return getSessionFactory().openSession(interceptor);
    }

    @Override
    public Session openSession(Connection connection) {
        return getSessionFactory().openSession(connection);
    }

    @Override
    public Session openSession(Connection connection, Interceptor interceptor) {
        return getSessionFactory().openSession(connection, interceptor);
    }

    @Override
    public Session getCurrentSession() throws HibernateException {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public StatelessSession openStatelessSession() {
        return getSessionFactory().openStatelessSession();
    }

    @Override
    public StatelessSession openStatelessSession(Connection connection) {
        return getSessionFactory().openStatelessSession(connection);
    }

    @Override
    public ClassMetadata getClassMetadata(Class entityClass) {
        return getSessionFactory().getClassMetadata(entityClass);
    }

    @Override
    public ClassMetadata getClassMetadata(String entityName) {
        return getSessionFactory().getClassMetadata(entityName);
    }

    @Override
    public CollectionMetadata getCollectionMetadata(String roleName) {
        return getSessionFactory().getCollectionMetadata(roleName);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return getSessionFactory().getAllClassMetadata();
    }

    @Override
    public Map getAllCollectionMetadata() {
        return getSessionFactory().getAllCollectionMetadata();
    }

    @Override
    public Statistics getStatistics() {
        return getSessionFactory().getStatistics();
    }

    @Override
    public void close() throws HibernateException {
        getSessionFactory().close();
    }

    @Override
    public boolean isClosed() {
        return getSessionFactory().isClosed();
    }

    @Override
    public Cache getCache() {
        return getSessionFactory().getCache();
    }

    @Override
    public void evict(Class persistentClass) throws HibernateException {
        getSessionFactory().evict(persistentClass);
    }

    @Override
    public void evict(Class persistentClass, Serializable id) throws HibernateException {
        getSessionFactory().evict(persistentClass, id);
    }

    @Override
    public void evictEntity(String entityName) throws HibernateException {
        getSessionFactory().evictEntity(entityName);
    }

    @Override
    public void evictEntity(String entityName, Serializable id) throws HibernateException {
        getSessionFactory().evictEntity(entityName, id);
    }

    @Override
    public void evictCollection(String roleName) throws HibernateException {
        getSessionFactory().evictCollection(roleName);
    }

    @Override
    public void evictCollection(String roleName, Serializable id) throws HibernateException {
        getSessionFactory().evictCollection(roleName, id);
    }

    @Override
    public void evictQueries(String cacheRegion) throws HibernateException {
        getSessionFactory().evictQueries(cacheRegion);
    }

    @Override
    public void evictQueries() throws HibernateException {
        getSessionFactory().evictQueries();
    }

    @Override
    public Set getDefinedFilterNames() {
        return getSessionFactory().getDefinedFilterNames();
    }

    @Override
    public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
        return getSessionFactory().getFilterDefinition(filterName);
    }

    @Override
    public boolean containsFetchProfileDefinition(String name) {
        return getSessionFactory().containsFetchProfileDefinition(name);
    }

    @Override
    public TypeHelper getTypeHelper() {
        return getSessionFactory().getTypeHelper();
    }

    @Override
    public Reference getReference() throws NamingException {
        return getSessionFactory().getReference();
    }
    
}
