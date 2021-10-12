package by.epum.training.db.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import by.epum.training.db.dao.BillDAO;
import by.epum.training.db.dao.exception.DAOBillDublicateEntryException;
import by.epum.training.db.dao.exception.DAOException;
import by.epum.training.db.entity.Bill;
import by.epum.training.db.entity.Bill.Status;
import by.epum.training.db.entity.Carriage;
import by.epum.training.db.entity.User;

public class MySQLBillDAO implements BillDAO {

	private Connection connection;

	public MySQLBillDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Long create(Bill bill) throws DAOException {
		String sql="INSERT INTO `bills` (`id_user`,`carriage`,`place`,`cost`,`creation_time`,`status`) VALUES (?,?,?,?,?,?)";
		
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
			st=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			st.setLong(1, bill.getUser().getId());
			st.setLong(2, bill.getCarriage().getId());
			st.setShort(3, bill.getPlace());
			st.setBigDecimal(4, bill.getCost());
			st.setTimestamp(5,Timestamp.valueOf(bill.getCreationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
			
			st.setString(6, bill.getStatus().name());
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
	public Bill read(Long id) throws DAOException {
		String sql="SELECT * FROM `bills` WHERE `id_bill`="+id;

		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			Bill bill=null;
			
			if(rs.next()) {
				bill=new Bill();
				bill.setId(id);
			
				User user=new User();
				user.setId(rs.getLong("id_user"));
				bill.setUser(user);
				
				Carriage carriage=new Carriage();
				carriage.setId(rs.getLong("carriage"));
				bill.setCarriage(carriage);
				
				bill.setPlace(rs.getShort("place"));
				bill.setCost(rs.getBigDecimal("cost"));
				bill.setCreationTime( (rs.getTimestamp("creation_time").toLocalDateTime()));
				bill.setStatus(Status.valueOf(rs.getString("status")));
			}
			return bill;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void update(Bill bill) throws DAOException {
		String sql="UPDATE `bills` SET `id_user`=?, `status`=? WHERE `id_bill`=?";
		
		PreparedStatement st=null;
		
		try {
			st=connection.prepareStatement(sql);

			st.setLong(1, bill.getUser().getId());
			st.setInt(2, bill.getStatus().ordinal()+1);
			st.setLong(3,bill.getId());
			
			st.executeUpdate();

		}catch(Exception e) {
			if(e.getMessage().contains("Duplicate")) {
				throw new DAOBillDublicateEntryException(e);
			}else {
				throw new DAOException(e);}
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public void delete(Long id) throws DAOException {
		String sql="DELETE FROM `bills` WHERE `id_bill`=?";
		
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
	public void updateBillStatus(Long id, Status status) throws DAOException {
		String sql="UPDATE `bills` SET `status`=? WHERE `id_bill`=?";
		
		PreparedStatement st=null;
		
		try {
			st=connection.prepareStatement(sql);
			
			st.setString(1, status.name());
			st.setLong(2,id);
			
			st.executeUpdate();
			
		}catch(Exception e) {
			if(e.getMessage().contains("Duplicate")) {
				throw new DAOBillDublicateEntryException(e);
			}else {
				throw new DAOException(e);}
		}finally {
			try {
				st.close();
			}catch(SQLException e) {};
		}

	}

	@Override
	public List<Bill> readAll() throws DAOException {
		String sql="SELECT * FROM `bills`";
		
		List <Bill> result=new ArrayList<Bill>();
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
			
			while(rs.next()) {
				Bill bill=new Bill();	
				bill.setId(rs.getLong("id_bill"));
				
				bill.setUser(new User());
				bill.getUser().setId(rs.getLong("id_user"));
				
				bill.setCarriage(new Carriage());
				bill.getCarriage().setId(rs.getLong("carriage"));
				bill.setPlace(rs.getShort("place"));
				bill.setCost(rs.getBigDecimal("cost"));
				bill.setStatus(Status.valueOf(rs.getString("status")));
				bill.setCreationTime( (rs.getTimestamp("creation_time").toLocalDateTime()));
				
				result.add(bill);
			}
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public List<Long> findByAll(Long userId, Long carriageId, Short place, BigDecimal cost,Status status) throws DAOException {
		String sql="SELECT `id_bill`  FROM `bills` WHERE `id_user`=? AND `carriage`=? AND `place`=? AND `cost`=? AND `status`=?";
		
		PreparedStatement st=null;
		ResultSet rs=null;
		List <Long> result=new ArrayList<Long>();
		
		try {
			st=connection.prepareStatement(sql);

			st.setLong(1, userId);
			st.setLong(2,carriageId);
			st.setShort(3, place);
			st.setBigDecimal(4, cost);
			st.setString(5, status.name());
		
			rs=st.executeQuery();

			while(rs.next()) {
				result.add(rs.getLong(1));		//
			}
			
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				if(rs!=null) {
					rs.close();}
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public List<Short> findReservedByCarriage(Long carriageId) throws DAOException {
		String sql="SELECT `place`  FROM `bills` WHERE `carriage`="+carriageId+" AND (`status`=?"+ " OR `status`=?)";

		PreparedStatement st=null;
		ResultSet rs=null;
		List <Short> result=new ArrayList<Short>();
		
		try {
			st=connection.prepareStatement(sql);
			
			st.setString(1,Status.ACTIVE.name());
			st.setString(2,Status.PAID.name());
			
			rs=st.executeQuery();

			while(rs.next()) {
				result.add(rs.getShort(1));	//
			}
			
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				if(rs!=null) {
					rs.close();}
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public List<Long> findByUserCarriageNotInvalid(Long userId, Long carriageId) throws DAOException {
		String sql="SELECT `id_bill`  FROM `bills` WHERE `id_user`=? AND `carriage`=? AND `status`<>?";
		
		PreparedStatement st=null;
		ResultSet rs=null;
		List <Long> result=new ArrayList<Long>();
		
		try {
			st=connection.prepareStatement(sql);

			st.setLong(1, userId);
			st.setLong(2,carriageId);
			st.setString(3, Status.INVALID.name());

			rs=st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getLong(1));	//
			}
			
			return result;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}finally {
			try {
				if(rs!=null) {
					rs.close();}
			}catch(SQLException e) {};
			try {
				st.close();
			}catch(SQLException e) {};
		}
	}

	@Override
	public List<Bill> findByPassengerId(Long userId) throws DAOException {
		String sql="SELECT `carriage`,`place`,`cost`,`status` FROM `bills` WHERE `id_user`="+userId;
		
		try (Statement st=connection.createStatement();ResultSet rs=st.executeQuery(sql)){
					
			List<Bill> bills=new ArrayList<Bill>();
			
			if(rs.next()) {
				Bill bill=new Bill();
				
				User user=new User();
				user.setId(userId);
				bill.setUser(user);
				
				Carriage carriage=new Carriage();
				carriage.setId(rs.getLong("carriage"));
				bill.setCarriage(carriage);
				
				bill.setPlace(rs.getShort("place"));
				bill.setCost(rs.getBigDecimal("cost"));
				bill.setStatus(Status.valueOf(rs.getString("status")));

				bills.add(bill);
			}
		
			return bills;
			
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
}
