/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.test.activiti.web.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author datouxi
 */
@WebServlet(name = "TestFormDesignServlet", urlPatterns = {"/tfd"})
public class TestFormDesignServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
//        Map<String,Object> result=new FormDesign().parseForm(request.getParameter("design_content"));
//        response.getWriter().write(result.toString());
//        response.getWriter().write(result.get("template").toString());
//        response.getWriter().write(request.getParameter("design_content"));
//        request.setAttribute("parseForm", new JSONObject(result).toString());
//        RequestDispatcher dispatcher=request.getRequestDispatcher("show.jsp");
//        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
}
