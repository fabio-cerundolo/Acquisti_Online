package com.example;

import java.sql.SQLException;

public interface Acquisti {
	Cliente read(Cliente s) throws ClassNotFoundException, SQLException;
	boolean insert(Cliente s) throws ClassNotFoundException, SQLException;
	boolean update(Cliente s) throws ClassNotFoundException, SQLException;
	boolean delete(Cliente s) throws ClassNotFoundException, SQLException;
	Cliente[] getAll() throws ClassNotFoundException, SQLException;
	Cliente[] search(String s) throws SQLException, ClassNotFoundException;
	String toString();
}
