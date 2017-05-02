/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.util.EntityUtil;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import org.activiti.engine.impl.interceptor.SessionFactory;

/**
 *
 * @author gg
 */
public class TestJavassist {
    
    private static final Map<String,CtClass> customFields;
    
    static {
        customFields=new LinkedHashMap<>();
        customFields.put("data_1", CtClass.intType);
        customFields.put("data_2", CtClass.longType);
        customFields.put("data_3", CtClass.booleanType);
        customFields.put("data_4", CtClass.byteType);
        customFields.put("data_5", CtClass.shortType);
        customFields.put("data_6", CtClass.floatType);
//        customFields.put("data_7", CtClass.voidType);
        customFields.put("data_8", CtClass.charType);
        customFields.put("data_9", CtClass.doubleType);
    }
    
    public static void main(String[] args) throws CannotCompileException, NotFoundException, ReflectiveOperationException {
        ClassPool pool=ClassPool.getDefault();
        CtClass newClass=pool.makeClass("dtx.test.activiti.web.app.userform.MyForm");
        newClass.setSuperclass(pool.get("dtx.test.activiti.web.app.DefaultUserForm"));
        Iterator<String> iter=customFields.keySet().iterator();
        while(iter.hasNext()){
            String field=iter.next();
            CtField ctf=new CtField(customFields.get(field), field, newClass);
            newClass.addField(ctf);
            newClass.addMethod(CtMethod.make(getter(field), newClass));
            newClass.addMethod(CtMethod.make(setter(field), newClass));
        }
        AnnotationsAttribute classAttr=new AnnotationsAttribute(newClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
        Annotation classAnnot=new Annotation("javax.persistence.Entity", newClass.getClassFile().getConstPool());
        classAttr.addAnnotation(classAnnot);
        newClass.getClassFile().addAttribute(classAttr);
        
        SessionFactory sessionFactory=(SessionFactory) EntityUtil.obtanSessionFactory(newClass.toClass());
    }
    
    private static String getter(String field){
        return String.format("public %s get%s(){return this.%s;}",customFields.get(field).getName(),methodName(field),field);
    }
    
    private static String setter(String field){
        return String.format("public void set%s(%s value){this.%s=value;}", methodName(field),customFields.get(field).getName(),field);
    }
    
    private static String methodName(String field){
        return (field.substring(0, 1).toUpperCase()+field.substring(1)).replace("_", "");
    }
    
}
