/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.LoginBean;

/**
 *
 * @author vaselinux
 */
public class LoginModel extends ConexionModel
{
    public LoginBean validar(String user, String password)
    {
        LoginBean usuario = new LoginBean();
        try {
            String sql = "CALL sp_select_user(?, ?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, user);
            st.setString(2, password);
            rs = st.executeQuery();
            while(rs.next())
            {
                    LoginBean.setId(rs.getInt("id"));
                    LoginBean.setRol(rs.getString("Rol"));
                    LoginBean.setNombre(rs.getString("Nombre"));
                    LoginBean.setCorreo(rs.getString("Correo"));
                    LoginBean.setJefe(rs.getString("Superior"));
                    LoginBean.setDepartamento(rs.getString("Departamento"));
                    usuario.setError(rs.getString("Error"));
            }
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionModel.class).error("Error al obtener los datos",ex);
            usuario.setError("No se obtuvieron datos");
            return usuario;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
    }
}
