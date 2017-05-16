/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.idao.ICustomFormClassDao;
import dtx.test.activiti.web.model.CustomFormClassModel;
import dtx.test.activiti.web.model.DefaultUserForm;
import dtx.test.activiti.web.util.EntityUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javassist.CannotCompileException;

/**
 *
 * @author datouxi
 */
public class CustomFormClassHelper {
    
    private CustomClassLoader customClassLoader;
    private DynamicSessionFactory dynamicSessionFactory;
    private final ThreadLocal<Boolean> isInitCustomFormClassHolder=new ThreadLocal<>();

    public void setDynamicSessionFactory(DynamicSessionFactory dynamicSessionFactory) {
        this.dynamicSessionFactory = dynamicSessionFactory;
    }

    public void setCustomClassLoader(CustomClassLoader customFormClassLoader) {
        this.customClassLoader = customFormClassLoader;
    }
    
    public Class loadClass(String name) throws IOException, CannotCompileException{
        ICustomFormClassDao formDao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        return loadClass(formDao.getByClassName(name));
    }
    
    public Class<?> loadClass(CustomFormClassModel formClass) throws IOException, CannotCompileException{
        return customClassLoader.loadClass(formClass.getClassSource());
    }
    
    public List<Class<?>> loadClass(List<CustomFormClassModel> formClasses) throws IOException, CannotCompileException{
        List<Class<?>> result=new ArrayList<>();
        for(CustomFormClassModel formClass:formClasses){
            result.add(loadClass(formClass));
        }
        return result;
    }
    
    public void initExistsClasses(){
        if(isInitCustomFormClassHolder.get()==null)
            isInitCustomFormClassHolder.set(false);
        if(isInitCustomFormClassHolder.get()==true)
            return;
        ICustomFormClassDao dao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        try {
            dynamicSessionFactory.createNewSessionFactory(loadClass(dao.getCustomFormClassModels()));
        } catch (Exception ex) {
        } finally{
            isInitCustomFormClassHolder.set(true);
        }
    }
    
    public DefaultUserForm newFormInstance(String formClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        initExistsClasses();
        Class clazz=Class.forName(formClassName);
        return (DefaultUserForm) clazz.newInstance();
    }
    
}
