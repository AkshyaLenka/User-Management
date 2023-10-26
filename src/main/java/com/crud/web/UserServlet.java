package com.crud.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crud.dao.UserDao;
import com.crud.model.Users;


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
       
    public UserServlet() {
        this.userDao = new UserDao();
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

    
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action= request.getServletPath();
	try {
        switch (action) {
            case "/update":
                this.updateUser(request, response);
                return;
            case "/new":
                this.showNewForm(request, response);
                return;
            case "/edit":
                this.showEditForm(request, response);
                return;
            case "/delete":
                this.deleteUser(request, response);
                return;
            case "/insert":
                this.insertUser(request, response);
                return;
        }

        this.listUser(request, response);
    } catch (SQLException var5) {
        throw new ServletException(var5);
    }
}

private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    List<Users> listUser = this.userDao.selectAllUsers();
    request.setAttribute("listUser", listUser);
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
    dispatcher.forward(request, response);
}

private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    dispatcher.forward(request, response);
}

private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    Users existingUser = this.userDao.selectUserById(id);
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    request.setAttribute("user", existingUser);
    dispatcher.forward(request, response);
}

private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");
    Users newUser = new Users(name, email, country);
    this.userDao.insertUser(newUser);
    response.sendRedirect("list");
}

private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");
    Users book = new Users(id, name, email, country);
    this.userDao.updateUser(book);
    response.sendRedirect("list");
}

private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    this.userDao.deleteUser(id);
    response.sendRedirect("list");
}

	
}
