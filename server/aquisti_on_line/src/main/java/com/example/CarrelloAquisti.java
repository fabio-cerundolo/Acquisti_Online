package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



public class CarrelloAquisti implements Acquisti {
    
    ArrayList<Cliente> alCliente;	
    int maxCliente;
    
    public CarrelloAquisti(int maxCliente) {
		this.maxCliente = maxCliente; 
		alCliente = new ArrayList<Cliente>();
	}

	@Override
	public boolean insert(Cliente s) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		boolean isCreated = false;
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);	
		
		isCreated = acquistiDAO.create(s);
		conn.commit();
		conn.close();
		return isCreated;
	}


	@Override
	public Cliente read(Cliente s) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		Cliente schedaRead = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);			

		schedaRead = acquistiDAO.read(s);

		conn.close();
		return schedaRead;
	}

	@Override
	public boolean update(Cliente s) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		boolean isUpdated = false;
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);	
		
		isUpdated = acquistiDAO.update(s);
		
		conn.commit();
		conn.close();
		return isUpdated;
	}


	@Override
	public boolean delete(Cliente s) throws ClassNotFoundException, SQLException  {
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		boolean isDeleted = false;
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);	
		
		isDeleted = acquistiDAO.delete(s);
		conn.commit();
		conn.close();
		return isDeleted;
	}
	
	@Override
	public Cliente[] search(String strToMatch) throws SQLException, ClassNotFoundException {
		Cliente[] arSearch = null;
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		String whereCondition = "";
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);	
		
		whereCondition = "nome LIKE '%" + strToMatch + "%'  cognome "
			           + strToMatch + "%'"
				;
		arSearch = acquistiDAO.findAll(whereCondition);
		conn.close();
		return arSearch;
	}

	@Override
	public Cliente[] getAll() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		AcquistiDAO acquistiDAO = null;
		ConnectionFactory cf = ConnectionFactory.getInstance();
		Cliente[] arSearch = null;
		
		conn = cf.getConnection();
		acquistiDAO = new AcquistiDAO();
		acquistiDAO.setConnection(conn);	
		
		arSearch = acquistiDAO.getAll();
		conn.close();
		return arSearch;	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cliente sk : alCliente) {
			sb.append("[Nome=" + sk.getNome());
			sb.append(" Cognome=" + sk.getCognome());
			sb.append("]");
		}
		return sb.toString();
	}
}
