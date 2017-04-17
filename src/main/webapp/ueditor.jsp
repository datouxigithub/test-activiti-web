<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ueditor demo</title>
    </head>
    <body>
        <script id="myFormDesign" type="text/plain" style="width: 100%">
            这里是Ueditor Formdesign 内容
        </script>
        
        <script type="text/javascript" src="js/ueditor/ueditor.config.js"></script>
        <script type="text/javascript" src="js/ueditor/ueditor.all.js"></script>
        <script type="text/javascript" src="js/ueditor/lang/zh-cn/zh-cn.js"></script>
        <script type="text/javascript" src="js/ueditor/formdesign/leipi.formdesign.v4.js"></script>
        
        <script type="text/javascript">
            var myEditor=UE.getEditor('myFormDesign',{
                toolleipi:true,//是否显示，设计器的 toolbars
                textarea: 'design_content'
            });
        </script>
    </body>
</html>
