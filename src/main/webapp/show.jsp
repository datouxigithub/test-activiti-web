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
                                if(val.equals(obj.getString("name"))){
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
                                        if(im.isMulti())
                                            element+="<select"+attrs+"</select>";
                                        else
                                            element+="<input"+attrs+" value=\""+im.macroValues(request,response).get(0).getValue()+"\" disabled />";
                                        sb.append(element);
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
