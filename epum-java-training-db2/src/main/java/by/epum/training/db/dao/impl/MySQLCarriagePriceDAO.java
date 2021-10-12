package by.epum.training.db.dao.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import by.epum.training.db.dao.CarriagePriceDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.CarriagePricePolicy;
import by.epum.training.db.entity.CarriageType;

public class MySQLCarriagePriceDAO implements CarriagePriceDAO {

	@Override
	public Map<CarriageType, BigDecimal> getPriceCoefficients() throws DAOException {
		Map<CarriageType, BigDecimal> carriagePriceCoefficients=new ConcurrentHashMap<CarriageType, BigDecimal> ();
		carriagePriceCoefficients.put(CarriageType.BERTH,new BigDecimal( 1.3) );
		carriagePriceCoefficients.put(CarriageType.COMPARTMENT,new BigDecimal(2.0) );
		carriagePriceCoefficients.put(CarriageType.SITTING,new BigDecimal(1.0) );
		carriagePriceCoefficients.put(CarriageType.SLEEPING,new BigDecimal(2.5) );
		
		return carriagePriceCoefficients;
	}


	@Override
	public Long create(CarriagePricePolicy entity) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public CarriagePricePolicy read(Long id) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(CarriagePricePolicy entity) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Long id) throws DAOException {
		throw new UnsupportedOperationException();	
	}
	
}

