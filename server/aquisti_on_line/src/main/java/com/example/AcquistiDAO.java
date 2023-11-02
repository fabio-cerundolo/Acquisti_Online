/**
 * 
 */
package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AcquistiDAO implements IDao<Cliente>  {
	public static final int MYSQL_DUPLICATE_PK = 1062;
	final String SELECT_RUBRICA_ALL  = "SELECT "
										+ " id_cliente "
										+ ",nome"
										+ ",cognome"
										+ " FROM clienti ";

	Connection connection = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;		

	public AcquistiDAO() {
	}

	/*
	 * Imposta una connessione al db attraverso il metodo factory
	 */
	public void setConnection(Connection connection) {
		this.connection = connection; 
	}

	@Override
	public boolean create(Cliente t) throws SQLException {
		boolean isSqlerror = false;
		SQLException sqlExcpOrigin = null;
		final String INSERT_RUBRICA  =  "INSERT INTO clienti("
										+ " nome "
										+ ",cognome"
										+ ") VALUES(?,?)";

		try {
			stmt = connection.prepareStatement(INSERT_RUBRICA);
			setStatementFromEntity(stmt, t, true);
			stmt.executeUpdate();
			System.out.println("SchedaPersona Added Successfully");
			return true;

		} catch (SQLException e) {
			// Catched Sql Error
			isSqlerror = true;           // May be MYSQL_DUPLICATE_PK
			sqlExcpOrigin = e;

			// Executed with or without errors	
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				// Exception closing statement or connection
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				// Not Duplicate primary key: exception to be thrown
				if (isSqlerror && sqlExcpOrigin.getErrorCode() != MYSQL_DUPLICATE_PK) {
					sqlExcpOrigin.printStackTrace();
					throw sqlExcpOrigin;
				}  
			}
		}
		// MYSQL_DUPLICATE_PK
		return false;
	}
    

	@Override
	public Cliente read(Cliente t) throws SQLException {
		final String SELECT_RUBRICA  = SELECT_RUBRICA_ALL
				+ " WHERE id_cliente = ?";

		try {
			stmt = connection.prepareStatement(SELECT_RUBRICA);
			stmt.setInt(1, t.getId());
			resultSet = stmt.executeQuery();

			// SchedaPersona non trovata, 0 righe restituite
			if (!resultSet.next()) {
				return null;
			}
			// Resultset con una sola riga disponibile
			Cliente tOut = new Cliente();
			setEntityFromResultset(tOut, resultSet);
			System.out.println("SchedaPersona restituita "+t.toString());
			return tOut;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}	 
	}

	@Override
	public boolean update(Cliente t) throws SQLException {
		boolean isSqlerror = false;
		boolean bRet = true;
		SQLException sqlExcp = null;
		final String UPDATE_RUBRICA  = "UPDATE clienti "
										+ "SET "
										+ " nome = ? "
										+ ",cognome = ? "
										+ "WHERE id_cliente = ? ";
		try {
			stmt = connection.prepareStatement(UPDATE_RUBRICA);
			setStatementFromEntity(stmt, t, false);
			stmt.executeUpdate();
			bRet = true;         // Suppose row deleted succesfully

		} catch (SQLException e) {
			isSqlerror = true;
			sqlExcp = e;

			// Executed with or without errors	
		} finally {  
			try {
				if (stmt != null) {
					if (stmt.getUpdateCount() <= 0 ) {
						if (!isSqlerror) {
							bRet = false;
						}						
					}
				}				
				if (stmt != null)
					stmt.close();
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
			} catch (SQLException e) {
				// Exception in close stmt or connection: throw the original exception
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
				// Throw the exception in close  
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				// Exception in close stmt or connection: throw the original exception
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
				e.printStackTrace();
				throw e;
			}		
		}
		return bRet;
	}

	@Override
	public boolean delete(Cliente t) throws SQLException {
		boolean isSqlerror = false;
		boolean bRet = true;
		SQLException sqlExcp = null;
		final String DELETE_RUBRICA  =  "DELETE FROM clienti WHERE id_cliente=?";
		try {
			stmt = connection.prepareStatement(DELETE_RUBRICA);
			stmt.setInt(1, t.getId());
			stmt.executeUpdate();			
		} catch (SQLException e) {
			isSqlerror = true;
			sqlExcp = e;

			// Executed with or without errors	
		} finally {
			try {
				if (stmt != null) {
					if (stmt.getUpdateCount() <= 0 ) {
						if (!isSqlerror) {
							bRet = false;
						}						
					}
				}				
				if (stmt != null)
					stmt.close();
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
			} catch (SQLException e) {
				// Exception in close stmt or connection: throw the original exception
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
				// Throw the exception in close  
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				// Exception in close stmt or connection: throw the original exception
				if (isSqlerror) {
					sqlExcp.printStackTrace();
					throw sqlExcp;
				}
				e.printStackTrace();
				throw e;
			}		
		}
		return bRet;
	}

	@Override
	public Cliente[] getAll() throws SQLException {
		Cliente tOut = null;
		List<Cliente> lOut = new ArrayList<Cliente>();
		Cliente arT[] = null;
		String queryString = SELECT_RUBRICA_ALL;
		
		try {
			stmt = connection.prepareStatement(queryString);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				tOut = new Cliente();
				setEntityFromResultset(tOut, resultSet);
				lOut.add(tOut);
			}
			arT = (Cliente[]) lOut.toArray(new Cliente[0]);

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}

		return arT;
	}

	@Override
	public Cliente[] findAll(String whereCondition) throws SQLException {
		Cliente tOut = null;
		List<Cliente> lOut = new ArrayList<Cliente>();
		Cliente arT[] = null;
		String queryString 	= SELECT_RUBRICA_ALL
							+ " WHERE "
							+ whereCondition;
		
		try {
			stmt = connection.prepareStatement(queryString);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				tOut = new Cliente();
				setEntityFromResultset(tOut, resultSet);
				lOut.add(tOut);
			}
			arT = new Cliente[lOut.size()];
			arT = (Cliente[]) lOut.toArray(arT);

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}	
		return arT;	}

	/*
	 * Set entity values with resultset row
	 */
	private void setEntityFromResultset(Cliente tOut, ResultSet resultSet) throws SQLException {
		tOut.setNome(resultSet.getString("nome"));
		tOut.setCognome(resultSet.getString("cognome"));
		
	}

	/*
	 * Set statement with entity values
	 */
	private void setStatementFromEntity(PreparedStatement stmt, Cliente t, boolean isCreate) throws SQLException {
		int i=1;			
		stmt.setString(i++, t.getNome());
		stmt.setString(i++, t.getCognome());
		if (!isCreate) {
			stmt.setInt(i++, t.getId());
		}
	}
}
