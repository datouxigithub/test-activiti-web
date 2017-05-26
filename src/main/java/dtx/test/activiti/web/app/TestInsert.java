/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.app;

import dtx.test.activiti.web.dao.TestDao;
import dtx.test.activiti.web.idao.ICustomFormInfoDao;
import dtx.test.activiti.web.model.CustomFormInfoModel;
import dtx.test.activiti.web.util.EntityUtil;
import java.lang.reflect.Field;

/**
 *
 * @author datouxi
 */
public class TestInsert {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        EntityUtil.getCustomFormClassHelper().initExistsClasses();
        ICustomFormInfoDao dao=(ICustomFormInfoDao) EntityUtil.getContext().getBean("customFormInfoDao");
        CustomFormInfoModel formInfo=dao.getById(2);
        Object formObj=Class.forName(formInfo.getCustomFormClass().getFormClassName()).newInstance();
        Field f=formObj.getClass().getDeclaredField("data_1");
        f.setAccessible(true);
        f.set(formObj, "请假天数");
        f=formObj.getClass().getDeclaredField("data_2");
        f.setAccessible(true);
        f.set(formObj, "请假原因");
        
        TestDao td=(TestDao) EntityUtil.getContext().getBean("testDao");
        td.save(formObj, EntityUtil.getDynamicSessionFactory());
    }
}
