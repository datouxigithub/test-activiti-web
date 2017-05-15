/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 *
 * @author gg
 */
public class TestDao{
    
    public void save(Object obj,SessionFactory sf){
        Session session=SessionFactoryUtils.getSession(sf, false);
        session.save(obj);
    }
    
}
