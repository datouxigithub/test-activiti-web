<%@page import="org.activiti.engine.impl.util.json.JSONObject"%>
<%@page import="java.io.File"%>
<%@page import="java.util.UUID"%>
<%@page import="com.google.zxing.BarcodeFormat"%>
<%@page import="com.google.zxing.client.j2se.MatrixToImageWriter"%>
<%@page import="com.google.zxing.MultiFormatWriter"%>
<%@page import="com.google.zxing.common.BitMatrix"%>
<%@page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel"%>
<%@page import="com.google.zxing.EncodeHintType"%>
<%@page import="java.util.HashMap"%>
<%
    //response.setContentType("image/gif");
    
    int width=Integer.parseInt(request.getParameter("width")),height=Integer.parseInt(request.getParameter("height"));
    String content=request.getParameter("content");
    String format="gif";
    
    String path=request.getRealPath("/qrcode");
    String fileName=UUID.randomUUID().toString()+"."+format;
    
    File file=new File(path+"/"+fileName);
    
    JSONObject jsonObj=new JSONObject();
    boolean result=false;
    try{
        result=file.createNewFile();
    }catch(Exception e){}
    
    if(result){
        HashMap hints=new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix matrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        //MatrixToImageWriter.writeToStream(matrix, format, response.getOutputStream());
        MatrixToImageWriter.writeToPath(matrix, format, file.toPath());
        jsonObj.put("result", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/qrcode/"+fileName);
    }else{
        jsonObj.put("result", "fail");
    }
    
    response.getWriter().write(jsonObj.toString());
%>
