<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="${pageContext.servletContext.contextPath}/test/submit">
            <div>${formInfo.template}</div>
            <div>
                <textarea name="comment"></textarea>
            </div>
            <div>
                <input type="submit" value="提交" />
                <input type="reset" value="重置" />
            </div>
        </form>
    </body>
</html>
