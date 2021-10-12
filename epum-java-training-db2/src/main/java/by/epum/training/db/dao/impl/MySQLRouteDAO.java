package by.epum.training.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.RouteDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Route;
import by.epum.training.db.entity.Station;

public class MySQLRouteDAO implements RouteDAO {
	private Connection connection;

	public MySQLRouteDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Integer create(Route route) throws DAOException {
		String sql="INSERT INTO `routes` (`departure`,`destination`) VALUES (?,?)";
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			System.out.println("START create routeDAO");
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			Integer departure=route.getDeparture().getId();
			Integer destination=route.getDestination().getId();

			if(departure!=destination) {
				st.setInt(1, departure);
				st.setInt(2, destination);
				st.executeUpdate();
				rs=st.getGeneratedKeys();
				rs.next();

				return rs.getInt(1);
			}else {
				throw new IllegalArgumentException("Destination should differ from departure");
			}
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try{ st.close(); } catch(Exception e) {}
			try{ rs.close(); } catch(Exception e) {}
		}
	}

	@Override
	public Route read(Integer id) throws DAOException {
		String sql="SELECT `id_route`, `departure`,`destination` FROM `routes` WHERE `id_route`="+id;
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
		
			Route route=null;
			
			if(rs.next()) {
				route=new Route();
				
				route.setId(id);
				route.setDeparture(new Station());
				route.setDestination(new Station());
				route.getDeparture().setId(rs.getInt("departure"));
				route.getDestination().setId(rs.getInt("destination"));		
			}
			return route;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Route route) throws DAOException {
		String sql="UPDATE `routes` SET `departure`=?, `destination`=? WHERE `id_route`=?";
		
		PreparedStatement st=null;
		try {
			if(route.getDeparture().getId()==route.getDestination().getId()){
				throw new IllegalArgumentException("Destination should differ from departure");
			}

			st=connection.prepareStatement(sql);

			st.setInt(1, route.getDeparture().getId());
			st.setInt(2, route.getDestination().getId());
			st.setInt(3, route.getId());
			
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
		String sql="DELETE FROM `routes` WHERE `id_route`=?";
		
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);
		
			st.setInt(1, id);
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
	public List<Route> readAll() throws DAOException {
		String sql="SELECT `id_route`, `departure`,`destination` FROM `routes`";
		
		List <Route> result=new ArrayList<Route>();
		
		try(Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)) {
			
			while(rs.next()) {
				Route route=new Route();

				route.setId(rs.getInt("id_route"));
				route.setDeparture(new Station());
				route.setDestination(new Station());
				//only id without names
				route.getDeparture().setId(rs.getInt("departure"));
				route.getDestination().setId(rs.getInt("destination"));

				result.add(route);
			}
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}

	}
	

	@Override
	public List<Integer> findByParametrs(Integer departureId, Integer destinationId) throws DAOException {
		String sqlOnlyDeparture="SELECT `id_route`, `departure`,`destination` FROM `routes` WHERE `departure`="+departureId;
		String sqlOnlyDestination="SELECT `id_route`, `departure`,`destination` FROM `routes` WHERE `destination`="+destinationId;
		String sqlBoth="SELECT `id_route`, `departure`,`destination` FROM `routes` WHERE `departure`="+departureId+ " AND `destination`="+destinationId;
		
		ResultSet rs=null;
		List <Integer> result=new ArrayList<Integer>();
		
		try(Statement st=connection.createStatement()) {
	
			if(departureId.equals(0)) {
				rs=st.executeQuery(sqlOnlyDestination);
			}else if(destinationId.equals(0)) {	
				rs=st.executeQuery(sqlOnlyDeparture);
			}else {
				rs=st.executeQuery(sqlBoth);
			}
	
			while(rs.next()) {
				result.add(rs.getInt(1));		
			}

			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				rs.close();
			}catch(SQLException e) {};
		}
	}
}
