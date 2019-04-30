/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.CaseBean;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.SingleCaseBean;
import static sv.com.tesa.ticket.models.ConexionModel.conexion;
import sv.com.tesa.ticket.utils.Utilidades;

/**
 *
 * @author eduar
 */
public class CasesModel extends ConexionModel{

    private JTable tabla;
 
    public JTable listarCasos(){
        try{
            String sql = "SELECT c.id AS IdCaso, r.title AS Caso, c.descrip AS Descripcion, c.percent AS porcentaje, \n" +
                "concat(t.fname,' ',t.lname) AS Tester, concat(a.fname, ' ', a.lname) AS Asignado, \n" +
                "cs.cs_name AS Estado, c.deadline AS Finalizacion \n" +
                "from cases c INNER JOIN requests r ON c.request = r.id \n" +
                "INNER JOIN employees t ON c.tester = t.id \n" +
                "INNER JOIN employees a ON c.assigned_to = a.id \n" +
                "INNER JOIN case_status cs ON c.case_status = cs.id \n" +
                "where r.department = (select id from departments where dname = 'Departamento de Sistemas')\n" +
                "UNION ALL\n" +
                "SELECT c.id AS IdCaso, r.title AS Caso, c.descrip AS Descripcion, c.percent AS porcentaje, \n" +
                "null AS Tester, concat(a.fname, ' ', a.lname) AS Asignado, \n" +
                "cs.cs_name AS Estado, c.deadline AS Finalizacion \n" +
                "from cases c INNER JOIN requests r ON c.request = r.id \n" +
                "INNER JOIN employees a ON c.assigned_to = a.id \n" +
                "INNER JOIN case_status cs ON c.case_status = cs.id \n" +
                "where r.department = (select id from departments where dname =  '" + LoginBean.getDepartamento() + "') and tester is null";
            this.conectar();
            st = conexion.prepareStatement(sql);
            boolean resultado = st.execute();
            
            if(resultado){
                rs = st.getResultSet();
                String[] col = {"IdCaso", "Caso", "Descripcion", "Porcentaje", "Tester", "Asignado", "Estado", "Fecha_Finalizacion"};
                tabla = Utilidades.cargarTabla(col, rs);
            }
            else{
                tabla = null;
            }
            return tabla;
        }catch(SQLException ex){
            Logger.getLogger(CasesModel.class).error("Error al listar "
                    + "casos en CasesModel función listarCasos",ex);
            return null;
        }
        finally    
        {
                    
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(CasesModel.class.getName()).log(null, ex);
            }
        }
    }
    
 @SuppressWarnings("empty-statement")
   public SingleCaseBean listarCaso(SingleCaseBean beanCase){
        try{
            String sql = "CALL sp_select_single_case(?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, beanCase.getId());
            rs = st.executeQuery();
            SingleCaseBean peticionIndividual = new SingleCaseBean();
            while(rs.next()){
                peticionIndividual.setId(rs.getString("IdCaso"));
                peticionIndividual.setTitulo(rs.getString("Caso"));
                peticionIndividual.setAsignadoA(rs.getString("Asignado"));
                peticionIndividual.setEstado(rs.getString("Estado"));;
                peticionIndividual.setDescripcion(rs.getString("Descripcion"));
                peticionIndividual.setAvance(rs.getDouble("porcentaje"));
                peticionIndividual.setTester(rs.getString("Tester"));
                peticionIndividual.setFechaCreacion(rs.getString("Creado"));
                peticionIndividual.setUltimoCambio(rs.getString("Modificacion"));
                peticionIndividual.setCreadoPor(rs.getString("CreadoPor"));
                peticionIndividual.setLimite(rs.getString("FechaLimite"));
                peticionIndividual.setProduccion(rs.getString("AProduccion"));
            }
            this.desconectar();
            return peticionIndividual;
        }catch(SQLException ex){
            Logger.getLogger(CasesModel.class).error("Error al listar "
                    + "caso individual en CasesModel función listarCaso",ex);
            return null;
        }
    }
   
   public boolean modificarCasoJefeDesarrollo(SingleCaseBean beanCase){
       try{
           JOptionPane.showMessageDialog(null,"Entre al modelo");
           String sql = "call sp_update_cases(?,?,?)";
           this.conectar();
           st = conexion.prepareCall(sql);
           st.setInt(1, Integer.parseInt(beanCase.getAsignadoA()));
           st.setString(2, beanCase.getDescripcion());
           st.setString(3, beanCase.getId());
           int resultado = st.executeUpdate();            
            if (resultado > 0) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
       }catch(Exception ex){
           Logger.getLogger(AdminDeptModel.class).error("Error al actualizar "
                    + "casos en función modificarCasoJefeDesarrollo",ex);
            return false;
       }
   }
   
   public boolean modificarCasoJefeAreaFuncional(SingleCaseBean beanCase){
       try{
           JOptionPane.showMessageDialog(null, "entre al model");
           String sql = "call sp_update_cases_JAF(?,?)";
           this.conectar();
           st = conexion.prepareCall(sql);
           st.setInt(1, Integer.parseInt(beanCase.getTester()));
           st.setString(2, beanCase.getId());
           int resultado = st.executeUpdate();            
            if (resultado > 0) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
       }catch(Exception ex){
           Logger.getLogger(AdminDeptModel.class).error("Error al actualizar "
                    + "casos en función modificarCasoJefeAreaFuncional",ex);
            return false;
       }
   }
   
   public HashMap<Integer,String> listarEmpleadosACargo()
    {
        HashMap<Integer, String> map = new HashMap<>();
        try {
            String sql = "call sp_select_employees_chief(?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setInt(1, LoginBean.getId());
            rs = st.executeQuery();
            
            while(rs.next())
            {
                map.put(rs.getInt(1), rs.getString(2));
            }
            this.desconectar();
            return map;
        } catch (SQLException e) {
            Logger.getLogger(CasesModel.class).error("Error al listar "
                    + "empleados a cargo en CasesModel función listarEmpleadosACargo",e);
             return null;
        }
        finally
        {
         try {
             this.desconectar();
         } catch (SQLException ex) {
             Logger.getLogger(CasesModel.class.getName()).log(null, ex);
         }
        }
    }
    
    public boolean ingresarCaso(CaseBean caso)
    {
        try {
            String sql = "call sp_insert_new_case(?,?,?,?,?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, caso.getDepartamento());
            st.setString(2, caso.getId());
            st.setInt(3, caso.getIdSolicitud());
            st.setInt(4, caso.getEmpleadoAsignado());
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = formato.parse(caso.getFechaLimite());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            st.setTimestamp(5, timestamp);
            st.setString(6, caso.getDescripcion());
            
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
            
        } catch (SQLException | ParseException e) {
            Logger.getLogger(CasesModel.class).error("Error al ingresar "
                    + "un caso en CasesModel función IngresarCaso",e);
            return false;
        }
        finally{
            
         try {
             this.desconectar();
         } catch (SQLException ex) {
             Logger.getLogger(CasesModel.class.getName()).log(null, ex);
         }
        }
    }
    public boolean reOpenCase(String caseId)
    {
     try {
         String sql = "CALL sp_re_open_case(?)";
         this.conectar();
         st = conexion.prepareStatement(sql);
         st.setString(1, caseId);
         return st.executeUpdate() > 0;
     }
     catch (SQLException ex) {
         Logger.getLogger(CasesModel.class.getName()).log( null, ex);
     }
     finally{
         try {
             this.desconectar();
         } catch (SQLException ex) {
             Logger.getLogger(CasesModel.class.getName()).log(null, ex);
         }
     }
     return false;
    }
}
