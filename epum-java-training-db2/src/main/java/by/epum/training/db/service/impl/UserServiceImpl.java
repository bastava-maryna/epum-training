package by.epum.training.db.service.impl;

import java.util.List;

import by.epum.training.db.dao.UserDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.User;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.UserService;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.exception.UserNotExistException;
import by.epum.training.db.service.exception.UserNotUniqueException;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;
	private Transaction transaction;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
	}

	@Override
	public List<User> findAll() throws ServiceException {
		try {
            return userDAO.readAll();
        } catch(DAOException e) {
            throw new ServiceException(e.getMessage());
        }
	}

	@Override
	public User findById(Long id) throws ServiceException {
		try {
            return userDAO.read(id);
        } catch(DAOException e) {
            throw new ServiceException(e.getMessage());
        }
	}

	@Override
	public User findByLoginAndPassword(String login, String password) throws ServiceException {	
		try {
			return userDAO.readByLoginAndPassword(login, password);
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	@Override
	public void save(User user) throws ServiceException {
		try {
			transaction.start();

			User userFromDB=null;
			if(user.getId() != null) {
				//update part
				userFromDB = userDAO.read(user.getId());

				if(userFromDB != null) {
					System.out.println("in service save user");
					userDAO.update(user);
				} else {
					throw new UserNotExistException(user.getId());
				}

				//create part    
			} else {
				userFromDB=userDAO.readByLoginAndPassword(user.getLogin(), user.getPassword());

				if(userFromDB==null) {
					Long id = userDAO.create(user);
					user.setId(id);
				} else {
					throw new UserNotUniqueException(userFromDB.getId());
				}
			}
			transaction.commit();
		} catch(DAOException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw new ServiceException(e.getMessage());
		} catch(ServiceException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw e;
		}
	}

	@Override
	public Long create(User user) throws ServiceException {
		Long id=null;
		
		try {
			transaction.start();
			User userFromDB=userDAO.readByLoginAndPassword(user.getLogin(), user.getPassword());
			
			if(userFromDB==null) {
				id = userDAO.create(user);
				user.setId(id);
			} else {
				throw new UserNotUniqueException(userFromDB.getId());
			}
			transaction.commit();
			return id;
		} catch(DAOException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw new ServiceException(e.getMessage());
		}catch(ServiceException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw e;
		}
	}

	@Override
	public User findByPassport(String passport)throws ServiceException {
		try {
			return userDAO.readByPassport(passport);
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}

