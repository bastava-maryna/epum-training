package by.epum.training.db.service.impl;

import java.time.LocalDate;
import java.util.List;

import by.epum.training.db.dao.TrainDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.entity.Station;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.ScheduleModeService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.StationService;
import by.epum.training.db.service.TrainService;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.ServiceException;
import by.epum.training.db.service.exception.TrainNotExistException;
import by.epum.training.db.service.exception.TrainNotUniqueException;

public class TrainServiceImpl implements TrainService {
	
	private TrainDAO trainDAO;
	private Transaction transaction;
	
	public void setTrainDAO(TrainDAO trainDAO) {
		this.trainDAO = trainDAO;
	}
	
	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
	}

	@Override
	public List<Train> findAll() throws ServiceException {
		List<Train> result=null;
		
		try (ServiceProvider sp=new ServiceProvider()){
			transaction.start();
			
			result=trainDAO.readAll();
			
			RouteService routeService = sp.getRouteService();
			StationService stationService = sp.getStationService();
			ScheduleModeService scheduleModeService=sp.getScheduleModeService();
			
			for(Train train:result) {
				Route route=new Route();
				route=routeService.findById(train.getRoute().getId());

				Station departure= new Station();
				departure=stationService.findById(route.getDeparture().getId());//or ??station with id but without name
				route.setDeparture(departure);
				
				Station destination=new Station();
				destination=stationService.findById(route.getDestination().getId());//or ??station with id but without name
				route.setDestination(destination);
				
				train.setRoute(route);
				
				ScheduleMode scheduleMode=new ScheduleMode();
				scheduleMode=scheduleModeService.findById(train.getScheduleMode().getId());
				
				train.setScheduleMode(scheduleMode);
			}
			transaction.commit();
		} catch (DAOException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}


	@Override
	public List<Train> findByStationsAndDate(Station departure, Station destination, LocalDate departureDate) throws ServiceException {	
		List<Train> result=null;
		
		try {
			result=trainDAO.findByStationsAndDate(departure.getId(), destination.getId(), departureDate);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}


	@Override
	public void save(Train train) throws ServiceException {
		try {
			transaction.start();

			if(train.getId() != null) {
				//update part
				Train trainFromDB = trainDAO.read(train.getId());
				
				if(trainFromDB != null) {
					trainDAO.update(train);
				} else {
					throw new TrainNotExistException(train.getId());
				}

				//create part    
			} else {
				Train duplicate=getDuplicate(train);
				
				if(duplicate==null) {
					Integer id = trainDAO.create(train);
					train.setId(id);
				} else {
					throw new TrainNotUniqueException(duplicate.getId());
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
	public Train getDuplicate(Train train) throws ServiceException {
		Train result=null;
		
		try {
			result=trainDAO.findByAll(train);
			return result;
			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}		
	}

	@Override
	public Train findById(Integer id) throws ServiceException {
		Train result=null; 
		
		try {
			result=trainDAO.read(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return result;
	}

}
