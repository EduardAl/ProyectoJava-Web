/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import javax.swing.JTable;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.RequestBean;
import static sv.com.tesa.ticket.models.ConexionModel.conexion;
import sv.com.tesa.ticket.utils.Utilidades;
import sv.com.tesa.ticket.beans.SingleRequestBean;
import org.apache.log4j.Logger;
/**
 *
 * @author Edu
 */
public class RequestModel extends LoginModel{
    
    private JTable tabla;
    
    public boolean ingresarPeticion(RequestBean peticion)
    {
        try {
            
            String sql = "call sp_insert_request(?,?,?,?,?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, peticion.getRequestType());
            st.setString(2, peticion.getDepartment());
            st.setString(3, peticion.getTitle());
            st.setString(4, peticion.getDescription());
            st.setInt(5, peticion.getCreatedBy());
            st.setNull(6, Types.NULL);
            
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0 ;
            
            
        } catch (SQLException e) {
            Logger.getLogger(RequestModel.class).error("Error al ingresar peticion en "
                    + "funcion ingresarPeticion",e);
            return false;
        }
    }
    
    public HashMap<Integer, String> listarTiposPeticion()
    {
        HashMap<Integer, String> map = new HashMap<>();
        try {
            String sql = "select * from request_types";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs  = st.executeQuery();
            while(rs.next())
            {
                map.put(rs.getInt("id"), rs.getString("rt_name"));
            }
            this.desconectar();
            return map;
        } catch (SQLException e) 
        {
            Logger.getLogger(RequestModel.class).error("Error al listar peticiones en "
                    + "funcion ListarTiposPeticion",e);
            return null;
        }
    }
    
    public JTable listarPeticiones(LoginBean usuario)
    {
        try {
            String sql = "call sp_select_request(?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setLong(1, LoginBean.getId());
            st.setString(2, LoginBean.getDepartamento());
            
            if (st.execute()) 
            {
                rs = st.getResultSet();
                String[] col = {"id", "Titulo", "Descripcion", "Departamento", 
                    "Tipo de peticion", "Estado de la peticion"}; 
                tabla = Utilidades.cargarTabla(col, rs);
            }
            else{
                tabla = null;
            }
            this.desconectar();
            return tabla;
        } catch (SQLException e) 
        {
            Logger.getLogger(RequestModel.class).error("Error al listar peticiones en "
                    + "funcion listarPeticiones",e);
            return null;
        }
    }
    
    @SuppressWarnings("empty-statement")
    public SingleRequestBean listarPeticionIndividual(RequestBean peticion)
    {
        try {
            String sql = "CALL sp_select_individual_request(?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1,peticion.getId());
            st.setInt(2,peticion.getCreatedBy());
            rs = st.executeQuery();
            SingleRequestBean peticionIndividual = new SingleRequestBean();
            peticionIndividual.setId(0);
            
            while(rs.next())
            {
                peticionIndividual.setId(rs.getInt("id"));
                peticionIndividual.setTipoPeticion(rs.getString("rt_name"));
                peticionIndividual.setDepartamento(rs.getString("dname"));
                peticionIndividual.setTitulo(rs.getString("title"));;
                peticionIndividual.setDescripcion(rs.getString("descrip"));
                peticionIndividual.setCreadoPor(rs.getString("Creado por"));
                peticionIndividual.setEstado(rs.getString("rs_name"));
                peticionIndividual.setComentario(rs.getString("commentary"));
                peticionIndividual.setFechaCreacion(rs.getString("created_at"));
                peticionIndividual.setFechaModificacion(rs.getString("updated_at"));                
            }
            this.desconectar();
            return peticionIndividual;
        } catch (SQLException e) {
            Logger.getLogger(RequestModel.class).error("Error al listar peticiones individuales en "
                    + "funcion listarPeticionIndividual",e);
            return null;    
        }
    }
    
    public boolean modificarPeticion(RequestBean peticion)
    {
        try {
            String sql = "call sp_modify_request (?,?,?,?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, peticion.getId());
            st.setInt(2, peticion.getRequestType());
            st.setString(3,peticion.getTitle());
            st.setString(4,peticion.getDescription());
            if (peticion.getCommentary() != null) {
                st.setString(5, peticion.getCommentary());
            }
            else
            {
                st.setNull(5, Types.NULL);
            }
            
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
            
        } catch (SQLException e) {
            Logger.getLogger(RequestModel.class).error("Error al modificar una peticion en "
                    + "funcion modificarPeticion",e);
            return false;
        }
    }
    
    public boolean eliminarPeticion(RequestBean peticion)
    {
        try {
            String sql = "call sp_delete_request(?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, peticion.getId());
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
            
        } catch (SQLException e) {
            Logger.getLogger(RequestModel.class).error("Error al eliminar peticion en "
                    + "funcion eliminarPeticion",e);
            return false;
        }
    }
    
    public boolean denegarPeticion(RequestBean peticion)
    {
        try {
            String sql = "call sp_deny_request(?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, peticion.getId());
            st.setString(2, peticion.getCommentary());
            
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
            
        } catch (SQLException e) {
            Logger.getLogger(RequestModel.class).error("Error al denegar peticion en "
                    + "funcion denegarPeticion",e);
            return false;
        }
    }
    
}
