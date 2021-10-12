package by.epum.training.db.service.impl;

import java.util.List;

import by.epum.training.db.dao.BillDAO;
import by.epum.training.db.dao.exception.DAOBillDublicateEntryException;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.service.BillService;
import by.epum.training.db.service.Transaction;
import by.epum.training.db.service.exception.BillDublicateEntryException;
import by.epum.training.db.service.exception.BillNotExistException;
import by.epum.training.db.service.exception.BillNotUniqueException;
import by.epum.training.db.service.exception.ServiceException;

public class BillServiceImpl implements BillService {

	private BillDAO billDAO;
	private Transaction transaction;

	public void setBillDAO(BillDAO billDAO) {
		this.billDAO = billDAO;
	}	
	
	public void setTransaction(Transaction transaction) {
	        this.transaction = transaction;
	}

	@Override
	public void updateBillStatus(Long id, Status status) throws ServiceException {
		try {
			billDAO.updateBillStatus(id, status);
			
		} catch(DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	@Override
	public List<Bill> findAll() throws ServiceException {
		List<Bill> result=null;
		
		try {
			result=billDAO.readAll();	
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

		return result;
	}


	@Override
	public void save(Bill bill) throws ServiceException {
		try {
			transaction.start();

			if(bill.getId() != null) {
				//update part
				Bill billFromDB = billDAO.read(bill.getId());

				if(billFromDB != null) {
					billDAO.update(bill);
				} else {
					throw new BillNotExistException(bill.getId());
				}

				//create part    
			} else {
				if(billDAO.findByUserCarriageNotInvalid(bill.getUser().getId(),bill.getCarriage().getId()).isEmpty() ){
					Long id = billDAO.create(bill);
					bill.setId(id);
				} else {
					throw new BillNotUniqueException(bill.getId());
				}
			}
			transaction.commit();
			
		} catch(DAOBillDublicateEntryException e) {
			try { 
				transaction.rollback(); 
			} catch(ServiceException e1) {}
			throw new BillDublicateEntryException(e.getMessage());    
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
	public List<Short> getReservedPlaces(Long carriageId) throws ServiceException {
		List<Short> result=null;
		
		try {
			result=billDAO.findReservedByCarriage(carriageId);			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

		return result;
	}

}
