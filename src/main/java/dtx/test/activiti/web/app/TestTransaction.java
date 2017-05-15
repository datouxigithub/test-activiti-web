/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.util.EntityUtil;
import java.io.IOException;
import javassist.CannotCompileException;

/**
 *
 * @author datouxi
 */
public class TestTransaction {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, CannotCompileException {
//        DynamicSessionFactory dynamicSessionFactory=EntityUtil.getDynamicSessionFactory();
        Class clazz=new CustomUserFormClassLoader().findClass("dtx.test.activiti.web.model.UserForm59710aa7897949ec8e76fa7a20a67994");
        Object obj=clazz.newInstance();
        System.out.println(obj.toString());
    }
}
