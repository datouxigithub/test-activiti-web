/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.test.activiti.web.idao.ICustomFormClassDao;
import dtx.test.activiti.web.model.CustomFormClassModel;

/**
 *
 * @author gg
 */
public class CustomFormClassDao extends BasicDao implements ICustomFormClassDao{

    @Override
    public CustomFormClassModel getByClassName(String className) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
