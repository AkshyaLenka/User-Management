package com.crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crud.model.Users;

public class UserDao {
private String jdbcURL="jdbc:mysql://localhost:3306/crud";
private String username="root";
private String password="Luckyboy@1792";

private static final String INSERT_USERS_SQL = "insert into users (name,email,country) values"+"(?,?,?);";
private static final String SELECT_USER_BY_ID ="select * from users where id=?";
//private static final String SELECT_ALL_USERS = "select * from users";
private static final String DELETE_USERS = "delete from users where id=?";
//private static final String	UPDATE_USERS_SQL  =	"update users set name=?, email=?, country=? where id=?;";

protected Connection getConnection() throws SQLException{
	Connection connection = null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection= DriverManager.getConnection(jdbcURL, username, password);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return connection;
}


//Create User
public boolean insertUser(Users user) throws SQLException {
	boolean rowUpdated;
	try(Connection con = getConnection();
	PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL);){
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getCountry());
		ps.setInt(4,user.getId());
		rowUpdated=ps.executeUpdate()>0;
	}
	return rowUpdated;
}


//Select user by ID
public Users selectUserById(int id) throws SQLException {
	Users user = null;
	try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID);){
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String name= rs.getString("name");
				String email= rs.getString("email");
				String country= rs.getString("country");
				user= new Users(id,name,email,country);
			}
	}
	return user;
}

//Select All users
public List<Users> selectAllUsers() throws SQLException {
	List<Users> users= new ArrayList<>();
	try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID);){
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int id= rs.getInt("id");
				String name= rs.getString("name");
				String email= rs.getString("email");
				String country= rs.getString("country");
				users.add(new Users(id,name,email,country));
			}
	}
	return users;
}

//Delete User
public boolean deleteUser(int id) throws SQLException {
	boolean rowDeleted;
	try(Connection con = getConnection();
	PreparedStatement ps = con.prepareStatement(DELETE_USERS);){
		ps.setInt(1,id);
		rowDeleted=ps.executeUpdate()>0;
	}
	return rowDeleted;
}

// Update User
public boolean updateUser(Users user) throws SQLException {
	boolean rowUpdated;
	try(Connection con = getConnection();
	PreparedStatement ps = con.prepareStatement(INSERT_USERS_SQL);){
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getCountry());
		ps.setInt(4,user.getId());
		rowUpdated=ps.executeUpdate()>0;
	}
	return rowUpdated;
}

}
