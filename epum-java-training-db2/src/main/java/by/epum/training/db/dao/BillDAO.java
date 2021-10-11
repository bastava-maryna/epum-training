package by.epum.training.db.dao;

import java.math.BigDecimal;
import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;

public interface BillDAO extends DAO<Bill, Long> {
   
    List<Short>findReservedByCarriage(Long carriageId) throws DAOException;
    void updateBillStatus(Long id, Status status) throws DAOException;
    
    List<Bill> readAll() throws DAOException;
    List<Long>findByAll(Long userId,Long carriageId,Short place,BigDecimal cost, Status status) throws DAOException;
    List<Long>findByUserCarriageNotInvalid(Long userId,Long carriageId) throws DAOException;
	List<Bill> findByPassengerId(Long userId)throws DAOException;
}
