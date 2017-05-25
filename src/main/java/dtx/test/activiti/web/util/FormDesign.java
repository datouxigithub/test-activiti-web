/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import dtx.test.activiti.web.app.DynamicSessionFactory;
import dtx.test.activiti.web.dao.TestDao;
import dtx.test.activiti.web.model.CustomFormClassModel;
import dtx.test.activiti.web.model.CustomFormInfoModel;
import dtx.test.activiti.web.model.DefaultUserForm;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 *
 * @author datouxi
 */
public class FormDesign {
    
    public final static String PACKAGE="dtx.test.activiti.web.model";
    
    public CustomFormInfoModel parseForm(String customName,String template){
        CustomFormInfoModel customForm=new CustomFormInfoModel();
        customForm.setCustomName(customName);
        CustomFormClassModel formClass=new CustomFormClassModel();
        formClass.setFormClassName(obtainCustomClassName());
        customForm.setCustomFormClass(formClass);
        JSONObject result=parseForm(template);
        customForm.setTemplate((String) result.get("template"));
        customForm.setParse((String) result.get("parse"));
        customForm.setFields((int) result.get("fields"));
        customForm.setAddFields(result.get("add_fields").toString());
        customForm.setPluginData(result.get("data").toString());
        return customForm;
    }
    
    public JSONObject parseForm(String template){
        return parseForm(template, 0);
    }
    
