/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

/**
 *
 * @author gg
 */
public class CustomClassLoader{
    
    public Class<?> loadClass(InputStream is) throws IOException, CannotCompileException{
        ClassPool pool=ClassPool.getDefault();
        CtClass ctc=pool.makeClass(is, false);
        return ctc.toClass();
    }
    
    public Class<?> loadClass(byte[] source) throws IOException, CannotCompileException{
        return loadClass(new ByteArrayInputStream(source));
    }
}
