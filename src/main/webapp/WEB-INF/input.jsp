<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form>
            <div>${formInfo.template}</div>
            <div>
                <textarea name="content"></textarea>
            </div>
            <div>
                <input type="submit" value="提交" />
                <input type="reset" value="重置" />
            </div>
        </form>
    </body>
</html>
