package com.example;
import java.sql.SQLException;

/**
 * Interfaccia per generica definizione di un DAO object
 * che implementi le funzioni CRUD e funzioni di ricerca generiche
 */
public interface IDao<T> {
    
	/*
	 * Restituisce true se inserimento effettuato
	 * e false se <T> era gi� esistente
	 */
    boolean create(T t) throws SQLException;    

    /*
     * Restituisce l'oggetto <T> se esistente o null
     */
    T read(T t) throws SQLException;
           
	/*
	 * Restituisce true se aggiornamento effettuato con successo
	 * e false se <T> NON era esistente
	 */
    boolean update(T t) throws SQLException;            
    
	/*
	 * Restituisce true se delete effettuato con successo
	 * e false se <T> NON era esistente
	 */
    boolean delete(T t) throws SQLException;
    
	/*
	 * Restituisce un array con tutte  righe dell'entity <T>
	 * oppure un array vuoto
	 */
    T[] getAll() throws SQLException;
    
	/*
	 * Restituisce un array con tutte righe dell'entity <T>
	 * che soddisfano la condizione where, senza la clausola WHERE
	 * oppure un array vuoto
	 */
    T[] findAll(String whereCondition) throws SQLException;
	
}