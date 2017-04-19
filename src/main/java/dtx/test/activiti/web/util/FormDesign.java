/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        Pattern preg=Pattern.compile("/(\\|-<span(((?!<span).)*leipiplugins=\"(radios|checkboxs|select)\".*?)>(.*?)<\\/span>-\\||<(img|input|textarea|select).*?(<\\/select>|<\\/textarea>|\\/>))/s");
        Pattern pregAttr=Pattern.compile("/(\\w+)=\"(.?|.+?)\"/s", Pattern.CASE_INSENSITIVE);
        Pattern pregGroup=Pattern.compile("/<input.*?\\/>/s", Pattern.CASE_INSENSITIVE);
        
//        String[] pregList=preg.split(template);
        Matcher matcher=preg.matcher(template);
        System.out.println("-------------->>>"+matcher.matches());
//        if(pregList[0]!=null){
//            
//        }
        
        return null;
    }
    
    public static void main(String[] args) {
        new FormDesign().parseForm("<p style=\"text-align: center;\">\n" +
"    <br/>\n" +
"</p>\n" +
"<p style=\"text-align: center;\">\n" +
"    <span style=\"font-size: 24px;\">示例表</span>\n" +
"</p>\n" +
"<table class=\"table table-bordered\">\n" +
"    <tbody>\n" +
"        <tr class=\"firstRow\">\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\">\n" +
"                文本框\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"227\">\n" +
"                <input style=\"text-align: left; width: 150px;\" title=\"文本框\" value=\"雷劈网\" name=\"leipiNewField\" orgheight=\"\" orgwidth=\"150\" orgalign=\"left\" orgfontsize=\"\" orghide=\"0\" leipiplugins=\"text\" orgtype=\"text\"/>\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"85\">\n" +
"                下拉菜单\n" +
"            </td>\n" +
"            <td style=\"border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"312\">\n" +
"                {|-<span leipiplugins=\"select\"><select name=\"leipiNewField\" title=\"下拉菜单\" leipiplugins=\"select\" size=\"1\" orgwidth=\"150\" style=\"width: 150px;\"><option value=\"下拉\">\n" +
"                    下拉\n" +
"                </option>\n" +
"                <option value=\"菜单\">\n" +
"                    菜单\n" +
"                </option></select>&nbsp;&nbsp;</span>-|}\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\">\n" +
"                单选\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"41\">\n" +
"                {|-<span leipiplugins=\"radios\" title=\"单选\" name=\"leipiNewField\"><input value=\"单选1\" checked=\"checked\" type=\"radio\"/>单选1&nbsp;<input value=\"单选2\" type=\"radio\"/>单选2&nbsp;</span>-|}\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"85\">\n" +
"                复选\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"312\">\n" +
"                {|-<span leipiplugins=\"checkboxs\" title=\"复选\"><input name=\"leipiNewField\" value=\"复选1\" checked=\"checked\" type=\"checkbox\"/>复选1&nbsp;<input name=\"leipiNewField\" value=\"复选2\" checked=\"checked\" type=\"checkbox\"/>复选2&nbsp;<input name=\"leipiNewField\" value=\"复选3\" type=\"checkbox\"/>复选3&nbsp;</span>-|}\n" +
"            </td>\n" +
"        </tr>\n" +
"        <tr>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\">\n" +
"                宏控件\n" +
"            </td>\n" +
"            <td style=\"border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"41\">\n" +
"                <input name=\"leipiNewField\" value=\"{macros}\" title=\"宏控件\" leipiplugins=\"macros\" orgtype=\"sys_date_cn\" orghide=\"0\" orgfontsize=\"12\" orgwidth=\"150\" style=\"font-size: 12px; width: 150px;\" type=\"text\"/>\n" +
"            </td>\n" +
"            <td style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"85\">\n" +
"                二维码\n" +
"            </td>\n" +
"            <td style=\"border-color: rgb(221, 221, 221);\" valign=\"top\" width=\"312\">\n" +
"                <img name=\"leipiNewField\" title=\"baidu\" value=\"http://www.baidu.com\" orgtype=\"url\" leipiplugins=\"qrcode\" src=\"http://localhost:8080//test-activiti-web/qrcode/e781abe6-6d32-432a-ae50-c32f59b2ed13.gif\" style=\"width: 44px; height: 44px;\" orgwidth=\"44\" orgheight=\"44\"/>\n" +
"            </td>\n" +
"        </tr>\n" +
"    </tbody>\n" +
"</table>\n" +
"<p>\n" +
"    <input name=\"leipiNewField\" leipiplugins=\"listctrl\" value=\"{列表控件}\" readonly=\"readonly\" title=\"采购商品列表\" orgtitle=\"商品名称`数量`单价`小计`描述`\" orgcoltype=\"text`int`int`int`text`\" orgunit=\"```元``\" orgsum=\"0`0`0`1`0`\" orgcolvalue=\"`````\" orgwidth=\"100%\" style=\"width: 100%;\" type=\"text\"/>\n" +
"</p>\n" +
"<p>\n" +
"    <textarea title=\"多行文本\" name=\"leipiNewField\" leipiplugins=\"textarea\" value=\"\" orgrich=\"0\" orgfontsize=\"12\" orgwidth=\"600\" orgheight=\"80\" style=\"font-size:12px;width:600px;height:80px;\"></textarea>\n" +
"</p>\n" +
"<p>\n" +
"    <img name=\"leipiNewField\" title=\"进度\" leipiplugins=\"progressbar\" orgvalue=\"20\" orgsigntype=\"progress-success\" src=\"http://localhost:8080/test-activiti-web/js/ueditor/formdesign/images/progressbar.gif\"/>\n" +
"</p>");
    }
    
}
