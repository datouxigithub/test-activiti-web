/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util.macro;

import dtx.test.activiti.web.util.IMacro;
import dtx.test.activiti.web.util.MacroEntry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author datouxi
 */
public class DateMacro implements IMacro{

    @Override
    public String macroType() {
        return "sys_date";
    }

    @Override
    public List<MacroEntry> macroValues(Object... args) {
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
        String dateStr=sdf.format(new Date());
        List<MacroEntry> result=new ArrayList<>();
        result.add(new MacroEntry(dateStr));
        return result;
    }
    
    @Override
    public boolean isMulti() {
        return false;
    }
    
}
