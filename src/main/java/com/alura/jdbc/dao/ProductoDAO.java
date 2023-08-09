package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	final private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto)  {
	    
	    try{
	    	PreparedStatement statement;
		    statement = con.prepareStatement(
				"INSERT INTO PRODUCTO "
		       + "(nombre, descripcion, cantidad) " 
		       + " VALUES(?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);// el return se utiliza para que devuelva el id generado
 
		//try con recursos que cierra la conexcieon automaticamente del statement
		try(statement){
			statement.setString(1, producto.getNombre());
			statement.setString(2, producto.getDescripcion());
			statement.setInt(3, producto.getCantidad());
			
			statement.execute();
			
			final ResultSet resulset = statement.getGeneratedKeys();
			
			try(resulset){
				while(resulset.next()) {
					producto.setId(resulset.getInt(1));
					System.out.println(String.format("Fue insertado el producto de ID %s",producto) );
				}
				}
			}
		
	} catch (SQLException e) {
		new RuntimeException(e);
	}
		
	}
	
	
	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<>();

		try{
		PreparedStatement statement = con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");

		try(statement){
				statement.execute();
		final ResultSet resulSet = statement.getResultSet();

		try(resulSet){
		while (resulSet.next()) {
			Producto fila = new Producto(resulSet.getInt("ID"),
					resulSet.getString("NOMBRE"),
					resulSet.getString("DESCRIPCION"),
					resulSet.getInt("CANTIDAD")
					);
	
			resultado.add(fila);
		    }
		}
		}
		 
		return resultado;
	} catch(SQLException e) {
		throw new RuntimeException(e);
	}
		}

	
	
	public int eliminar(Integer id) {
		
		PreparedStatement statement;
		try{
			statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
		
		try(statement) {
			statement.setInt(1, id);
			 statement.execute();
			 //metodo para saber cuantas filas fueron eliminadas
				return statement.getUpdateCount();
		}  
		} catch (SQLException e) {
		throw new RuntimeException(e);
		}
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		try{
			final PreparedStatement statement =  con.prepareStatement("UPDATE PRODUCTO SET " 
					 + " NOMBRE = ?"
					 + ", DESCRIPCION = ?"
					 + ", CANTIDAD = ?" 
					 +" WHERE ID = ?");
			try(statement){
			statement.setString(1, nombre);
			 statement.setString(2, descripcion);
			 statement.setInt(3, cantidad);
			 statement.setInt(4, id);
			 
			 statement.execute();
			
		int UPdateCount	= statement.getUpdateCount();
			 //metodo para saber cuantas filas fueron modificados
				return UPdateCount;
		}  
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
		
		 
		
		
		



