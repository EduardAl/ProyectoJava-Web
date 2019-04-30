/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.utils;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.control.DatePicker;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author vaselinux
 */
public class Utilidades {
    
    private static DefaultTableModel modeloTabla;
    private static DatePicker picker;
    private static JDatePanel auxPanel;
    
    public static JTable cargarTabla(String[] columnas,ResultSet rs) throws SQLException{
        try {
            modeloTabla = new DefaultTableModel(null,columnas){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            Object datos[] = new Object[cantidadColumnas];
            while(rs.next()){//recorrer registros
                for(int i=0;i<cantidadColumnas;i++){ //cargar datos
                    datos[i]=rs.getString(i+1);
                }
                modeloTabla.addRow(datos);

            }
            JTable tabla = new JTable(modeloTabla);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            return tabla;
        } catch (SQLException e) {
            Logger.getLogger(Utilidades.class).error("Error al cargar tabla en funciòn cargarTable ",e);
            return null;
        }
    }
    
    public static Object regresarValorHashMap(HashMap map, String valor)
    {
        //cuando se quiere la llave por medio del valor
        for (Object o : map.keySet()) {
            if(map.get(o).equals(valor))
                return o;
        }
        return null;
    }
    
    public static String generarNumAleatorio()
    {
        Random ale = new Random();
        int numero = ale.nextInt(1000);
        return String.format("%03d", numero);
    }
    
    
    
}
