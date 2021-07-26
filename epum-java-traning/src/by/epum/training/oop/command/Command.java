package by.epum.training.oop.command;

import by.epum.training.oop.service.exception.ServiceException;

public interface Command {
	String execute(String param) throws ServiceException;
}
