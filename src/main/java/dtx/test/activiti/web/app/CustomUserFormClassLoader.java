/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.idao.ICustomFormClassDao;
import dtx.test.activiti.web.dao.CustomFormClassDao;
import dtx.test.activiti.web.model.CustomFormClassModel;
import dtx.test.activiti.web.util.FormDesign;

/**
 *
 * @author gg
 */
public class CustomUserFormClassLoader extends ClassLoader{

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        name=name.startsWith(FormDesign.PACKAGE) ? name:FormDesign.PACKAGE+"."+name;
        ICustomFormClassDao formDao=new CustomFormClassDao();
        CustomFormClassModel formClass=formDao.getByClassName(name);
        if(formClass==null){
            throw new ClassNotFoundException();
        }
        byte[] classResource=formClass.getClassSource();
        return super.defineClass(name, classResource, 0, classResource.length);
    }
    
}