    public JSONObject parseForm(String template,int fields){
        
        String templateParse=template;
        
        Pattern preg=Pattern.compile("\\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\\/span>-\\||<(img|input|textarea|select).*?(<\\/select>|<\\/textarea>|\\/>)");
        Pattern pregAttr=Pattern.compile("(\\w+)=\"(.?|.+?)\"", Pattern.CASE_INSENSITIVE);
        Pattern pregGroup=Pattern.compile("<input.*?\\/>", Pattern.CASE_INSENSITIVE);
        Pattern pregVal=Pattern.compile("(?<=\").*(?=\")");
//        Pattern pregKey=Pattern.compile("\\b.*(?=\\=)");
        
        boolean isNew=false;
        String name="",leipiplugins="",selectDot="";
        int checkboxs=0;
        
        Map addFields=new HashMap();
        
        List<List<String>> templateArr=matchAll(preg, template);
        List templateData=new ArrayList<>();

        for(List<String> contentElement:templateArr){
            String tag=contentElement.get(5)!=null ? contentElement.get(5):contentElement.get(3);
            String plugin=contentElement.get(0);
            
            Map<String,Object> attrAll=new HashMap<>();
            
            if(tag.equals("radios")||tag.equals("checkboxs")){
                plugin=contentElement.get(1);
            }
            else if(tag.equals("select")){
                plugin=plugin.replace("|-", "").replace("-|", "");
            }
            
            List<List<String>> parseAttr=matchAll(pregAttr, plugin);
            for(List<String> attrElement:parseAttr){
                String attr=attrElement.get(0).trim();
                if(StringUtils.isEmpty(attr))continue;

                String val=matchOne(pregVal, attr);
                attr=attrElement.get(1).trim();
                if(attr.equals("name")){
                    if(val.equals("leipiNewField")){
                        isNew=true;
                        val="data_"+(++fields);
                    }
                    name=val;
                }
                if(tag.equals("select")&&attr.equals("value")){
                    attrAll.put(attr, (attrAll.get(attr)==null ? "":attrAll.get(attr))+selectDot+val);
                    selectDot=",";
                }else{
                    attrAll.put(attr, val);
                }
            }
            
            if(tag.equals("checkboxs")){
                plugin=contentElement.get(0).replace("|-", "").replace("-|", "");
                name="checkboxs_"+checkboxs;
                attrAll.put("parse_name", name);
                attrAll.put("name", "");
                attrAll.put("value", "");
                String options=contentElement.get(4);
                String dotName="",dotValue="";
                if(attrAll.get("title")==null)attrAll.put("title", "");
                attrAll.put("content", "<span leipiplugins=\"checkboxs\" title=\""+attrAll.get("title")+"\">");
                Matcher pregGroupMatcher=pregGroup.matcher(options);
                while(pregGroupMatcher.find()){
                    parseAttr=matchAll(pregAttr, pregGroupMatcher.group());
                    isNew=false;
                    Map optionsMap=new HashMap();
                    for(List<String> attrElement:parseAttr){
                        String val=matchOne(pregVal, attrElement.get(0));
                        if("name".equals(attrElement.get(1))){
                            if("leipiNewField".equals(val)){
                                isNew=true;
                                val="data_"+(++fields);
                            }
                            attrAll.put("name", (attrAll.get("name")!=null ? attrAll.get("name"):"")+dotName+val);
                            dotName=",";
                        }else if("value".equals(attrElement.get(1))){
                            attrAll.put("value", (attrAll.get("value")!=null ? attrAll.get("value"):"")+dotValue+val);
                            dotValue=",";
                        }
                        optionsMap.put(attrElement.get(1), val);
                    }
                    attrAll.put("options", optionsMap);
                    if(isNew){
                        Map tmpMap=new HashMap();
                        tmpMap.put("name", optionsMap.get("name"));
                        tmpMap.put("leipiplugins", attrAll.get("leipiplugins"));
                        addFields.put(optionsMap.get("name"), tmpMap);
                    }
                    attrAll.put("content", (attrAll.get("content")!=null ? (String)attrAll.get("content"):"")
                                            +"<input type=\"checkbox\" name=\""
                                            +(String)optionsMap.get("name")+"\" value=\""
                                            +(String)optionsMap.get("value")+"\" "
                                            +(optionsMap.get("checked")!=null ? "checked=\"checked\"":"")+"/>"
                                            +(String)optionsMap.get("value")+"&nbsp;");
                }
                attrAll.put("content", (String)attrAll.get("content")+"</span>");
                
                template=strReplaceOnce(plugin, (String) attrAll.get("content"), template);
                templateParse=strReplaceOnce(plugin, "{"+name+"}", templateParse).replace("|-", "").replace("-|", "");
                templateData.add(attrAll);
                checkboxs++;
                
            }else if(!StringUtils.isEmpty(name)){
                if("radios".equals(tag)){
                    plugin=contentElement.get(0).replace("|-", "").replace("-|", "");//?
                    attrAll.put("value", "");
                    String options=contentElement.get(4);
                    String dot="";
                    attrAll.put("content", "<span leipiplugins=\"radios\" name=\""+(attrAll.get("name")!=null ? (String)attrAll.get("name"):"")+"\" title=\""+(attrAll.get("title")!=null ? (String)attrAll.get("title"):"")+"\">");
                    Matcher pregGroupMatcher=pregGroup.matcher(options);
                    while(pregGroupMatcher.find()){
                        parseAttr=matchAll(pregAttr, pregGroupMatcher.group());
                        Map optionsMap=new HashMap();
                        for(List<String> attrElement:parseAttr){
                            if("value".equals(attrElement.get(1))){
                                attrAll.put("value", (attrAll.get("value")!=null ? (String)attrAll.get("value"):"")+dot+matchOne(pregVal, attrElement.get(0)));
                                dot=",";
                            }
                            optionsMap.put(attrElement.get(1), matchOne(pregVal, attrElement.get(0)));
                        }
                        optionsMap.put("name", attrAll.get("name"));
                        attrAll.put("options", optionsMap);
                        attrAll.put("content", (attrAll.get("content")!=null ? (String)attrAll.get("content"):"")
                                                +"<input type=\"radio\" name=\""
                                                +(attrAll.get("name")!=null ? (String)attrAll.get("name"):"")
                                                +"\" value=\""+optionsMap.get("value")+"\" "
                                                +(optionsMap.get("checked")!=null ? "checked=\"checked\"":"")+"/>"
                                                +optionsMap.get("value")+"&nbsp;");
                    }
                    attrAll.put("content", (String)attrAll.get("content")+"</span>");
                }else{
                    attrAll.put("content", isNew ? plugin.replace("leipiNewField", name):plugin);
                }
                
                if(isNew){
                    Map tmpMap=new HashMap();
                    tmpMap.put("name", name);
                    tmpMap.put("leipiplugins", attrAll.get("leipiplugins"));
                    addFields.put(name, tmpMap);
                }
                
                template=strReplaceOnce(plugin, (String) attrAll.get("content"), template);
                templateParse=strReplaceOnce(plugin, "{"+name+"}", templateParse).replace("|-", "").replace("-|", "");
                templateData.add(attrAll);
            }
        }
        Map<String,Object> parseForm=new HashMap<>();
        parseForm.put("fields", fields);
        parseForm.put("template", template);
        parseForm.put("parse", templateParse);
        parseForm.put("data", templateData);
        parseForm.put("add_fields", addFields);
        return new JSONObject(parseForm);
    }
    
