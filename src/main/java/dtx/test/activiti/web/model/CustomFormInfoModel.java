/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author gg
 */
@Entity
@Table(name = "custom_form_info")
public class CustomFormInfoModel implements Serializable{
    
    private int id,fields;
    private String customName,template,addFields,pluginData,parse,remark;
    private CustomFormClassModel formClass;

    @Column(nullable = true,columnDefinition = "text")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    private Date createTime;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(nullable = false)
    public int getFields() {
        return fields;
    }

    public void setFields(int fields) {
        this.fields = fields;
    }

    @Column(nullable = false,unique = true,length = 255)
    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @OneToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "formClassId",insertable = true,unique = true)
    public CustomFormClassModel getCustomFormClass() {
        return formClass;
    }

    public void setCustomFormClass(CustomFormClassModel formClass) {
        this.formClass=formClass;
    }

    @Column(nullable = true,columnDefinition = "text")
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Column(nullable = true,columnDefinition = "text")
    public String getAddFields() {
        return addFields;
    }

    public void setAddFields(String addFields) {
        this.addFields = addFields;
    }

    @Column(nullable = true,columnDefinition = "text")
    public String getPluginData() {
        return pluginData;
    }

    public void setPluginData(String data) {
        this.pluginData = data;
    }

    @Column(nullable = true,columnDefinition = "text")
    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
