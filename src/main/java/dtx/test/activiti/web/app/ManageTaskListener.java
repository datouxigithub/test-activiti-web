/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import java.util.Stack;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 *
 * @author datouxi
 */
public class ManageTaskListener implements TaskListener{
    
    public static final Stack<String> sampleUsers=new Stack<>();
    
    static{
        sampleUsers.add("张三");
        sampleUsers.add("李四");
        sampleUsers.add("王五");
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(ManageTaskListener.sampleUsers.peek());
    }
    
}
