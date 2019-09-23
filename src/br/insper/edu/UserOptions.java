package br.insper.edu;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet("/useroptions")
public class UserOptions extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DAO dao;
		PrintWriter out = response.getWriter();
		try {
			dao = new DAO();
			Cadastro cadastro = new Cadastro();
			cadastro.setUser(request.getParameter("user"));
			cadastro.setPassword(request.getParameter("password"));

			if (dao.verificaUser(cadastro)) {
				String user = request.getParameter("user");
				request.setAttribute("user", user);
				out.println("<html><body>");
				out.println("bem vindo " + cadastro.getUser());
				RequestDispatcher rd = request.getRequestDispatcher("crud.jsp");
				rd.forward(request, response);
				dao.close();
			} else {
				out.println("<html><body>");
				out.println("Você não está cadastrado");
				out.println("<a href=\"signin.jsp\">");
				out.println("Sign In");
				out.println("</a>");
				out.println("</body></html>");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAO dao;
		PrintWriter out = response.getWriter();

		try {
			dao = new DAO();
			Cadastro cadastro = new Cadastro();
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			String check = request.getParameter("password_check");
			
			cadastro.setUser(user);
			cadastro.setPassword(password);

			if (dao.verificaUser(cadastro)) {
				out.println("<html><body>");
				out.println("Usuário já cadastrado. Favor redirecionar para a tela de login, " + cadastro.getUser() + ".");
				out.println("<a href=\"login.jsp\">");
				out.println("Login");
				out.println("</a>");
				out.println("</body></html>");
			} else {
				if (password.contentEquals(check)) {
					dao.adicionaUser(cadastro);
					
					out.println("<html><body>");
					out.println("Cadastrado com sucesso, " + cadastro.getUser() + ".");
					out.println("<a href=\"login.jsp\">");
					out.println("Fazer login");
					out.println("</a>");
					out.println("</body></html>");
					
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);

				} else {
					out.println("<html><body>");
					out.println("Não foi possível cadastrar o usuário, senhas incompatíveis.");
					out.println("<a href=\"signin.jsp\">");
					out.println("Voltar");
					out.println("</a>");
					out.println("</body></html>");
				}
			}

			dao.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
