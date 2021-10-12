package by.epum.training.db.service.impl;

import java.util.List;

import by.epum.training.db.dao.RouteDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;
import by.epum.training.db.service.RouteService;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.RouteNotExistException;
import by.epum.training.db.service.exception.RouteNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;

public class RouteServiceImpl implements RouteService {
	
	private RouteDAO routeDAO;
	private Transaction transaction;
	
	public void setRouteDAO(RouteDAO routeDAO) {
		this.routeDAO = routeDAO;
	}

	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
}
	
	@Override
	public Route findById(Integer id) throws ServiceException {
		Route result=null; 
		
		try {
			result=routeDAO.read(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<Route> findAll() throws ServiceException {	
		List<Route> result=null;
		
		try {
			result=routeDAO.readAll();			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}

	@Override
	public Route findByStations(Station departure, Station destination) throws ServiceException {
		Route result=null; 
		
		try {
			result=new Route();	
			List<Integer>routes=routeDAO.findByParametrs(departure.getId(),destination.getId());
			if(!routes.isEmpty()) {
				result.setId(routes.get(0));
				return result;
			}else {
				throw new RouteNotExistException();
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	@Override
	public void save(Route route) throws ServiceException {
		try {
			transaction.start();

			if(route.getId() != null) {
				//update part
				Route routeFromDB = routeDAO.read(route.getId());
				
				if(routeFromDB != null) {
					routeDAO.update(route);
				} else {
					throw new RouteNotExistException();
				}

				//create part    
			} else {
				if(routeDAO.findByParametrs(route.getDeparture().getId(),route.getDestination().getId()).isEmpty()) {
					Integer id = routeDAO.create(route);
					route.setId(id);
				} else {
					throw new RouteNotUniqueException(route.getId());
				}
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
