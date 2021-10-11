package by.epum.training.db.dao;

import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.User;

public interface UserDAO extends DAO<User, Long> {
	 	List<User> readAll() throws DAOException;
	    User readByLogin(String login) throws DAOException;
	    User readByLoginAndPassword(String login, String password) throws DAOException;
	    User readByPassport(String passport) throws DAOException;
	    User findByAll(User user)throws DAOException;
}
