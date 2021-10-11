package by.epum.training.db.dao;

import java.math.BigDecimal;
import java.util.Map;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.CarriagePricePolicy;

public interface PriceDAO<T> extends DAO<CarriagePricePolicy, Long>{
	Map<T,BigDecimal> getPriceCoefficients() throws DAOException;

}
	

