package com.alura.jdbc.controller;


import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	private ProductoDAO productoDAO;
	
	public ProductoController()  {
		var factory =  new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperarConexion());
	}
	
	
	

	public int modificar(String nombre, String descripcion,Integer cantidad, Integer id){
	return this.productoDAO.modificar(nombre,  descripcion, cantidad,  id);
	}

	public int eliminar(Integer id){
		return this.productoDAO.eliminar(id);
	
	}

	public List<Producto> listar(){
		return this.productoDAO.listar();
		
		
	}

	public void guardar(Producto producto){
		this.productoDAO.guardar(producto);
	}




}
