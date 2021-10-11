package by.epum.training.db.dao;

import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;

public interface CarriageDAO extends DAO<Carriage, Long> {
	List<Carriage> readAllByTrain(Integer trainId) throws DAOException;
    List<Carriage> readAll() throws DAOException;
    List<Long>findByAll(Integer trainId,Short carriageNumber, String carriageType) throws DAOException;
    List<Carriage>readAllByTrainAndType(Integer trainId , CarriageType carriageType) throws DAOException;
	List<Carriage> findEmpty(Integer trainId)throws DAOException;
}
