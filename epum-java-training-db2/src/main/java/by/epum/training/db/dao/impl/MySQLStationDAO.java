package by.epum.training.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.StationDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Station;

public class MySQLStationDAO implements StationDAO{
	private Connection connection;

	public MySQLStationDAO(Connection connection) {
		this.connection = connection;
	}

	public Integer create(Station station) throws DAOException {
		String sql="INSERT INTO `stations` (`station_name`) VALUES (?)";
		
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			st.setString(1, station.getStationName());
			st.executeUpdate();
			rs=st.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
			
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}finally {
			try {
				rs.close();
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	public Station read(Integer id) throws DAOException {
		String sql="SELECT `id_st`, `station_name` FROM `stations` WHERE `id_st`="+id;
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			Station station=null;
			if(rs.next()) {
				station=buildStationFromResultSet(rs);
			}
			
			return station;
			
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Station readByName(String stationName) throws DAOException {
		String sql="SELECT `id_st`, `station_name` FROM `stations` WHERE `station_name`=?";
		
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql);
			st.setString(1, stationName);
			rs=st.executeQuery();
			
			Station station=null;
			if(rs.next()) {
				station=buildStationFromResultSet(rs);
			}

			return station;
			
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}finally {
			try {
				rs.close();
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	public void update(Station station) throws DAOException {
		String sql="UPDATE `stations` SET `station_name`=? WHERE `id_st`=?";
		
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);

			st.setString(1, station.getStationName());
			st.setInt(2, station.getId());
			st.executeUpdate();
		
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	public void delete(Integer id) throws DAOException {
		String sql="DELETE FROM `stations` WHERE `id_st`=?";
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);
			
			st.setInt(1, id);
			st.executeUpdate();
		
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	public List<Station> readAll() throws DAOException {
		String sql="SELECT `id_st`, `station_name` FROM `stations`";
		
		List <Station> result=new ArrayList<Station>();
		
		try(Statement st=connection.createStatement(); ResultSet rs=st.executeQuery(sql)) {
			
			while(rs.next()) {
				Station station=new Station();
				station.setId(rs.getInt("id_st"));
				station.setStationName(rs.getString("station_name"));
				result.add(station);
			}
			
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e.getMessage());
		}
	}
	
	
	private Station buildStationFromResultSet(ResultSet rs) throws SQLException {
		Station station=new Station();
		
		station.setId(rs.getInt("id_st"));
		station.setStationName(rs.getString("station_name"));
		
		return station;
	}
}
