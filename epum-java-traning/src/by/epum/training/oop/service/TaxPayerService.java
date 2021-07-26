package by.epum.training.oop.service;

import java.util.List;

import by.epum.training.oop.entity.TaxPayer;
import by.epum.training.oop.service.exception.ServiceException;


public interface TaxPayerService {
	TaxPayer getById(Long id) throws ServiceException;
	List<TaxPayer> findByLastName(String lastName) throws ServiceException;
}
