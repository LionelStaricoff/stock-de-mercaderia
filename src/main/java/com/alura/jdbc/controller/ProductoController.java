package com.alura.jdbc.controller;


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

public class ProductoController {

	public int modificar(String nombre, String descripcion,Integer cantidad, Integer id) throws SQLException {
	final Connection con = new ConnectionFactory().recuperarConexion();
	try(con){
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
	}
	}

	public int eliminar(Integer id) throws SQLException {
		final Connection con = new ConnectionFactory().recuperarConexion();
		try(con){
		final PreparedStatement statement =  con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
		try(statement){
		statement.setInt(1, id);
		 statement.execute();
		 
		
		 //metodo para saber cuantas filas fueron eliminadas
		return statement.getUpdateCount();
		  }
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		final Connection con = new ConnectionFactory().recuperarConexion();

		try(con){
		PreparedStatement statement = con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");

		statement.execute();
		final ResultSet resulSet = statement.getResultSet();

		try(resulSet){
		List<Map<String, String>> resultado = new ArrayList<>();

		while (resulSet.next()) {
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resulSet.getInt("ID")));
			fila.put("NOMBRE", resulSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resulSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resulSet.getInt("CANTIDAD")));

			resultado.add(fila);
		    }
		 
		return resultado;
		  }
		}
	}

	public void guardar(Producto producto) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
	    final Connection con =  factory.recuperarConexion();
	    
	    try(con){
		con.setAutoCommit(false);// desactivamos la responsbilidad del jdbc para hacer transacciones

		final PreparedStatement statement = con.prepareStatement(
				"INSERT INTO PRODUCTO "
		       + "(nombre, descripcion, cantidad) " 
		       + " VALUES(?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);// el return se utiliza para que devuelva el id generado
 
		//try con recursos que cierra la conexcieon automaticamente del statement
		try(statement){
		ejecutarRegistro(producto, statement);
		
		con.commit();// verifica que todas las transacciones se realicen correctamente
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("rollback e la transaccion");
		con.rollback();// ante cualquer error entre transacciones se regresa al estado inicial

	}
  }


	private void ejecutarRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		
		statement.execute();
		final ResultSet resulset = statement.getGeneratedKeys();
		
		//ejecutando el try con recursos de cierre automatico
		try(resulset ){
		while (resulset.next()) {
			producto.setId(resulset.getInt(1) );
			System.out.println(String.format("Fue insertado el producto de ID %s",producto) );
				
		}
		}
	}

}
