package by.epum.training.db.service;

import by.epum.training.db.service.exception.TransactionException;

public interface Transaction {
	void start() throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;
}
