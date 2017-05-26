/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dtx.test.activiti.web.app.CustomFormClassHelper;
import dtx.test.activiti.web.app.DynamicSessionFactory;
import dtx.test.activiti.web.app.ManageTaskListener;
import dtx.test.activiti.web.dao.TestDao;
import dtx.test.activiti.web.idao.ICustomFormInfoDao;
import dtx.test.activiti.web.model.ApproverOptionModel;
import dtx.test.activiti.web.model.CustomFormInfoModel;
import dtx.test.activiti.web.model.DefaultUserForm;
import dtx.test.activiti.web.model.HolidayModel;
import dtx.test.activiti.web.util.EntityUtil;
import dtx.test.activiti.web.util.FormDesign;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
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
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author datouxi
 */
@Controller
@RequestMapping(value = "/test")
//@SessionAttributes("userForm")
@SessionAttributes(value = {"userForm"})
public class TestController {
    
    @RequestMapping(value = "input",method = RequestMethod.GET)
    public String input(@RequestParam("id") int id,Model model) throws IOException, CannotCompileException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        CustomFormInfoModel formInfo=dao.getById(id);
        model.addAttribute("formInfo", formInfo);
        CustomFormClassHelper helper=EntityUtil.getCustomFormClassHelper();
        helper.initExistsClasses();
        model.addAttribute("userForm",Class.forName(formInfo.getCustomFormClass().getFormClassName()).newInstance());
        return "input";
    }
    
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public void submit(@ModelAttribute("userForm") Object userForm,HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalArgumentException, IllegalAccessException{
        EntityUtil.getCustomFormClassHelper().initExistsClasses();
        TestDao dao=(TestDao) EntityUtil.getContext().getBean("testDao");
        DynamicSessionFactory dsf=EntityUtil.getDynamicSessionFactory();
        Stack<SessionFactory> stack=dsf.view();
        SessionFactory sessionFactory=dsf.getSessionFactory();
        dao.save(userForm, sessionFactory);
        ApproverOptionModel option=new ApproverOptionModel();
        option.setAppvoer(ManageTaskListener.sampleUsers.pop());
        option.setComment(request.getParameter("comment"));
        option.setApprolDate(new Date());
        option.setUserFormId(userForm.getClass().getSimpleName()+"."+((DefaultUserForm)userForm).getId());
        dao.save(option, sessionFactory);
        response.getWriter().write(option.toString());
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
