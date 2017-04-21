/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author datouxi
 */
public class FormDesign {
    
    public Map<String,Object> parseForm(String template){
        return parseForm(template, 0);
    }
    
    public Map<String,Object> parseForm(String template,int fields){
        
        String templateParse=template;
        
        Pattern preg=Pattern.compile("\\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\\/span>-\\||<(img|input|textarea|select).*?(<\\/select>|<\\/textarea>|\\/>)");
        Pattern pregAttr=Pattern.compile("(\\w+)=\"(.?|.+?)\"", Pattern.CASE_INSENSITIVE);
        Pattern pregGroup=Pattern.compile("<input.*?\\/>", Pattern.CASE_INSENSITIVE);
        Pattern pregVal=Pattern.compile("(?<=\").*(?=\")");
        
        boolean isNew=false;
        String name="",leipiplugins="",selectDot="";
        int checkboxs=0;
        
        Map addFields=new HashMap();
        
        List<List<String>> templateArr=matchAll(preg, template);

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
                    attrAll.put("content", (attrAll.get("content")!=null : (String)attrAll.get("content"):"")+"<input type=\"checkbox\" name=\""+(String)optionsMap.get("name")+" value=\""+(String)optionsMap.get("value")+"\" "+(optionsMap.get("checked")!=null ? "checked=\"checked\"":"")+"/>"+(String)optionsMap.get("value")+"&nbsp;");
                }
                attrAll.put("content", (String)attrAll.get("content")+"</span>");
                
                //未处理
                
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
                                                +"\" value=\""+optionsMap.get("value")
                                                +(optionsMap.get("checked")!=null ? "checked=\"checked\"":"")+"/>"
                                                +optionsMap.get("value")+"&nbsp;");
                    }
                    attrAll.put("content", (String)attrAll.get("content")+"</span>");
                }else{
                    attrAll.put("content", isNew ? plugin.replace(name, "str_replace"):plugin);
                }
                
                if(isNew){
                    Map tmpMap=new HashMap();
                    tmpMap.put("name", name);
                    tmpMap.put("leipiplugins", attrAll.get("leipiplugins"));
                    addFields.put(name, tmpMap);
                }
                
                //未处理
            }
//            break;
        }
        
        return null;
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
    
    private void print(List<List<String>> list){
        for(List<String> strList:list){
            System.out.println("------------------------------------------------------------------");
            for(String str:strList)
                System.out.println(str);
        }
        System.out.println("----------------------"+list.toString()+"------------------------------");
    }
    
    public static void main(String[] args) {
        new FormDesign().parseForm("<p style=\"text-align: center;\"><br/></p><p style=\"text-align: center;\"><span style=\"font-size: 24px;\">示例表</span></p><table class=\"table table-bordered\"><tbody><tr class=\"firstRow\"><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">文本框</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"227\"><input style=\"text-align: left; width: 150px;\" title=\"文本框\" value=\"雷劈网\" name=\"leipiNewField\" orgheight=\"\" orgwidth=\"150\" orgalign=\"left\" orgfontsize=\"\" orghide=\"0\" leipiplugins=\"text\" orgtype=\"text\"/></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">下拉菜单</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"312\">{|-<span leipiplugins=\"select\"><select name=\"leipiNewField\" title=\"下拉菜单\" leipiplugins=\"select\" size=\"1\" orgwidth=\"150\" style=\"width: 150px;\"><option value=\"下拉\">下拉</option><option value=\"菜单\">菜单</option></select>&nbsp;&nbsp;</span>-|}</td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">单选</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"41\">{|-<span leipiplugins=\"radios\" title=\"单选\" name=\"leipiNewField\"><input value=\"单选1\" type=\"radio\" checked=\"checked\"/>单选1&nbsp;<input value=\"单选2\" type=\"radio\"/>单选2&nbsp;</span>-|}</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">复选</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"312\">{|-<span leipiplugins=\"checkboxs\" title=\"复选\"><input name=\"leipiNewField\" value=\"复选1\" type=\"checkbox\" checked=\"checked\"/>复选1&nbsp;<input name=\"leipiNewField\" value=\"复选2\" type=\"checkbox\" checked=\"checked\"/>复选2&nbsp;<input name=\"leipiNewField\" value=\"复选3\" type=\"checkbox\"/>复选3&nbsp;</span>-|}</td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\">宏控件</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"41\"><input name=\"leipiNewField\" type=\"text\" value=\"{macros}\" title=\"宏控件\" leipiplugins=\"macros\" orgtype=\"sys_date_cn\" orghide=\"0\" orgfontsize=\"12\" orgwidth=\"150\" style=\"font-size: 12px; width: 150px;\"/></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"85\">二维码</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"312\"><img name=\"leipiNewField\" title=\"雷劈网\" value=\"http://www.leipi.org\" orgtype=\"url\" leipiplugins=\"qrcode\" src=\"/Public/js/ueditor/formdesign/images/qrcode.gif\" orgwidth=\"40\" orgheight=\"40\" style=\"width: 40px; height: 40px;\"/></td></tr></tbody></table><p><input name=\"leipiNewField\" leipiplugins=\"listctrl\" type=\"text\" value=\"{列表控件}\" readonly=\"readonly\" title=\"采购商品列表\" orgtitle=\"商品名称`数量`单价`小计`描述`\" orgcoltype=\"text`int`int`int`text`\" orgunit=\"```元``\" orgsum=\"0`0`0`1`0`\" orgcolvalue=\"`````\" orgwidth=\"100%\" style=\"width: 100%;\"/></p><p><textarea title=\"多行文本\" name=\"leipiNewField\" leipiplugins=\"textarea\" value=\"\" orgrich=\"0\" orgfontsize=\"12\" orgwidth=\"600\" orgheight=\"80\" style=\"font-size:12px;width:600px;height:80px;\"></textarea></p><p><img name=\"leipiNewField\" title=\"进度条\" leipiplugins=\"progressbar\" orgvalue=\"20\" orgsigntype=\"progress-info\" src=\"/Public/js/ueditor/formdesign/images/progressbar.gif\"/></p>");
    }
    
}
