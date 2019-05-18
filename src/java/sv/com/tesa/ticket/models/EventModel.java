/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import sv.com.tesa.ticket.beans.EventBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Rodrigo
 */
public class EventModel extends Conexion {

    public ArrayList<EventBean> listarEvents(String rol, String id) {
        try {

            ArrayList<EventBean> lista = new ArrayList<>();
            String sql = "";
            if (rol == "2") {
                sql = "CALL sp_select_events_functional_boss(?)";
                this.conectar();
                st = conexion.prepareStatement(sql);
                st.setString(1, id);
                rs = st.executeQuery();
                while (rs.next()) {
                    EventBean eventBean = new EventBean();
                    eventBean.setDescription(rs.getString("descrip"));
                    eventBean.setCreationDate(rs.getString("created_at"));
                    eventBean.setPercent(rs.getString("percent"));
                    eventBean.setEstado(rs.getString("STATUS"));
                    lista.add(eventBean);
                }
                return lista;
            } else {
                sql = "CALL sp_select_events_dev_boss()";
                this.conectar();
                st = conexion.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    EventBean eventBean = new EventBean();
                    eventBean.setDescription(rs.getString("descrip"));
                    eventBean.setCreationDate(rs.getString("created_at"));
                    eventBean.setPercent(rs.getString("percent"));
                    eventBean.setEstado(rs.getString("STATUS"));
                    lista.add(eventBean);
                }
                return lista;
            }

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UsersModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(AdminDeptModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
