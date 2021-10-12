package by.epum.training.db.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.ScheduleModeDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.ScheduleMode;


public class MySQLScheduleModeDAO implements ScheduleModeDAO {
	
	private Connection connection;

	public MySQLScheduleModeDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Short create(ScheduleMode scheduleMode) throws DAOException {
		String sql="INSERT INTO `schedule_modes` (`description`) VALUES (?)";

		PreparedStatement st=null;
		ResultSet rs=null;

		try {	
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			st.setString(1, scheduleMode.getDescription());
			st.executeUpdate();
			rs=st.getGeneratedKeys();
			rs.next();

			return rs.getShort(1);

		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try{ st.close(); } catch(Exception e) {}
			try{ rs.close(); } catch(Exception e) {}
		}
	}

	@Override
	public ScheduleMode read(Short id) throws DAOException {
		String sql="SELECT `id_sch`, `description` FROM `schedule_modes` WHERE `id_sch`="+id;
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			ScheduleMode scheduleMode=null;
			
			if(rs.next()) {
				scheduleMode=new ScheduleMode();
				scheduleMode.setId(id);
				scheduleMode.setDescription(rs.getString("description"));
			}
			return scheduleMode;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(ScheduleMode scheduleMode) throws DAOException {
		String sql="UPDATE `schedule_modes` SET `description`=? WHERE `id_sch`=?";
		
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);

			st.setString(1, scheduleMode.getDescription());
			st.setShort(2, scheduleMode.getId());
			
			st.executeUpdate();
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public void delete(Short id) throws DAOException {
		String sql="DELETE FROM `schedule_modes` WHERE `id_sch`=?";
		
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);
		
			st.setShort(1, id);
			st.executeUpdate();
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}

	}

	@Override
	public List<ScheduleMode> readAll() throws DAOException {
		String sql="SELECT `id_sch`, `description` FROM `schedule_modes`";
		
		List <ScheduleMode> result=new ArrayList<ScheduleMode>();
		
		try(Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)) {
			
			while(rs.next()) {
				ScheduleMode scheduleMode=new ScheduleMode();
				scheduleMode.setId(rs.getShort("id_sch"));
				scheduleMode.setDescription(rs.getString("description"));
				
				result.add(scheduleMode);
			}
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
}
