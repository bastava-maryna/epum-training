package by.epum.training.db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epum.training.db.controller.util.Utils;
import by.epum.training.db.dao.CarriageDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.service.CarriageService;
import by.epum.training.db.service.ServiceProvider;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.CarriageNotExistException;
import by.epum.training.db.service.exception.CarriageNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;

public class CarriageServiceImpl implements CarriageService {
	
	private CarriageDAO carriageDAO;
	private Transaction transaction;
	
	public void setCarriageDAO(CarriageDAO carriageDAO) {
		this.carriageDAO = carriageDAO;
	}
	
	public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
	}


	@Override
	public Carriage findById(Long id) throws ServiceException {
		Carriage result=null; 
		
		try {
			result=carriageDAO.read(id);
			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<Carriage> findAll() throws ServiceException {
		List<Carriage> result=null;
		
		try {
			result=carriageDAO.readAll();			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}

	@Override
	public List<Carriage> findAllByTrain(Integer trainId) throws ServiceException {
		try {
			return carriageDAO.readAllByTrain(trainId );
		} catch(DAOException e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public void save(Carriage carriage) throws ServiceException {
		try {
			transaction.start();

			if(carriage.getId() != null) {
				//update part
				Carriage carriageFromDB = carriageDAO.read(carriage.getId());
				
				if(carriageFromDB != null) {
					carriageDAO.update(carriage);
				} else {
					throw new CarriageNotExistException(carriage.getId());
				}

				//create part    
			} else {
				if(carriageDAO.findByAll(carriage.getTrain().getId(),carriage.getCarriageNumber(),carriage.getCarriageType().name()).isEmpty() ){
					Long id = carriageDAO.create(carriage);
					carriage.setId(id);
				} else {
					throw new CarriageNotUniqueException(carriage.getId());
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
	public List<Carriage> findAllByTrainAndCarriageType(Integer trainId, CarriageType carriageType) throws ServiceException{
		try {
			return carriageDAO.readAllByTrainAndType(trainId , carriageType);
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

		
	@Override
	public List<Carriage> findEmpty(Integer trainId) throws ServiceException {
		try {
			List<Carriage> empty=carriageDAO.findEmpty(trainId);
			return empty ;
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	

	@Override
	public List<Short> findEmptyNumbers(Integer trainId) throws ServiceException {
		List<Carriage> empty=findEmpty(trainId);
		List<Short> numbers=new ArrayList<Short>(); 
		
		for(Carriage car:empty) {
			numbers.add(car.getCarriageNumber());
		}
		 return numbers ;
	}

	@Override
	public List<Short> findAvailableToAddNumbers(Integer trainId) throws ServiceException {
		
		List<Carriage> carriagesInTrain;
		try {
			carriagesInTrain = carriageDAO.readAllByTrain(trainId );
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		List<Short> availableCarriageNumbers=Utils.getCarriageRange();
				
		//those number which can add
		for(Carriage carr:carriagesInTrain) {
			availableCarriageNumbers.remove(carr.getCarriageNumber());
		}
		
		return availableCarriageNumbers;
	}

	@Override
	public void delete(Long id) throws ServiceException {
		try {
			carriageDAO.delete(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	@Override
	public Map<Carriage, List<Short>> getAvailablePlacesInTrain(Integer trainId) throws ServiceException {

		Map<Carriage,List<Short>> availablePlacesInTrain=new HashMap<Carriage,List<Short>>();//concur

		List<Carriage> carriagesInTrain=findAllByTrain(trainId);

		if(carriagesInTrain!=null && !carriagesInTrain.isEmpty()){
			try(ServiceProvider sp=new ServiceProvider()){
				transaction.start();
				
				for(Carriage carr:carriagesInTrain) {
					List<Short>	placeRange=Utils.getPlaceRange(carr.getCarriageType());
					List<Short>reservedPlaces = sp.getBillService().getReservedPlaces(carr.getId());

					if(reservedPlaces!=null && !reservedPlaces.isEmpty()) {
						placeRange.removeAll(reservedPlaces);

						if(placeRange.size()>0) {
							availablePlacesInTrain.put(carr, placeRange);
						}

					}else {
						availablePlacesInTrain.put(carr, placeRange);
					}
				}
				transaction.commit();
				
			} catch (ServiceException e) {
				try { 
					transaction.rollback(); 
				} catch(ServiceException e1) {}
				throw new ServiceException(e.getMessage());
			}
		}	
		return availablePlacesInTrain;
	}
}
