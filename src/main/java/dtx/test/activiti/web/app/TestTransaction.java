/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.dao.TestDao;
import dtx.test.activiti.web.model.CustomFormClassModel;
import dtx.test.activiti.web.util.EntityUtil;

/**
 *
 * @author datouxi
 */
public class TestTransaction {
    public static void main(String[] args) {
        CustomFormClassModel formClass=new CustomFormClassModel();
        formClass.setFormClassName("test_transaction");
        formClass.setClassSource("测试一下事务管理".getBytes());
        DynamicSessionFactory sessionFactory=EntityUtil.getDynamicSessionFactory();
        sessionFactory.setEntityClass(CustomFormClassModel.class);
        TestDao td=(TestDao) EntityUtil.getContext().getBean("testDao");
        td.save(formClass, sessionFactory);
    }
}
