package by.epum.training.db.service;

import java.util.List;

import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.service.exception.ServiceException;

public interface ScheduleModeService {
	 List<ScheduleMode> findAll() throws ServiceException;
	    ScheduleMode findById(Short id) throws ServiceException;
	   
	    void delete(Short id) throws ServiceException;
	    void save(ScheduleMode schedueMode) throws ServiceException;//create or update
}
