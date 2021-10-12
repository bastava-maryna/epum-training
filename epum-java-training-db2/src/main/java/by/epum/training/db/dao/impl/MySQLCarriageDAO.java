package by.epum.training.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.CarriageDAO;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.CarriageType;
import by.epum.training.db.entity.Train;

public class MySQLCarriageDAO implements CarriageDAO {

	private Connection connection;
	
	public MySQLCarriageDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Long create(Carriage carriage) throws DAOException {
		String sql="INSERT INTO `carriages` (`train`,`carr_number`,`carr_type`) VALUES (?,?,?)";
		
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, carriage.getTrain().getId());
			st.setShort(2, carriage.getCarriageNumber());
			st.setString(3, carriage.getCarriageType().name());
			
			st.executeUpdate();

			rs=st.getGeneratedKeys();
			rs.next();
			
			return rs.getLong(1);

		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try{ st.close(); } catch(Exception e) {}
			try{ rs.close(); } catch(Exception e) {}
		}

	}

	@Override
	public Carriage read(Long id) throws DAOException {
		String sql="SELECT `id_carriage`, `train`,`carr_number`,`carr_type` FROM `carriages` WHERE `id_carriage`="+id;
	
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){			
			Carriage carriage=null;
			
			if(rs.next()) {
				carriage=new Carriage();
				carriage.setId(id);
				
				carriage.setTrain(new Train());
				carriage.getTrain().setId(rs.getInt("train"));
				carriage.setCarriageType(CarriageType.valueOf(rs.getString("carr_type")));
				carriage.setCarriageNumber(rs.getShort("carr_number"));
			}
			return carriage;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Carriage carriage) throws DAOException {
		String sql="UPDATE `carriages` SET `train`=?, `carr_number`=?,`carr_type`=? WHERE `id_carriage`=?";
		
		PreparedStatement st=null;
		
		try {
			st=connection.prepareStatement(sql);

			st.setInt(1, carriage.getTrain().getId());
			st.setShort(2, carriage.getCarriageNumber());
			st.setString(3, carriage.getCarriageType().name());
			st.setLong(4, carriage.getId());
			
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
	public void delete(Long id) throws DAOException {
		String sql="DELETE FROM `carriages` WHERE `id_carriage`=?";
		
		PreparedStatement st=null;

		try {
			st=connection.prepareStatement(sql);
			st.setLong(1, id);
			
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
	public List<Carriage> readAllByTrain(Integer trainId) throws DAOException {
		String sql="SELECT `id_carriage`, `train`,`carr_number`,`carr_type` FROM `carriages` WHERE `train`=?";
		
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql);

			st.setInt(1, trainId);
			rs=st.executeQuery();
			
			List<Carriage> result=new ArrayList<Carriage>();
			
			while(rs.next()) {
				Carriage carriage=new Carriage();
				carriage.setId(rs.getLong("id_carriage"));

				Train train=new Train();
				train.setId(trainId);
				carriage.setTrain(train);

				carriage.setCarriageNumber(rs.getShort("carr_number"));
				carriage.setCarriageType(CarriageType.valueOf(rs.getString("carr_type")));

				result.add(carriage);
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
	public List<Carriage> readAll() throws DAOException {
		String sql="SELECT `id_carriage`, `train`,`carr_number`,`carr_type` FROM `carriages`";
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			List <Carriage> result=new ArrayList<Carriage>();
			
			while(rs.next()) {
				Carriage carriage=new Carriage();

				carriage.setId(rs.getLong("id_carriage"));
				carriage.setTrain(new Train());
				carriage.getTrain().setId(rs.getInt("train"));
				carriage.setCarriageNumber(rs.getShort("carr_number"));
				//only id without names
				carriage.setCarriageType(CarriageType.valueOf(rs.getString("carr_type")));

				result.add(carriage);
			}
			
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}

	}
	
	@Override
	public List<Long> findByAll(Integer trainId, Short carriageNumber, String carriageType) throws DAOException {
		String sql="SELECT `id_carriage`, `train`,`carr_number`,`carr_type` FROM `carriages` WHERE `train`=? AND `carr_number`=?  AND `carr_type`=?";
		
		PreparedStatement st=null;

		ResultSet rs=null;
		List <Long> result=new ArrayList<Long>();
		
		try {
			st=connection.prepareStatement(sql);
			
			st.setInt(1, trainId);
			st.setShort(2, carriageNumber);
			st.setString(3, carriageType);

			rs=st.executeQuery();

			while(rs.next()) {
				result.add(rs.getLong(1));
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
	public List<Carriage> readAllByTrainAndType(Integer trainId, CarriageType carriageType) throws DAOException {
		String sql="SELECT `id_carriage`,`train`,`carr_number`FROM `carriages` WHERE `train`="+trainId+ " AND `carr_type`="+carriageType;
	
		List <Carriage> result=new ArrayList<Carriage>();
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){

			while(rs.next()) {
				Carriage carriage=new Carriage();

				carriage.setId(rs.getLong("id_carriage"));
				Train train=new Train();
				train.setId(trainId);
				carriage.setTrain(train);
				carriage.setCarriageNumber(rs.getShort("carr_number"));
				//only id without names
				carriage.setCarriageType(carriageType);

				result.add(carriage);
			}
		
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<Carriage> findEmpty(Integer trainId) throws DAOException {	
		String sql="select id_carriage,carr_number,carr_type\r\n"
				+ "from carriages \r\n"
				+ "left join \r\n"
				+ "(select carriage,status from bills where status='INVALID')as b on b.carriage=id_carriage\r\n"
				+ " where train=? and status is null";

		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql);

			st.setInt(1, trainId);
			
			rs=st.executeQuery();
	
			List<Carriage> result=new ArrayList<Carriage>();
			
			while(rs.next()) {
				Carriage carriage=new Carriage();
				carriage.setId(rs.getLong("id_carriage"));

				Train train=new Train();
				train.setId(trainId);
				carriage.setTrain(train);

				carriage.setCarriageNumber(rs.getShort("carr_number"));
				carriage.setCarriageType(CarriageType.valueOf(rs.getString("carr_type")));

				result.add(carriage);
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


	public List<Carriage> findNotEmpty(Integer trainId) throws DAOException {
			String sql="select distinct c.id_carriage, c.carr_number,c.carr_type "
				+ "from "
				+ "(select id_carriage, carr_number,carr_type "
				+ "from carriages "
				+ "where train=?) as c "
				+ "join bills on carriage=c.id_carriage "
				+ "where status<>?";


		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql);

			st.setInt(1, trainId);
			st.setString(2,Status.INVALID.name());
			rs=st.executeQuery();
			
			List<Carriage> result=new ArrayList<Carriage>();
			while(rs.next()) {
				Carriage carriage=new Carriage();
				carriage.setId(rs.getLong("id_carriage"));

				Train train=new Train();
				train.setId(trainId);
				carriage.setTrain(train);

				carriage.setCarriageNumber(rs.getShort("carr_number"));
				carriage.setCarriageType(CarriageType.valueOf(rs.getString("carr_type")));

				result.add(carriage);
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
}
