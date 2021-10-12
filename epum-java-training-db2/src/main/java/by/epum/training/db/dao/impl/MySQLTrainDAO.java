package by.epum.training.db.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.TrainDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.ScheduleMode;
import by.epum.training.db.entity.Train;

public class MySQLTrainDAO implements TrainDAO{
	
	private Connection connection;
	
	public MySQLTrainDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Integer create(Train train) throws DAOException {
		String sql="INSERT INTO `trains` (`train_name`,`route`, `departure_time`,`destination_time`,`price`,`schedule_mode`,`departure_date`) VALUES (?,?,?,?,?,?,?)";

		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			st.setString(1, train.getTrainName());
			st.setInt(2, train.getRoute().getId());
			st.setTime(3, Time.valueOf(train.getDepartureTime()));
			st.setTime(4, Time.valueOf(train.getDestinationTime()));
			st.setBigDecimal(5, train.getPrice());
			st.setShort(6, train.getScheduleMode().getId());
			st.setDate(7, Date.valueOf(train.getDepartureDate()));

			st.executeUpdate();
			rs=st.getGeneratedKeys();			
			rs.next();

			return rs.getInt(1);

		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				rs.close();
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public Train read(Integer id) throws DAOException {
		String sql="SELECT * FROM `trains` WHERE `id_train`="+id;

		try(Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)) {
			
			Train train=null;
			
			if(rs.next()) {
				train=new Train();
				
				train.setId(id);
				train.setTrainName(rs.getString("train_name"));
			
				Route route=new Route();
				route.setId(Integer.valueOf(rs.getInt("route")));
				train.setRoute(route);
				
				train.setDepartureTime(rs.getTime("departure_time").toLocalTime());
				train.setDestinationTime(rs.getTime("destination_time").toLocalTime());
				train.setPrice(rs.getBigDecimal("price"));
				
				ScheduleMode scheduleMode=new ScheduleMode();
				scheduleMode.setId(Short.valueOf(rs.getShort("schedule_mode")));
				train.setScheduleMode(scheduleMode);
				
				train.setDepartureDate(rs.getDate("departure_date").toLocalDate());
			}
			return train;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Train train) throws DAOException {
		String sql="UPDATE `trains` SET `train_name` = ?, `route` = ?, `departure_time` = ?,`destination_time` = ?,`price` = ?, `schedule_mode` = ?, `departure_date` = ? WHERE `id_train`=?";
		
		PreparedStatement st=null;
		
		try {
			st=connection.prepareStatement(sql);

			st.setString(1, train.getTrainName());
			st.setInt(2, train.getRoute().getId());
			st.setTime(3, Time.valueOf(train.getDepartureTime()));
			st.setTime(4, Time.valueOf(train.getDestinationTime()));
			st.setBigDecimal(5, train.getPrice());
			st.setShort(6, train.getScheduleMode().getId());
			st.setDate(7, Date.valueOf(train.getDepartureDate()));
			st.setInt(8, train.getId());
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
	public void delete(Integer id) throws DAOException {
		throw new UnsupportedOperationException();

	}

	@Override
	public List<Train> readAll() throws DAOException {
		String sql="SELECT `id_train`,`train_name`,`route`, `departure_time`,`destination_time`,`price`,`schedule_mode`,`departure_date` FROM `trains`";
		
		List <Train> result=new ArrayList<Train>();
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			while(rs.next()) {
				Train train=new Train();

				train.setId(rs.getInt("id_train"));
				train.setTrainName(rs.getString("train_name"));
				train.setRoute(new Route());
				train.getRoute().setId(rs.getInt("route"));
				train.setDepartureTime(rs.getTime("departure_time").toLocalTime());
				train.setDestinationTime(rs.getTime("destination_time").toLocalTime());
				//only id without names
				train.setPrice(rs.getBigDecimal("price"));
				train.setScheduleMode(new ScheduleMode());
				train.getScheduleMode().setId(rs.getShort("schedule_mode"));
				train.setDepartureDate(rs.getDate("departure_date").toLocalDate());	

				result.add(train);
			}
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Train findByAll(Train train) throws DAOException {
		String sql="SELECT `id_train`,`train_name`,`route`, `departure_time`,`destination_time`,`price`,`schedule_mode`,`departure_date` FROM `trains` WHERE `train_name` = ? AND `route` = ? AND `departure_time` = ? AND `destination_time` = ? AND `price` = ? AND `schedule_mode` = ? AND `departure_date` = ?";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Train result=null;
		
		try {
			st=connection.prepareStatement(sql);

			st.setString(1, train.getTrainName());
			st.setInt(2, train.getRoute().getId());
			st.setTime(3, Time.valueOf(train.getDepartureTime()));
			st.setTime(4, Time.valueOf(train.getDestinationTime()));
			st.setBigDecimal(5, train.getPrice());
			st.setShort(6, train.getScheduleMode().getId());
			st.setDate(7, Date.valueOf( train.getDepartureDate()));

			rs = st.executeQuery();

			if(rs.next()) {
				result=new Train();

				result.setId(rs.getInt("id_train"));
				result.setTrainName(rs.getString("train_name"));
				result.setRoute(new Route());
				result.getRoute().setId(rs.getInt("route"));

				result.setDepartureTime(rs.getTime("departure_time").toLocalTime());
				result.setDestinationTime(rs.getTime("destination_time").toLocalTime());
				//only id without names
				result.setPrice(rs.getBigDecimal("price"));
				result.setScheduleMode(new ScheduleMode());
				result.getScheduleMode().setId(rs.getShort("schedule_mode"));
				result.setDepartureDate(rs.getDate("departure_date").toLocalDate());	
			}
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				rs.close();
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public List<Train> findByStationsAndDate(Integer departureId, Integer destinationId, LocalDate departureDate)
			throws DAOException {
		String sql="select id_train,train_name, r.id_route,  departure_time, destination_time, price, schedule_mode, departure_date\r\n"
				+ "from trains\r\n"
				+ "join \r\n"
				+ "(select id_route\r\n"
				+ "from routes\r\n"
				+ "join stations as a on a.id_st=departure\r\n"
				+ "join stations  as b on b.id_st=destination  where a.id_st=? and b.id_st=? )as r\r\n"
				+ "on trains.route=r.id_route\r\n"
				+ "where departure_date= ? \r\n"
				+ "order by departure_time";

		PreparedStatement statement = null;
		ResultSet resultSet = null;
	
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, departureId);
			statement.setInt(2, destinationId);
			statement.setDate(3,Date.valueOf(departureDate));
			
			resultSet = statement.executeQuery();

			List <Train> result=new ArrayList<Train>();
			
			while(resultSet.next()) {
				Train train = new Train();

				train.setId(resultSet.getInt("id_train"));
				train.setTrainName(resultSet.getString("train_name"));
				
				Route route=new Route();
				route.setId(resultSet.getInt("r.id_route"));
				train.setRoute(route);
				
				train.setDepartureTime(resultSet.getTime("departure_time").toLocalTime());
				train.setDestinationTime(resultSet.getTime("destination_time").toLocalTime());
				train.setPrice(resultSet.getBigDecimal("price"));
				
				train.setScheduleMode(new ScheduleMode());
				train.getScheduleMode().setId(resultSet.getShort("schedule_mode"));
				train.setDepartureDate(departureDate);
				
				result.add(train);
			}
			return result;
			
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			try{ statement.close(); } catch(Exception e) {}
			try{ resultSet.close(); } catch(Exception e) {}
		}
	}
}
