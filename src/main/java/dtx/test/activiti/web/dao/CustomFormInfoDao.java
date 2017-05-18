/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.test.activiti.web.idao.ICustomFormInfoDao;
import dtx.test.activiti.web.model.CustomFormInfoModel;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 *
 * @author datouxi
 */
public class CustomFormInfoDao extends BasicDao implements ICustomFormInfoDao{

    @Override
    public CustomFormInfoModel getById(int id) {
        return (CustomFormInfoModel) findById(CustomFormInfoModel.class, id);
//        return (CustomFormInfoModel) SessionFactoryUtils.getSession(sessionFactory, false).get(CustomFormInfoModel.class, id);
    }
    
}
