/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IMacro extends Serializable{
    String macroType();
    List<MacroEntry> macroValues(Object... args);
    boolean isMulti();
}
