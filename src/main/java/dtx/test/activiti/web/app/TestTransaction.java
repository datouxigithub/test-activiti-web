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
        CustomFormClassHelper helper=EntityUtil.getCustomFormClassHelper();
        helper.initExistsClasses();
        Class clazz=Class.forName("dtx.test.activiti.web.model.UserForm6d90a5161d3f4099ab89414b0862adc7");
        Object obj=clazz.newInstance();
        System.out.println(obj.toString());
    }
}
