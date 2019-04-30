/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.com.tesa.ticket.beans.Login;

/**
 *
 * @author eduar
 */
public class User extends Conexion 
{
    public User()
    {
        
    }
    public Login validar(String user, String password)
    {
        Login usuario = new Login();
        try {
            String sql = "CALL sp_select_user('" + user + "', '" + password + "')";
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next())
            {
                    usuario.setId(rs.getInt("id"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setCorreo(rs.getString("Correo"));
                    usuario.setJefe(rs.getString("Superior"));
                    usuario.setDepartamento(null);
                    usuario.setError(rs.getString("Error"));
            }
            return usuario;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            usuario.setError("No se obtuvieron datos");
            return usuario;
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
