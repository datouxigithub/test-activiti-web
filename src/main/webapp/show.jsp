<%@page import="java.util.List"%>
<%@page import="org.json.JSONException"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Iterator"%>
<%@page import="dtx.test.activiti.web.util.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form id="saveform" name="saveform" action="" method="post">
            <%
                String json=request.getAttribute("parseForm").toString();
                JSONObject result=new JSONObject(json);
                StringBuilder sb=new StringBuilder();
                boolean isVal=false;
                String val="";
                String parseStr=result.getString("parse");
                for(int i=0,len=parseStr.length();i<len;i++){
                    String s=parseStr.substring(i, i+1);
                    if("{".equals(s)){
                        isVal=true;
                    }else if("}".equals(s)){
                        if(isVal){
                            for(int j=0;j<result.getJSONArray("data").length();j++){
                                JSONObject obj=result.getJSONArray("data").getJSONObject(j);
                                String targetName="";
                                try{
                                    targetName=obj.getString("parse_name");
                                }catch(JSONException e){
                                    targetName=obj.getString("name");
                                }
                                if(val.equals(targetName)){
                                    if("macros".equals(obj.getString("leipiplugins"))){
                                        IMacro im=IMacroContainer.chooseMacro(obj.getString("orgtype"));
                                        String element="";
                                        String attrs="";
                                        Iterator<String> iter=obj.keys();
                                        while(iter.hasNext()){
                                            String key=iter.next();
                                            if("value".equals(key)||"content".equals(key))continue;
                                            attrs+=" "+key+"=\""+obj.getString(key)+"\"";
                                        }
                                        if(im.isMulti()){
                                            element+="<select"+attrs+">";
                                            List<MacroEntry> entrys=im.macroValues(request,response);
                                            for(MacroEntry entry:entrys){
                                                element+="<option value=\""+entry.getValue()+"\">"+entry.getKey()+"</option>";
                                            }
                                            element+="</select>";
                                        }
                                        else
                                            element+="<input"+attrs+" value=\""+im.macroValues(request,response).get(0).getValue()+"\" disabled />";
                                        sb.append(element);
                                    }else if("listctrl".equals(obj.getString("leipiplugins"))){
                                        String[] orgTitle=obj.getString("orgtitle").trim().substring(0, obj.getString("orgtitle").length()-1).split("`");//标题
                                        String[] orgColType=obj.getString("orgcoltype").trim().substring(0, obj.getString("orgcoltype").length()-1).split("`");//类型
                                        String[] orgSum=obj.getString("orgsum").trim().substring(0, obj.getString("orgsum").length()-1).split("`");//合计
                                        String[] orgUnit=obj.getString("orgunit").trim().substring(0, obj.getString("orgunit").length()-1).split("`");//单位
                                        String[] orgColValue=obj.getString("orgcolvalue").trim().substring(0, obj.getString("orgcolvalue").length()-1).split("`");//默认值
                                        
                                        String ths="";
                                        String tbTds="";
                                        int tdSum=0;
                                        for(int x=0;x<orgTitle.length;x++){
                                            String title=orgTitle[x];
                                            tdSum++;
                                            String unit="";
                                            try{
                                                unit=(orgUnit[x]!=null&&!"".equals(orgUnit[x])) ? "("+orgUnit[x]+")":"";
                                            }catch(Exception ex){}
                                            ths+="<th>"+title+unit+"</th>";
                                            switch(orgColType[x]){
                                                case "text":
                                                    tbTds+="<td><input class=\"input-medium\" type=\"text\" name=\""+obj.getString("name")+"\"["+x+"][]\" value=\""+(orgColValue[x]!=null ? orgColValue[x]:"")+"\">"+(orgUnit[x]!=null ? orgUnit[x]:"")+"</td>";
                                                    break;
                                                case "textarea":
                                                    tbTds+="<td><textarea class=\"input-medium\" name=\""+obj.getString("name")+"\"["+x+"][]\""+(orgColValue[x]!=null ? orgColValue[x]:"")+"</textarea>"+(orgUnit[x]!=null ? orgUnit[x]:"")+"</td>";
                                                    break;
                                                case "int":
                                                    tbTds+="<td><input class=\"input-medium\" type=\"text\" name=\""+obj.getString("name")+"\"["+x+"][]\" value=\""+(orgColValue[x]!=null ? orgColValue[x]:"")+"\">"+(orgUnit[x]!=null ? orgUnit[x]:"")+"</td>";
                                                    break;
                                                case "calc":
                                                    tbTds+="<td><input class=\"input-medium\" type=\"text\" name=\""+obj.getString("name")+"\"["+x+"][]\" value=\""+(orgColValue[x]!=null ? orgColValue[x]:"")+"\">"+(orgUnit[x]!=null ? orgUnit[x]:"")+"</td>";
                                                    break;
                                                default:break;
                                            }
                                        }
                                        
                                        String listTable="<table id=\""+obj.getString("name")+"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\""+obj.getString("style")+"\" border=\"1\">"
                                                            +"<thead><tr><th colspan=\""+tdSum+"\">"+obj.getString("title")+"</th></tr><tr>"+ths+"</tr></thead>";
                                        listTable+="</table>";
                                        sb.append(listTable);
                                    }else{
                                        sb.append(obj.getString("content"));
                                    }
                                    break;
                                }
                            }
                            isVal=false;
                            val="";
                        }
                    }else{
                        if(isVal){
                            val+=s;
                        }else
                            sb.append(s);
                    }
                }
                out.println(sb.toString());
            %>     
        </form>
    </body>
</html>
