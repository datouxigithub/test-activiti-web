/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author gg
 */
@Entity
@Table(name = "custom_form_class")
public class CustomFormClassModel implements Serializable{
    private int id;
    private String formClassName;
    private byte[] classSource;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false,length = 255)
    public String getFormClassName() {
        return formClassName;
    }

    public void setFormClassName(String formClassName) {
        this.formClassName = formClassName;
    }

    @Column(nullable = true,columnDefinition = "blob")
    public byte[] getClassSource() {
        return classSource;
    }

    public void setClassSource(byte[] classSource) {
        this.classSource = classSource;
    }
}
