/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import sv.com.tesa.ticket.beans.EmployeeBean;
import sv.com.tesa.ticket.beans.Usuarios;

/**
 *
 * @author eduar
 */
public class UsersModel extends Conexion
{
    public ArrayList<Usuarios> listarUsuarios() throws SQLException
    {
        ArrayList<Usuarios> lista = new ArrayList<>();
        try {
            String sql = "SELECT  r.rname as 'rol', e.id, e.fname, e.lname, e.email, e.chief, d.dname as 'department' from roles r inner join employees e on r.id = e.rol inner join departments d on d.id = e.department order by e.id;";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next())
            {
                Usuarios usuarios = new Usuarios();
                usuarios.setId(rs.getInt("id"));
                
                usuarios.setFname(rs.getString("fname"));
                usuarios.setLname(rs.getString("lname"));
                usuarios.setEmail(rs.getString("email"));
                usuarios.setRol(rs.getString("rol"));
                usuarios.setDepartement(rs.getString("department"));
                lista.add(usuarios);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            this.desconectar();
        }
    }
    
    public Usuarios obtenerUsuario(Integer id)
    {
        try {
            String sql = "SELECT  r.rname as 'rol', e.id, e.fname, e.lname, e.email, e.chief, d.dname as 'department' from roles r inner join employees e on r.id = e.rol inner join departments d on d.id = e.department where e.id = ?;";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            Usuarios usuarios = new Usuarios();
            rs.first();
            usuarios.setId(rs.getInt("id"));
            usuarios.setFname(rs.getString("fname"));
            usuarios.setLname(rs.getString("lname"));
            usuarios.setEmail(rs.getString("email"));
            usuarios.setRol(rs.getString("rol"));
            usuarios.setChief(rs.getString("chief"));
            usuarios.setDepartement(rs.getString("department"));
            return usuarios;
        } catch (SQLException ex) {
            Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean modificarJefe(EmployeeBean beanEmpleado, boolean op)
    {
         try {
            String sql = "CALL sp_update_boss_employees(?,?,?,?,?,?,?,?,?)";
                         System.out.println(beanEmpleado.getId());
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, beanEmpleado.getId());
            st.setInt(2, beanEmpleado.getRol());
            st.setString(3, beanEmpleado.getNombre());
            st.setString(4, beanEmpleado.getApellido());
            st.setString(5, beanEmpleado.getEmail());
            st.setString(6, beanEmpleado.getPassword());
            st.setString(7, beanEmpleado.getDepartamento());
            st.setBoolean(8, op);
            st.setInt(9, beanEmpleado.getJefe());
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
        } catch (SQLException e) {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error al modificar "
                    + "jefes en función modificarJefe",e);
            return false;
        }
    }
    
    public HashMap<Integer, String> listarRoles()
    {
        HashMap<Integer, String> map = new HashMap<>();
        try {
            String sql = "CALL sp_select_roles()";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs  = st.executeQuery();
            while(rs.next())
            {
                map.put(rs.getInt("id"), rs.getString("rol"));
            }
            this.desconectar();
            return map;
        } catch (SQLException e) 
        {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error al listar "
                    + "roles en función listarRoles",e);
            return null;
        }
    }
    
    public HashMap<String, String> listarDepartamentos()
    {
        HashMap<String, String> map = new HashMap<>();
        try {
            String sql = "CALL sp_select_departments()";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs  = st.executeQuery();    
            while(rs.next())
            {
                map.put(rs.getString("id"), rs.getString("departamento"));
            }
            this.desconectar();
            return map;
        } catch (SQLException e) 
        {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error al listar "
                    + "roles en función listarDepartamentos",e);
            return null;
        }
    }
    
    public boolean ingresarEmpleado (EmployeeBean empleado)
    {
        try {
            String sql = "CALL sp_insert_new_employee(?,?,?,?,?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, empleado.getRol());
            st.setString(2, empleado.getNombre());
            st.setString(3, empleado.getApellido());
            st.setString(4, empleado.getEmail());
            st.setInt(5, empleado.getJefe());
            st.setString(6, empleado.getDepartamento());
            
            int res = st.executeUpdate();
            this.desconectar();
            return res > 0;
        } catch (SQLException e) {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error en AdminBossModel al ingresar "
                    + "empleado en función ingresarEmpleado",e);
            return false;
        }
    }
    public boolean eliminarEmpleado (Integer empleado)
    {
        try {
            String sql = "CALL sp_delete_employee(?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, empleado);
            int res = st.executeUpdate();
            this.desconectar();
            return res > 0;
        } catch (SQLException e) {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error en AdminBossModel al ingresar "
                    + "empleado en función ingresarEmpleado",e);
            return false;
        }
    }
    
    public HashMap<Integer,String> listarJefes()
    {
        HashMap<Integer, String> map = new HashMap<>();
        try {
            String sql = "CALL sp_select_boss_employees()";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs  = st.executeQuery();    
            while(rs.next())
            {
                map.put(rs.getInt(1), rs.getString(2));
            }
            this.desconectar();
            return map;
        } catch (SQLException e) 
        {
            org.apache.log4j.Logger.getLogger(UsersModel.class).error("Error al listar "
                    + "jefes en función listarDepartamentos",e);
            return null;
        }
    }
}
