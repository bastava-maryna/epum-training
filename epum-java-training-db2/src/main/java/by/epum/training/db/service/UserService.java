package by.epum.training.db.service;

import java.util.List;

import by.epum.training.db.entity.User;
import by.epum.training.db.service.exception.ServiceException;




public interface UserService {
	User findById(Long id) throws ServiceException;	
	User findByLoginAndPassword(String login, String password)throws ServiceException;
	User findByPassport(String passport)throws ServiceException;
	List<User> findAll() throws ServiceException;
    Long create(User user)throws ServiceException;
    void save(User user) throws ServiceException;

}





