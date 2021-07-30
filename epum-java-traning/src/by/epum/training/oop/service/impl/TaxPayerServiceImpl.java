package by.epum.training.oop.service.impl;

import java.util.List;

import by.epum.training.oop.dao.DAOProvider;
import by.epum.training.oop.dao.exception.DAOException;
import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.TaxPayerService;
import by.epum.training.oop.service.exception.ServiceException;



public class TaxPayerServiceImpl implements TaxPayerService{
	private final DAOProvider provider=DAOProvider.getInstance();
	//TaxPayerDAO taxPayerDAO=provider.getTaxPayerDAO();  //what is better
	
	@Override
	public TaxPayer getById(Long id) throws ServiceException {
		
		TaxPayer taxPayer=null;
		try {
			taxPayer=provider.getTaxPayerDAO().getById(id);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	
		return taxPayer;
	}

	@Override
	public List<TaxPayer> findByLastName(String lastName) throws ServiceException {
		
		List <TaxPayer> taxPayers=null;
		try {
			taxPayers=provider.getTaxPayerDAO().findByLastName(lastName);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	
		return taxPayers;
	}	
}
