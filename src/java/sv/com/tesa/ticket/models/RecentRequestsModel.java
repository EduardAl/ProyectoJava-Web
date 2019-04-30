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
import sv.com.tesa.ticket.beans.RecentRequestsBean;
import static sv.com.tesa.ticket.models.ConexionModel.conexion;

/**
 *
 * @author eduar
 */
public class RecentRequestsModel extends ConexionModel
{
    public ArrayList<RecentRequestsBean> recientes()
    {
        ArrayList<RecentRequestsBean> latestRequest = new ArrayList<>();
        try {
            String sql = "CALL sp_select_latest_requests(?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, LoginBean.getDepartamento());
            rs = st.executeQuery();
            while(rs.next())
            {
                    RecentRequestsBean latestRequestsBean = new RecentRequestsBean();
                    latestRequestsBean.setId(rs.getString("Id"));
                    latestRequestsBean.setTitulo(rs.getString("Titulo"));
                    latestRequestsBean.setCreadoPor(rs.getString("CreadoPor"));
                    if(rs.getString("UltimoCambio") == null)
                    {
                        latestRequestsBean.setUltimoCambio("No hay avances");
                    }
                    else
                    {
                        latestRequestsBean.setUltimoCambio(rs.getString("UltimoCambio"));
                    }
                    latestRequest.add(latestRequestsBean);       
            }
            return latestRequest; 
        } catch (SQLException ex) {
            Logger.getLogger(RecentRequestsModel.class).error("Error al obtener los datos",ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentRequestsModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
    }

    public RecentRequestsBean latestRequets(String tipo)
    {
        RecentRequestsBean latestRequests = new RecentRequestsBean();
        String sql = "";
        try {
            switch (tipo) {
                case "Cerrado":
                    sql = "CALL sp_select_finalized_request(?)";
                    break;
                case "Aprobar":
                    sql = "CALL sp_select_to_accept_request(?)";
                    break;
                case "Desarrollo":
                    sql = "CALL sp_select_develop_request(?)";
                    break;
                case "Rechazado":
                    sql = "CALL sp_select_refuse_request(?)";
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
                    latestRequests.setId(rs.getString("Id"));
                    latestRequests.setTitulo(rs.getString("Titulo"));
                    latestRequests.setCreadoPor(rs.getString("CreadoPor"));
                    latestRequests.setTipo(rs.getString("Tipo"));
                    latestRequests.setCreado(rs.getString("Creado"));
                    if(rs.getString("UltimoCambio") == null)
                    {
                        latestRequests.setUltimoCambio("No hay avances");
                    }
                    else
                    {
                        latestRequests.setUltimoCambio(rs.getString("UltimoCambio"));
                    }
            }
            return latestRequests;
        } catch (SQLException ex) {
            Logger.getLogger(RecentRequestsModel.class).error("Error al obtener los datos",ex);
            return null;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentRequestsModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
    }
}
