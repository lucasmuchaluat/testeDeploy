package br.insper.edu;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/adiciona")
public class Adiciona extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); 
		
		String user = request.getParameter("user");
		request.setAttribute("user", user);
		
		RequestDispatcher rd = request.getRequestDispatcher("add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); 
		DAO dao;
		
		try {
			dao = new DAO();
			Tarefa tarefa = new Tarefa();
			
			tarefa.setUser(request.getParameter("user"));
			tarefa.setType(request.getParameter("type"));
			tarefa.setTask(request.getParameter("task"));
			
			String date = request.getParameter("date");
			Date data = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Calendar quando = Calendar.getInstance();
			quando.setTime(data);
			tarefa.setDate(quando);
			
			dao.adicionaTarefa(tarefa);
			
			String user = request.getParameter("user");
			request.setAttribute("user", user);

			RequestDispatcher rd = request.getRequestDispatcher("crud.jsp");
			rd.forward(request, response);

			dao.close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			RequestDispatcher rd = request.getRequestDispatcher("crud.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}


	}
	
}