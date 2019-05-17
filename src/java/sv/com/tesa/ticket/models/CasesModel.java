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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.CaseBean;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.SingleCaseBean;
import sv.com.tesa.ticket.utils.Utilidades;

/**
 *
 * @author eduar
 */
public class CasesModel extends Conexion{
 
    public ArrayList<SingleCaseBean> listarCasos(){
        try{
            ArrayList <SingleCaseBean> lista = new ArrayList<>();
            String sql = "select cases.id, requests.title, cases.descrip, cases.percent, \n" +
                            "concat(employees.fname, ' ', employees.lname) as 'asignado',\n" +
                            "case_status.cs_name, cases.deadline, cases.file_dir from cases \n" +
                            "inner join requests on cases.request = requests.id\n" +
                            "inner join employees on cases.assigned_to = employees.id\n" +
                            "inner join case_status on cases.case_status = case_status.id \n" +
                            "where requests.department in (select departments.id from departments where departments.dname = ?);";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, LoginBean.getDepartamento());
            rs = st.executeQuery();
            while(rs.next()){
                SingleCaseBean obj = new SingleCaseBean();
                obj.setId(rs.getString("id"));
                System.out.println(obj.getId());
                obj.setTitulo(rs.getString("title"));
                obj.setDescripcion(rs.getString("descrip"));
                obj.setAvance(rs.getDouble("percent"));
                obj.setAsignadoA(rs.getString("asignado"));
                obj.setEstado(rs.getString("cs_name"));
                obj.setLimite(rs.getString("deadline"));
                obj.setFileDir(rs.getString("file_dir"));
                lista.add(obj);
            }
            return lista;
        }catch(SQLException ex){
            Logger.getLogger(CasesModel.class).error("Error al listar "
                    + "casos en CasesModel función listarCasos",ex);
            ex.printStackTrace();
            return null;
        }
        finally    
        {
                    
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(CasesModel.class.getName()).log(null, ex);
                ex.printStackTrace();
            }
        }
    }
    
    
    public ArrayList<SingleCaseBean> listarCasosPorDesarrollador(){
        try{
            ArrayList <SingleCaseBean> lista = new ArrayList<>();
            String sql = "select cases.id, requests.title, cases.descrip, cases.percent, \n" +
                            "concat(employees.fname, ' ', employees.lname) as 'asignado',\n" +
                            "case_status.cs_name, cases.deadline, cases.file_dir from cases \n" +
                            "inner join requests on cases.request = requests.id\n" +
                            "inner join employees on cases.assigned_to = employees.id\n" +
                            "inner join case_status on cases.case_status = case_status.id \n" +
                            "where requests.department in (select departments.id from departments where departments.dname = ?)" +
                            " and assigned_to = ? ;";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, LoginBean.getDepartamento());
            st.setInt(2, LoginBean.getId());
            rs = st.executeQuery();
            while(rs.next()){
                SingleCaseBean obj = new SingleCaseBean();
                obj.setId(rs.getString("id"));
                System.out.println(obj.getId());
                obj.setTitulo(rs.getString("title"));
                obj.setDescripcion(rs.getString("descrip"));
                obj.setAvance(rs.getDouble("percent"));
                obj.setAsignadoA(rs.getString("asignado"));
                obj.setEstado(rs.getString("cs_name"));
                obj.setLimite(rs.getString("deadline"));
                obj.setFileDir(rs.getString("file_dir"));
                lista.add(obj);
            }
            return lista;
        }catch(SQLException ex){
            Logger.getLogger(CasesModel.class).error("Error al listar "
                    + "casos en CasesModel función listarCasos",ex);
            ex.printStackTrace();
            return null;
        }
        finally    
        {
                    
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(CasesModel.class.getName()).log(null, ex);
                ex.printStackTrace();
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
            String sql = "call sp_insert_new_case(?,?,?,?,?,?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, caso.getDepartamento());
            st.setString(2, caso.getId());
            st.setInt(3, caso.getIdSolicitud());
            st.setInt(4, caso.getEmpleadoAsignado());
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = formato.parse(caso.getFechaLimite());
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            st.setTimestamp(5, timestamp);
            st.setString(6, caso.getDescripcion());
            st.setString(7, caso.getFileDir());
            
            int resultado = st.executeUpdate();
            this.desconectar();
            return resultado > 0;
            
        } catch (SQLException | ParseException e) {
           // Logger.getLogger(CasesModel.class).error("Error al ingresar "
            //        + "un caso en CasesModel función IngresarCaso",e);
            e.printStackTrace();
            return false;
        }
        finally{
            
         try {
             this.desconectar();
         } catch (SQLException ex) {
             //Logger.getLogger(CasesModel.class.getName()).log(null, ex);
             ex.printStackTrace();
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
