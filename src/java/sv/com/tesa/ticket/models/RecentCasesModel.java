/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.RecentCasesBean;
import static sv.com.tesa.ticket.models.ConexionModel.conexion;

/**
 *
 * @author eduar
 */
public class RecentCasesModel extends ConexionModel
{
    public ArrayList<RecentCasesBean> recientes()
    {
        ArrayList<RecentCasesBean> latestCases = new ArrayList<>();
        try {
            String sql = "CALL sp_select_latest_cases(?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, LoginBean.getDepartamento());
            rs = st.executeQuery();
            while(rs.next())
            {
                    RecentCasesBean latestCasesBean = new RecentCasesBean();
                    latestCasesBean.setId(rs.getString("Id"));
                    latestCasesBean.setTitulo(rs.getString("Titulo"));
                    latestCasesBean.setCreadoPor(rs.getString("CreadoPor"));
                    latestCasesBean.setAsignadoA(rs.getString("Asignado"));                    
                    latestCasesBean.setLimite(rs.getString("Limite"));
                    latestCasesBean.setAvance(rs.getDouble("Avance"));
                    if(rs.getString("UltimoCambio") == null)
                    {
                        latestCasesBean.setUltimoCambio("No hay avances");
                    }
                    else
                    {
                        latestCasesBean.setUltimoCambio(rs.getString("UltimoCambio"));
                    }
                    latestCases.add(latestCasesBean);       
            }
            return latestCases; 
        } catch (SQLException ex) {
            Logger.getLogger(RecentCasesModel.class).error("Error al obtener los datos",ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentCasesModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
    }

    public RecentCasesBean otherCases(String tipo)
    {
        RecentCasesBean latestCases = new RecentCasesBean();
        String sql = "";
        try {
            switch (tipo) {
                case "Finalizado":
                    sql = "CALL sp_select_finalized_case(?)";
                    break;
                case "Aprobar":
                    sql = "CALL sp_select_to_accept_case(?)";
                    break;
                case "Vencido":
                    sql = "CALL sp_select_death_case(?)";
                    break;
                case "Devuelto":
                    sql = "CALL sp_select_back_case(?)";
                    break;
                default:
                    throw new AssertionError();
            }
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, LoginBean.getDepartamento());
            rs = st.executeQuery();
            while(rs.next())
            {
                    latestCases.setId(rs.getString("Id"));
                    latestCases.setTitulo(rs.getString("Titulo"));
                    latestCases.setCreadoPor(rs.getString("CreadoPor"));
                    latestCases.setAsignadoA(rs.getString("Asignado"));
                    latestCases.setLimite(rs.getString("Limite"));
                    latestCases.setAvance(rs.getDouble("Avance"));
                    if(rs.getString("UltimoCambio") == null)
                    {
                        latestCases.setUltimoCambio("No hay avances");
                    }
                    else
                    {
                        latestCases.setUltimoCambio(rs.getString("UltimoCambio"));
                    }
            }
            return latestCases;
        } catch (SQLException ex) {
            Logger.getLogger(RecentCasesModel.class).error("Error al obtener los datos",ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentCasesModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
    }
}