    private String strReplaceOnce(String needle,String replace,String hayStack){
        int pos=hayStack.indexOf(needle);
        if(pos==-1)
            return hayStack;
        else{
            StringBuilder sb=new StringBuilder(hayStack);
            sb.replace(pos, pos+needle.length(), replace);
            return sb.toString();
        }
    }
    
    private String matchOne(Pattern pat,String attr){
        Matcher matcher=pat.matcher(attr);
        String val="";
        if(matcher.find())val=matcher.group();
        return val;
    }
    
    private List<List<String>> matchAll(Pattern pat,String content){
        
        Matcher matcher=pat.matcher(content);
        List<List<String>> result=new ArrayList<>();
        while(matcher.find()){
            List<String> groups=new ArrayList<>();
            for(int i=0,len=matcher.groupCount();i<len;i++)
                groups.add(matcher.group(i));
            result.add(groups);
        }
        
        return result;
    }
    
    private String fieldTypeSql(String leipiplugins){
        switch(leipiplugins){
            case "textarea":
            case "listctrl":return "text NOT NULL";
            case "checkboxs":return "tinyint(1) UNSIGNED NOT NULL DEFAULT 0";
            default:return "varchar(255) NOT NULL DEFAULT ''";
        }
    }
    
    private CtClass fieldType(String leipiplugins) throws NotFoundException{
        switch(leipiplugins){
            case "checkboxs":return CtClass.booleanType;
            default:return ClassPool.getDefault().get("java.lang.String");
        }
    }
    
    private String getter(String field,CtClass fieldTypeClass){
        return String.format("public %s get%s(){return this.%s;}",fieldTypeClass.getName(),methodName(field),field);
    }
    
    private String setter(String field,CtClass fieldTypeClass){
        return String.format("public void set%s(%s value){this.%s=value;}", methodName(field),fieldTypeClass.getName(),field);
    }
    
    private String methodName(String field){
//        return (field.substring(0, 1).toUpperCase()+field.substring(1)).replace("_", "");
        return (field.substring(0, 1).toUpperCase()+field.substring(1));
    }
    
