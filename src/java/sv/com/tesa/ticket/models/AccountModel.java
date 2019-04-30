/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.LoginBean;
import static sv.com.tesa.ticket.models.ConexionModel.conexion;

/**
 *
 * @author Rodrigo
 */
public class AccountModel extends ConexionModel{
    public boolean actualizarCuenta(String currentPass, String newPass)
    {
        try {
            String sql = "CALL sp_update_employeePass(?, ?, ?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, LoginBean.getId());
            st.setString(2, newPass);            
            st.setString(3, currentPass);

            int i = st.executeUpdate();
            return i > 0;
        } catch (SQLException ex) {
            Logger.getLogger(AccountModel.class).error("Error al obtener los datos",ex);
            return false;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(AccountModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }

    }
}
