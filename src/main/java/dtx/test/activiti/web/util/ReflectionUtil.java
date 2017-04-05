/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.lang.reflect.Field;

/**
 *
 * @author datouxi
 */
public class ReflectionUtil {
    
    public static Class getFieldClass(Object obj,String fieldName) throws NoSuchFieldException{
        Class objClass=obj.getClass();
        Field field=null;
        try{
            field=objClass.getDeclaredField(fieldName);
        }catch(NoSuchFieldException e){
            field=objClass.getField(fieldName);
        }
        return field.getType();
    }
    
}
