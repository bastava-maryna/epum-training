package by.epum.training.db.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Train;
import by.epum.training.db.service.exception.ServiceException;

public interface PriceService {

	Map<Integer, List<BigDecimal>> getPrices(List<Train> trains) throws ServiceException;
	Map<CarriageType, BigDecimal> getTrainPrice(Train train) throws ServiceException;	

}
