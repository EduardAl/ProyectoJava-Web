/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.ArrayList;
import sv.com.tesa.ticket.beans.CaseBean;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.SingleCaseBean;

/**
 *
 * @author eduar
 */
public class TesterModel extends Conexion {
    
    public ArrayList<SingleCaseBean> listarCasosTester()
    {
        try {
            ArrayList<SingleCaseBean> lista = new ArrayList();
            String sql = "call sp_select_waiting_case(?,?)";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, LoginBean.getDepartamento());
            st.setInt(2, LoginBean.getId());
            rs = st.executeQuery();
            while(rs.next())
            {
                SingleCaseBean obj = new SingleCaseBean();
                obj.setId(rs.getString(1));
                obj.setTitulo(rs.getString(2));
                obj.setCreadoPor(rs.getString(3));
                obj.setAsignadoA(rs.getString(4));
                obj.setLimite(rs.getString(5));
                obj.setAvance(rs.getDouble(6));
                obj.setUltimoCambio(rs.getString(7));
                obj.setEstado(rs.getString(8));
                lista.add(obj);
            }
            return lista;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{
            try {
                this.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String regresarEstado(CaseBean caso) {
        try {
            String sql = "select case_status.cs_name from case_status \n" +
                            "inner join cases on case_status.id = cases.case_status\n" +
                            "where cases.id = ?";
            this.conectar();
            st = conexion.prepareCall(sql);
            st.setString(1, caso.getId());

            rs = st.executeQuery();

            String res = null;
            while (rs.next()) {
                res = rs.getString(1);
            }
            this.desconectar();
            return res;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean aceptarCaso(CaseBean caso)
    {
        try {
            String sql = "call ticketstesa.sp_acept_case(?)";
            st = conexion.prepareCall(sql);
            st.setString(1, caso.getId());
            int resultado = st.executeUpdate();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally{
            try {
                this.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean denegarCaso(CaseBean caso)
    {
        try {
            String sql = "call ticketstesa.sp_deny_case(?,?)";
            st = conexion.prepareCall(sql);
            st.setString(1, caso.getId());
            st.setString(2, caso.getComentario());
            int resultado = st.executeUpdate();
            return resultado > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally{
            try {
                this.desconectar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
