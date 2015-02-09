package mapreport.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapreport.controller.Endpoints;
import mapreport.util.Log;

@WebServlet("/news/*")
public class HTTPServletClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Log.info("HTTPServletClass doGet");
    	
		response.setContentType("text/x-json;charset=UTF-8");           
        response.setHeader("Cache-Control", "no-cache");
        //response.addHeader("Access-Control-Allow-Origin", "*");
		
        try {
            response.getWriter().write(Endpoints.news(request));
        } catch (Exception e) {
        	Log.info("HTTPServletClass IOException in populateWithJSON" + e);
        }    
	}
}