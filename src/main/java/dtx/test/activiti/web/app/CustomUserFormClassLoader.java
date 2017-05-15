/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.idao.ICustomFormClassDao;
import dtx.test.activiti.web.model.CustomFormClassModel;
import dtx.test.activiti.web.util.EntityUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

/**
 *
 * @author gg
 */
public class CustomUserFormClassLoader{

    public Class<?> findClass(String name) throws IOException, CannotCompileException{
        ICustomFormClassDao formDao=(ICustomFormClassDao) EntityUtil.getContext().getBean("customFormClassDao");
        return loadClass(formDao.getByClassName(name));
    }

    
    public Class<?> loadClass(CustomFormClassModel formClass) throws IOException, CannotCompileException{
        ClassPool pool=ClassPool.getDefault();
        CtClass ctc=pool.makeClass(new ByteArrayInputStream(formClass.getClassSource()),false);
        return ctc.toClass();
    }
    
    public List<Class<?>> loadClass(List<CustomFormClassModel> formClasses) throws IOException, CannotCompileException{
        List<Class<?>> result=new ArrayList<>();
        ClassPool pool=ClassPool.getDefault();
        for(CustomFormClassModel formClass:formClasses){
            result.add(pool.makeClass(new ByteArrayInputStream(formClass.getClassSource()),false).toClass());
        }
        return result;
    }
    
}
