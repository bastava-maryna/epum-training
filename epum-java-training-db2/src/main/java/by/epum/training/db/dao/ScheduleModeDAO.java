package by.epum.training.db.dao;

import java.util.List;

import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.ScheduleMode;

public interface ScheduleModeDAO extends DAO<ScheduleMode,Short> {
	List<ScheduleMode> readAll() throws DAOException;
	
}
