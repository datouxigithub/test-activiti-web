/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.idao;

import dtx.oa.rbac.basic.BasicDaoInter;
import dtx.test.activiti.web.model.CustomFormInfoModel;

/**
 *
 * @author datouxi
 */
public interface ICustomFormInfoDao extends BasicDaoInter{

    public CustomFormInfoModel getById(int id);
    
}
