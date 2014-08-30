package mapreport.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapreport.controller.Endpoints;

@WebServlet("/news")
public class HTTPServletClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/x-json;charset=UTF-8");           
        response.setHeader("Cache-Control", "no-cache");
		
        try {
            response.getWriter().write(Endpoints.news(request));
        } catch (IOException e) {
           throw new IOException("IOException in populateWithJSON", e);
        }    
	}
}