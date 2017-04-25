/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

/**
 *
 * @author datouxi
 */
public final class MacroEntry{
    
    private String key;
    private Object value;
    
    public MacroEntry(){
    }
    
    public MacroEntry(String value){
        setValue(value);
    }
    
    public MacroEntry(String key,String value){
        setKey(key);
        setValue(value);
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
}
