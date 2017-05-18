/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.test.activiti.web.app.ManageTaskListener;
import dtx.test.activiti.web.idao.ICustomFormInfoDao;
import dtx.test.activiti.web.model.CustomFormInfoModel;
import dtx.test.activiti.web.util.EntityUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author datouxi
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
    
    @RequestMapping(value = "input",method = RequestMethod.GET)
    public String input(@RequestParam("id") int id,Model model) throws IOException{
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        CustomFormInfoModel formInfo=dao.getById(id);
        model.addAttribute("formInfo", formInfo);
        return "input";
    }
    
    @RequestMapping(value = "run/{modelId}")
    public void run(@PathVariable("modelId")String modelId,HttpServletRequest request,HttpServletResponse response) throws IOException{
        RepositoryService repositoryService=EntityUtil.getRepositoryService();
        org.activiti.engine.repository.Model model=repositoryService.getModel(modelId);
        ObjectNode nodeModel=(ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
        BpmnModel bpmnModel=new BpmnJsonConverter().convertToBpmnModel(nodeModel);
        Deployment deployment=repositoryService.createDeployment().name(model.getName()).addString(model.getName()+".bpmn20.xml", new String(new BpmnXMLConverter().convertToXML(bpmnModel))).deploy();
        System.out.println(deployment.toString());
        
        
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        ProcessInstance processInstance=EntityUtil.getRuntimeService().startProcessInstanceByKey(processDefinition.getKey());
        while(!ManageTaskListener.sampleUsers.isEmpty()){
            String assigner=ManageTaskListener.sampleUsers.pop();
            Task task=(Task) EntityUtil.getTaskService().createTaskQuery().taskAssignee(assigner).singleResult();
            System.out.println(assigner+"------------------->>>"+task.toString());
            EntityUtil.getTaskService().complete(task.getId());
        }
        
        response.sendRedirect(String.format("%s/workflow/list", request.getContextPath()));
    }
}
