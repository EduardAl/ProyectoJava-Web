/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import sv.com.tesa.ticket.beans.DepartmentBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Edu
 */
public class AdminDeptModel extends Conexion {
    public ArrayList<DepartmentBean> listarDept()
    {
        try {
            
            ArrayList<DepartmentBean> lista = new ArrayList<>();
            String sql = "CALL sp_select_departments()";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next())
            {
                DepartmentBean departmentBean = new DepartmentBean();
                departmentBean.setId(rs.getString("id"));
                departmentBean.setNombreDept(rs.getString("departamento"));
                lista.add(departmentBean);
            }
            return lista;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(AdminDeptModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public DepartmentBean obtenerDepatartamento(String id)
    {
        try {
            
            String sql = "CALL sp_select_department(?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();
            rs.first();
                DepartmentBean departmentBean = new DepartmentBean();
                departmentBean.setId(rs.getString("id"));
                departmentBean.setNombreDept(rs.getString("departamento"));
            return departmentBean;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(AdminDeptModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean ingresarDept(DepartmentBean beanDept)
    {
        try {
            String sql = "call sp_insert_department(?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, beanDept.getId());
            st.setString(2, beanDept.getNombreDept());
            int resultado = st.executeUpdate();
            
            if (resultado > 0) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
        } catch (SQLException e) 
        {
            Logger.getLogger(AdminDeptModel.class).error("Error al insertar "
                    + "departamentos en función insertarDept",e);
            return false;
        }
    }
    public boolean modificarDept(DepartmentBean beanDept)
    {
        try {
            String sql = "call sp_update_department(?, ?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, beanDept.getId());
            st.setString(2, beanDept.getNombreDept());
            int resultado = st.executeUpdate();
            
            if (resultado > 0) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
        } catch (SQLException e) 
        {
            Logger.getLogger(AdminDeptModel.class).error("Error al insertar "
                    + "departamentos en función insertarDept",e);
            return false;
        }
    }
    
    public boolean eliminarDepartamento(String id)
    {
        try {
            String sql = "call sp_delete_department(?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, id);
            int resultado = st.executeUpdate();
            
            if (resultado > 0) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
        } catch (SQLException e) 
        {
            Logger.getLogger(AdminDeptModel.class).error("Error al actualizar "
                    + "departamentos en función modificarDept",e);
            return false;
        }
    }
}
