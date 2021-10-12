package by.epum.training.db.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epum.training.db.dao.CarriagePriceDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.PriceService;
import by.epum.training.db.service.exception.ServiceException;

public class PriceServiceImpl implements PriceService {
	private CarriagePriceDAO priceDAO;
	
	public void setCarriagePriceDAO(CarriagePriceDAO priceDAO) {
		this.priceDAO = priceDAO;
	}
	

	private Map<CarriageType, BigDecimal> getCarriagePriceCoefficients() throws ServiceException {
		Map<CarriageType, BigDecimal> result=null; 
		
		try {
			result=priceDAO.getPriceCoefficients();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return result;
	}
	

	@Override
	public Map<Integer, List<BigDecimal>> getPrices(List<Train> trains) throws ServiceException {
		
		Map<Integer, List<BigDecimal>> result=new HashMap<Integer, List<BigDecimal>>();   //??concur

		for(Train tr:trains)
			try {
				List<BigDecimal> priceList=new ArrayList<BigDecimal>();
				
				for(Map.Entry<CarriageType, BigDecimal> entry:getTrainPrice(tr).entrySet()) {
					priceList.add(entry.getValue());
				}
				
				result.put(tr.getId(), priceList);
				
			} catch (ArithmeticException e) {
				throw new ServiceException(e.getMessage());
			}
		
		return result;
	}
	
	//here we can use additional price regulation -personal, season, ...
	@Override
	public Map<CarriageType,BigDecimal> getTrainPrice(Train train) throws ServiceException {
		int SCALE=2;
		RoundingMode MODE=RoundingMode.CEILING;
		
		Map<CarriageType,BigDecimal>result=new HashMap<CarriageType,BigDecimal>();
		
		Map<CarriageType, BigDecimal> carriageCoefficients=getCarriagePriceCoefficients();
		
		for(CarriageType carriageType:CarriageType.values()) {
			BigDecimal price=(train.getPrice().multiply(carriageCoefficients.get(carriageType))).setScale(SCALE,MODE);
			result.put(carriageType, price);
		}
		
		return result;
	}	
}
