package dtx.test.activiti.web.test;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dtx.test.activiti.web.util.EntityUtil;
import java.util.List;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author datouxi
 */
public class HelloWorldJUnitTest {
    
    @Test
    public void deploy(){
        Deployment deployment=EntityUtil.getRepositoryService().createDeployment().name("testprocess").addClasspathResource("testprocess.bpmn20.xml").deploy();
        System.out.println("------------------>>>"+deployment.getId());
        System.out.println("------------------>>>"+deployment.getName());
        System.out.println("------------------>>>"+deployment.getDeploymentTime());
    }
    
    @Ignore
    @Test
    public void process(){
        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey("holiday");
        System.out.println("------------------>>>"+processInstance.getId());
        System.out.println("------------------>>>"+processInstance.getName());
        System.out.println("------------------>>>"+processInstance.getProcessDefinitionId());
    }
    @Ignore
    @Test
    public void find(){
        String assignee="王五";
        List<Task> tasks=EntityUtil.getTaskService().createTaskQuery().taskAssignee(assignee).list();
        for(Task task:tasks){
            System.out.println("------------------>>>"+task.getId());
            System.out.println("------------------>>>"+task.getName());
            System.out.println("------------------>>>"+task.getAssignee());
            System.out.println("------------------>>>"+task.getProcessDefinitionId());
            System.out.println("------------------>>>"+task.getProcessInstanceId());
            System.out.println("------------------------------------------------------------------------");
        }
    }
    @Ignore
    @Test
    public void complete(){
        String taskId="25002";
        EntityUtil.getTaskService().complete(taskId);
        System.out.println(taskId+"------------------>>>completed");
    }
}
