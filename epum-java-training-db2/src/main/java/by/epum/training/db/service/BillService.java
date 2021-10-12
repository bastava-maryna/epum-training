package by.epum.training.db.service;

import java.util.List;

import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.service.exception.ServiceException;

public interface BillService {

    List<Bill> findAll() throws ServiceException;
    List<Short> getReservedPlaces(Long id) throws ServiceException;
   
    void save(Bill bill) throws ServiceException;//create or update
    void updateBillStatus(Long id, Status status) throws ServiceException;
 
}
