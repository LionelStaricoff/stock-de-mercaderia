package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	private DataSource dataSource;
	
	public ConnectionFactory() {
		//variable de la libreria c3p0 para menejar conecciones
		var PooledDataSource = new ComboPooledDataSource();
		PooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		PooledDataSource.setUser("root");
		PooledDataSource.setPassword("1234");
		PooledDataSource.setMaxPoolSize(10);//cantidad de conexciones que se pueden abrir
		
		this.dataSource = PooledDataSource;
	
	}

	public Connection recuperarConexion()  {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			new RuntimeException(e);
		}
		return null;
	
              
	}
}
