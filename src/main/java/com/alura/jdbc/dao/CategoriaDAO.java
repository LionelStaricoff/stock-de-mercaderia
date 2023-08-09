package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {
	private Connection con;

	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE FROM CATEGORIA");
			
			try(statement){
				final ResultSet resulset = statement.executeQuery();
				try(resulset){
					while(resulset.next()) {
					var categoria = new Categoria(resulset.getInt("ID"),
							resulset.getString("NOMBRE"));
					resultado.add(categoria);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public List<Categoria> listarConProducto() {
	List<Categoria> resultado = new ArrayList<>();
		
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT C.ID, C.NOMBRE, "
					+ "P.ID, P.NOMBRE, P.CANTIDAD FROM CATEGORIA C "
					+ "INNER JOIN PRODUCTO P ON C.ID = P.CATEGORIA_ID");
			
			try(statement){
				final ResultSet resulset = statement.executeQuery();
				try(resulset){
					while(resulset.next()) {
						Integer categoriaId = resulset.getInt("C.ID");
						String categoriaNombre = resulset.getString("C.NOMBRE");
						
					var categoria = resultado.stream()
							.filter(cat-> cat.getId().equals(categoriaId))
							.findAny().orElseGet(()->{
								Categoria cat = new Categoria(categoriaId, categoriaNombre);
								
								
								resultado.add(cat);
								return cat;
							});
					Producto producto = new Producto(resulset.getInt("P.ID"),
									resulset.getString("P.NOMBRE"),
									resulset.getInt("P.CANTIDAD"));
									
									categoria.agregar(producto);
					
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

}
