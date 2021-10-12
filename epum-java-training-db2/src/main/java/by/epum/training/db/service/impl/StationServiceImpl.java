package by.epum.training.db.service.impl;

import java.util.List;

import by.epum.training.db.dao.StationDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.exception.StationNameNotUniqueException;
import by.epum.training.db.service.exception.StationNotExistException;

public class StationServiceImpl implements StationService {

	private StationDAO stationDAO;
	private Transaction transaction;

	public void setStationDAO(StationDAO stationDAO) {
		this.stationDAO = stationDAO;
	}

	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
	}
	
	@Override
	public List<Station> findAll() throws ServiceException {
		List<Station> result=null;
	
		try {
			result=stationDAO.readAll();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

		return result;
	}

	public Station findById(Integer id) throws ServiceException {
		Station result=null; 
		
		try {
			result=stationDAO.read(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return result;
	}

	public Station findByName(String stationName) throws ServiceException {
		Station result=null; 
		try {
			result=stationDAO.readByName(stationName);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

		return result;
	}

	public void delete(Integer id) throws ServiceException {
		try {
			stationDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void save(Station station) throws ServiceException {
		try {
			transaction.start();

			if(station.getId() != null) {
				//update part
				Station stationFromDB = stationDAO.read(station.getId());
				
				if(stationFromDB != null) {
					//if equal- dont do anything
					if( !stationFromDB.equals(station)) {
						stationDAO.update(station);
					}
				} else {
					throw new StationNotExistException(station.getId());
				}

				//create part    
			} else {
				if(stationDAO.readByName(station.getStationName()) == null) {
					Integer id = stationDAO.create(station);
					station.setId(id);
				} else {
					throw new StationNameNotUniqueException(station.getStationName());
				}
			}
			transaction.commit();
		} catch(DAOException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw new ServiceException(e.getMessage());
		} catch(ServiceException e) {
			try { transaction.rollback(); 
		} catch(ServiceException e1) {}
			throw e;
		}
	}
}