    public void setFormFieldValue(Object form,String field,Object value) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        if(!(form instanceof DefaultUserForm))return;
        Field f=form.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(form, value);
        f.setAccessible(false);
//        Method method=form.getClass().getDeclaredMethod(String.format("set%s", methodName(field)));
//        method.invoke(form, value);
    }
    
    public String obtainCustomClassName(){
        return FormDesign.PACKAGE+".UserForm"+UUID.randomUUID().toString().replace("-", "");
    }
    
    public CtClass obtainTableClass(String className,String tableName,JSONObject addFields) throws NotFoundException, CannotCompileException{
        ClassPool pool=ClassPool.getDefault();
        CtClass newClass=pool.makeClass(className);
        newClass.setSuperclass(pool.get(FormDesign.PACKAGE+".DefaultUserForm"));
        ClassFile classFile=newClass.getClassFile();
        classFile.setMajorVersion(ClassFile.JAVA_7);
        
        AnnotationsAttribute classAttr=new AnnotationsAttribute(classFile.getConstPool(), AnnotationsAttribute.visibleTag);
        classAttr.addAnnotation(new Annotation("javax.persistence.Entity", classFile.getConstPool()));
        if(tableName!=null&&!"".equals(tableName)){
            Annotation tableAnnot=new Annotation("javax.persistence.Table", classFile.getConstPool());
            tableAnnot.addMemberValue("name", new StringMemberValue(tableName, classFile.getConstPool()));
            classAttr.addAnnotation(tableAnnot);
        }
        classFile.addAttribute(classAttr);
        
        Iterator iter=addFields.keys();
        while(iter.hasNext()){
            JSONObject value=addFields.getJSONObject((String) iter.next());
            CtClass fieldTypeClass=fieldType((String) value.get("leipiplugins"));
            CtField ctf=new CtField(fieldTypeClass, (String) value.get("name"), newClass);
            ctf.setModifiers(Modifier.PRIVATE);
            newClass.addField(ctf);
            CtMethod setter=CtMethod.make(setter((String) value.get("name"), fieldTypeClass), newClass);
            newClass.addMethod(setter);
            CtMethod getter=CtMethod.make(getter((String) value.get("name"), fieldTypeClass), newClass);
            AnnotationsAttribute getterAttr=new AnnotationsAttribute(classFile.getConstPool(), AnnotationsAttribute.visibleTag);
            Annotation notNullAnnot=new Annotation("javax.persistence.Column", classFile.getConstPool());
            notNullAnnot.addMemberValue("nullable", new BooleanMemberValue(false, classFile.getConstPool()));
            notNullAnnot.addMemberValue("columnDefinition", new StringMemberValue("text", classFile.getConstPool()));
            getterAttr.addAnnotation(notNullAnnot);
            getter.getMethodInfo().addAttribute(getterAttr);
            newClass.addMethod(getter);
        }
        
        return newClass;
    }
    
    public String parseTable(String tableName,Map addFields){
        String fields="";
        Iterator iter=addFields.values().iterator();
        while(iter.hasNext()){
            Map value=(Map) iter.next();
            fields+="`"+value.get("name")+"` "+fieldTypeSql((String) value.get("leipiplugins"))+",";
        }
        
        return "CREATE TABLE "+tableName+" IF NOT EXISTS("
                +"`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
                +"`uid` int(10) unsigned NOT NULL DEFAULT '0',"
                +"`foreign_id` int(10) unsigned NOT NULL DEFAULT '0',"
                +fields
                +"`is_del` tinyint(1) unsigned NOT NULL DEFAULT '0',"
                +"`updatetime` int(10) unsigned NOT NULL DEFAULT '0',"
                +"`dateline` int(10) unsigned NOT NULL DEFAULT '0',"
                +"PRIMARY KEY (`id`),"
                +"KEY `uid` (`uid`),"
                +"KEY `foreign_id` (`foreign_id`)"
                +") ENGINE=MyISAM DEFAULT CHARSET=utf8;";
    }
    
    public static void main(String[] args) throws Exception {
        FormDesign fd=new FormDesign();
//        CustomFormInfoModel customFormInfoModel=fd.parseForm("测试表单", "<p style=\"text-align: center;\"><br/></p><p style=\"text-align: center;\"><span style=\"font-size: 24px;\">示例表</span></p><table class=\"table table-bordered\"><tbody><tr class=\"firstRow\"><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">文本框</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"227\"><input style=\"text-align: left; width: 150px;\" title=\"文本框\" value=\"雷劈网\" name=\"leipiNewField\" orgheight=\"\" orgwidth=\"150\" orgalign=\"left\" orgfontsize=\"\" orghide=\"0\" leipiplugins=\"text\" orgtype=\"text\"/></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">下拉菜单</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"312\">{|-<span leipiplugins=\"select\"><select name=\"leipiNewField\" title=\"下拉菜单\" leipiplugins=\"select\" size=\"1\" orgwidth=\"150\" style=\"width: 150px;\"><option value=\"下拉\">下拉</option><option value=\"菜单\">菜单</option></select>&nbsp;&nbsp;</span>-|}</td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">单选</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"41\">{|-<span leipiplugins=\"radios\" title=\"单选\" name=\"leipiNewField\"><input value=\"单选1\" type=\"radio\" checked=\"checked\"/>单选1&nbsp;<input value=\"单选2\" type=\"radio\"/>单选2&nbsp;</span>-|}</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">复选</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"312\">{|-<span leipiplugins=\"checkboxs\" title=\"复选\"><input name=\"leipiNewField\" value=\"复选1\" type=\"checkbox\" checked=\"checked\"/>复选1&nbsp;<input name=\"leipiNewField\" value=\"复选2\" type=\"checkbox\" checked=\"checked\"/>复选2&nbsp;<input name=\"leipiNewField\" value=\"复选3\" type=\"checkbox\"/>复选3&nbsp;</span>-|}</td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">宏控件</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"41\"><input name=\"leipiNewField\" type=\"text\" value=\"{macros}\" title=\"宏控件\" leipiplugins=\"macros\" orgtype=\"sys_date_cn\" orghide=\"0\" orgfontsize=\"12\" orgwidth=\"150\" style=\"font-size: 12px; width: 150px;\"/></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">二维码</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"312\"><img name=\"leipiNewField\" title=\"雷劈网\" value=\"http://www.leipi.org\" orgtype=\"url\" leipiplugins=\"qrcode\" src=\"/Public/js/ueditor/formdesign/images/qrcode.gif\" orgwidth=\"40\" orgheight=\"40\" style=\"width: 40px; height: 40px;\"/></td></tr></tbody></table><p><input name=\"leipiNewField\" leipiplugins=\"listctrl\" type=\"text\" value=\"{列表控件}\" readonly=\"readonly\" title=\"采购商品列表\" orgtitle=\"商品名称`数量`单价`小计`描述`\" orgcoltype=\"text`int`int`int`text`\" orgunit=\"```元``\" orgsum=\"0`0`0`1`0`\" orgcolvalue=\"`````\" orgwidth=\"100%\" style=\"width: 100%;\"/></p><p><textarea title=\"多行文本\" name=\"leipiNewField\" leipiplugins=\"textarea\" value=\"\" orgrich=\"0\" orgfontsize=\"12\" orgwidth=\"600\" orgheight=\"80\" style=\"font-size:12px;width:600px;height:80px;\"></textarea></p><p><img name=\"leipiNewField\" title=\"进度条\" leipiplugins=\"progressbar\" orgvalue=\"20\" orgsigntype=\"progress-info\" src=\"/Public/js/ueditor/formdesign/images/progressbar.gif\"/></p>");
        CustomFormInfoModel customFormInfoModel=fd.parseForm("请假表单", "<p style=\"text-align: left; text-indent: 0em;\">\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"font-size: 24px; font-family: 微软雅黑,Microsoft YaHei;\"><strong><span style=\"font-size: 24px; color: rgb(0, 0, 0);\">请假申请表</span></strong></span><br/>\n</p>\n<table>\n<tbody>\n<tr class=\"firstRow\">\n<td style=\"word-break: break-all;\" align=\"right\" width=\"224\" valign=\"middle\" height=\"71\">\n请假天数<br/>\n</td>\n<td align=\"left\" width=\"489\" valign=\"middle\" height=\"71\">\n<input name=\"leipiNewField\" title=\"请假天数\" value=\"\" leipiplugins=\"text\" orghide=\"0\" style=\"text-align: left; width: 150px;\" orgalign=\"left\" orgwidth=\"150\" orgtype=\"text\" type=\"text\"/>\n</td>\n</tr>\n<tr>\n<td align=\"right\" width=\"224\" valign=\"middle\" height=\"82\">\n请假原因<br/>\n</td>\n<td align=\"left\" width=\"489\" valign=\"middle\" height=\"82\">\n<textarea title=\"请假原因\" name=\"leipiNewField\" leipiplugins=\"textarea\" value=\"\" orgrich=\"0\" orgfontsize=\"\" orgwidth=\"300\" orgheight=\"80\" style=\"width:300px;height:80px;\"></textarea>\n</td>\n</tr>\n</tbody>\n</table>\n<p>\n<br/>\n</p>");
        CtClass ctc=fd.obtainTableClass(customFormInfoModel.getCustomFormClass().getFormClassName(), null, new JSONObject(customFormInfoModel.getAddFields()));
        DynamicSessionFactory sf=EntityUtil.getDynamicSessionFactory();
        sf.createNewSessionFactory(ctc.toClass());
        TestDao dao=(TestDao) EntityUtil.getContext().getBean("testDao");
        customFormInfoModel.getCustomFormClass().setClassSource(ctc.toBytecode());
        customFormInfoModel.setCreateTime(new Date());
        dao.save(customFormInfoModel, sf);
    }
    
}
