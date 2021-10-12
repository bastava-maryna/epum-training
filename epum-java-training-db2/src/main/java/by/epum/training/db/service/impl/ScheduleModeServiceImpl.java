package by.epum.training.db.service.impl;

import java.util.List;

import by.epum.training.db.dao.ScheduleModeDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.service.ScheduleModeService;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.ScheduleModeNotExistException;
import by.epum.training.db.service.exception.ServiceException;

public class ScheduleModeServiceImpl implements ScheduleModeService {
	private ScheduleModeDAO scheduleModeDAO;
	private Transaction transaction;
	
	public void setScheduleModeDAO(ScheduleModeDAO scheduleModeDAO) {
		this.scheduleModeDAO = scheduleModeDAO;
	}
	
	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
}

	@Override
	public List<ScheduleMode> findAll() throws ServiceException {	
		List<ScheduleMode> result=null;
		
		try {
			result=scheduleModeDAO.readAll();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}

	@Override
	public ScheduleMode findById(Short id) throws ServiceException {
		ScheduleMode result=null; 
		
		try {
			result=scheduleModeDAO.read(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return result;
	}

	@Override
	public void delete(Short id) throws ServiceException {
		try {
			scheduleModeDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void save(ScheduleMode scheduleMode) throws ServiceException {
		try {
			transaction.start();

			if(scheduleMode.getId() != null) {
				//update part
				ScheduleMode scheduleModeFromDB = scheduleModeDAO.read(scheduleMode.getId());

				if(scheduleModeFromDB != null) {
					scheduleModeDAO.update(scheduleMode);
				} else {
					throw new ScheduleModeNotExistException(scheduleMode.getId());
				}

				//create part    
			} else {
				Short id = scheduleModeDAO.create(scheduleMode);
				scheduleMode.setId(id);
			}
			transaction.commit();
		} catch(DAOException e) {
			try { 
				transaction.rollback(); 
		} catch(ServiceException e1) {}
			throw new ServiceException(e);
		} catch(ServiceException e) {
			try { 
				transaction.rollback(); 
		} catch(ServiceException e1) {}
			throw e;
		}
	}
}
