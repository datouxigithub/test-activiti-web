/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.test.activiti.web.util.EntityUtil;
import java.io.IOException;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 *
 * @author datouxi
 */
public class TestProcess {
    public static void main(String[] args) throws IOException {
        
        String modelId="37503";
        RepositoryService repositoryService=EntityUtil.getRepositoryService();
        Model model=repositoryService.getModel(modelId);
        ObjectNode nodeModel=(ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
        BpmnModel bpmnModel=new BpmnJsonConverter().convertToBpmnModel(nodeModel);
        Deployment deployment=repositoryService.createDeployment().name(model.getName()).addString(model.getName()+".bpmn20.xml", new String(new BpmnXMLConverter().convertToXML(bpmnModel))).deploy();
        System.out.println(deployment.toString());
        
        
//        String key="HolidayBill";
        String key=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult().getKey();
        System.out.println(key);
//        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(key);
//        while(!ManageTaskListener.sampleUsers.isEmpty()){
//            String assigner=ManageTaskListener.sampleUsers.pop();
//            Task task=(Task) EntityUtil.getTaskService().createTaskQuery().taskAssignee(assigner).singleResult();
//            System.out.println(assigner+"------------------->>>"+task.toString());
//            EntityUtil.getTaskService().complete(task.getId());
//        }
    }
}
