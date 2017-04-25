/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import dtx.test.activiti.web.util.macro.DateCnMacro;
import dtx.test.activiti.web.util.macro.DateCnShort1Macro;
import dtx.test.activiti.web.util.macro.DateCnShort2Macro;
import dtx.test.activiti.web.util.macro.DateCnShort3Macro;
import dtx.test.activiti.web.util.macro.DateCnShort4Macro;
import dtx.test.activiti.web.util.macro.DateTimeMacro;
import dtx.test.activiti.web.util.macro.TimeMacro;
import java.util.Stack;

/**
 *
 * @author datouxi
 */
public class IMacroContainer {
    private static final Stack<IMacro> macroStack=new Stack<>();
    static{
        macroStack.push(new DateCnMacro());
        macroStack.push(new DateCnMacro());
        macroStack.push(new DateCnShort1Macro());
        macroStack.push(new DateCnShort2Macro());
        macroStack.push(new DateCnShort3Macro());
        macroStack.push(new DateCnShort4Macro());
        macroStack.push(new DateTimeMacro());
        macroStack.push(new TimeMacro());
    }
    
    public static IMacro chooseMacro(String type){
        Stack<IMacro> tmpStack=new Stack<>();
        while(!macroStack.isEmpty()){
            IMacro m=macroStack.peek();
            if(m.macroType().equals(type)){
                return m;
            }
            tmpStack.push(macroStack.pop());
        }
        while(!tmpStack.isEmpty())
            macroStack.push(tmpStack.pop());
        return null;
    }
}
