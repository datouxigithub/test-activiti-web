/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import java.util.LinkedHashMap;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 *
 * @author gg
 */
public class TestJavassist {
    
    private static Map<String,String> customFields;
    
    static {
        customFields=new LinkedHashMap<>();
        for(int i=1;i<=10;i++){
            customFields.put("data_"+i, "Data"+i);
        }
    }
    
    public static void main(String[] args) throws CannotCompileException, NotFoundException {
        ClassPool pool=ClassPool.getDefault();
        CtClass newClass=pool.makeClass("dtx.test.activiti.web.app.userform.MyForm");
        newClass.setSuperclass(pool.get("dtx.test.activiti.web.app.DefaultUserForm"));
        System.out.println(newClass.toString());
    }
    
}
