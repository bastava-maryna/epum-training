package by.epum.training.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.UserDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Role;
import by.epum.training.db.entity.User;

public class MySQLUserDAO implements UserDAO {
	
	private Connection connection;	
	
	public MySQLUserDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Long create(User user) throws DAOException {	
		String sql = "INSERT INTO `users` (`last_name`,`first_name`,`passport`,`role`,`login`, `password`, `email`) VALUES (?, ?, ?,?,?,?,?)";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getLastName());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getPassport());
			statement.setString(4, user.getRole().name());
			statement.setString(5, user.getLogin());
			statement.setString(6, user.getPassword());
			statement.setString(7, user.getEmail());

			statement.executeUpdate();
			Long id = null;

			resultSet = statement.getGeneratedKeys();
			
			if(resultSet.next()) {
				id = resultSet.getLong(1);
			}

			return id;

		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ resultSet.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User read(Long id) throws DAOException {
		String sql="SELECT * FROM `users` WHERE `id_user`="+id;

		try(Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)) {

			User user=null;
			
			if(rs.next()) {
				user=new User();
				user.setId(id);
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setPassport(rs.getString("passport"));
				user.setRole(Role.valueOf(rs.getString("role")));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
			}
			
			return user;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(User user) throws DAOException {
		String sql = "UPDATE `users` SET `last_name`=?,`first_name`=?,`passport`=?,`login` = ?, `password` = ?, `email`=?,`role` = ? WHERE `id_user` = ?";
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getLastName());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getPassport());
			statement.setString(4, user.getLogin());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getEmail());
			statement.setString(7, user.getRole().name());
			statement.setLong(8, user.getId());
			statement.executeUpdate();
		
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
		}

	}

	@Override
	public void delete(Long id) throws DAOException {
		String sql = "DELETE FROM `users` WHERE `id_user` = ?";
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
		}
	}

	@Override
	public List<User> readAll() throws DAOException {
		String sql = "SELECT `id_user`,`last_name`,`first_name`,`passport`,`login`, `password`, `email`,`role` FROM `users`";
		
		try (Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql)){
			
			List<User> users = new ArrayList<>();
			
			while(resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id_user"));
				user.setLastName(resultSet.getString("last_name"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setPassport(resultSet.getString("passport"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setRole(Role.valueOf(resultSet.getString("role")));
				users.add(user);
			}
			
			return users;
		} catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User readByLogin(String login) throws DAOException {
		String sql = "SELECT `id_user`,`last_name`,`first_name`,`passport`, `password`, `email`,`role`FROM `users` WHERE `login` = ?";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			User user = null;
			
			if(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong("id_user"));
				user.setLastName(resultSet.getString("last_name"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setPassport(resultSet.getString("passport"));
				user.setLogin(login);
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setRole(Role.values()[resultSet.getInt("role")]);
			}
			return user;
			
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ resultSet.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User readByLoginAndPassword(String login, String password) throws DAOException {
		String sql = "SELECT `id_user`, `last_name`,`first_name`,`passport`, `email`,`role`  FROM `users` WHERE `login` = ? AND `password` = ?";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, login);
			statement.setString(2, password);
			
			resultSet = statement.executeQuery();
			User user = null;
			
			if(resultSet.next()) {
				user = new User();
				
				user.setId(resultSet.getLong("id_user"));
				user.setLastName(resultSet.getString("last_name"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setPassport(resultSet.getString("passport"));
				user.setLogin(login);
				user.setPassword(password);
				user.setEmail(resultSet.getString("email"));
				user.setRole(Role.valueOf(resultSet.getString("role")));

			}
			return user;
			
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ resultSet.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User findByAll(User user) throws DAOException {
		String sql="SELECT * FROM users \r\n"
				+ "WHERE last_name=? AND first_name=? AND passport=? AND login =? AND password =? AND email=? AND role=?";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getLastName());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getPassport());
			statement.setString(4, user.getLogin());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getEmail());
			statement.setString(7, user.getRole().name());

			resultSet = statement.executeQuery();
			User us = null;

			while(resultSet.next()) {
				us = new User();
				us.setId(resultSet.getLong("id_user"));
				us.setLastName(resultSet.getString("last_name"));
				us.setFirstName(resultSet.getString("first_name"));
				us.setPassport(resultSet.getString("passport"));
				us.setLogin(resultSet.getString("login"));
				us.setPassword(resultSet.getString("password"));
				us.setEmail(resultSet.getString("email"));
				us.setRole(Role.valueOf(resultSet.getString("role")));  
			}
			return us;

		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ resultSet.close(); } catch(Exception e) {}
		}
	}

	@Override
	public User readByPassport(String passport) throws DAOException {
		String sql="SELECT * FROM `users` WHERE `passport`=?";
		
		PreparedStatement statement = null;
		ResultSet rs=null;

		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, passport);
			rs=statement.executeQuery();

			User user=null;
			
			if(rs.next()) {
				user=new User();
				user.setId(rs.getLong("id_user"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setPassport(passport);
				user.setRole(Role.valueOf(rs.getString("role")));
				user.setLogin(rs.getString("login"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
			}
			
			return user;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ rs.close(); } catch(Exception e) {}
		}
	}
}
