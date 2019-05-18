/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.BinnaclesBean;
import static sv.com.tesa.ticket.models.Conexion.conexion;
import sv.com.tesa.ticket.utils.Utilidades;

/**
 *
 * @author vaselinux
 */
public class BinnaclesModel extends Conexion{
    
    public List<BinnaclesBean> getBinnacles(String caseId)
    {
        try {
            
            List<BinnaclesBean> list = new ArrayList<>();
            //String sql = "CALL  sp_select_binnacle_cases(?)";
            String sql = "CALL  sp_select_binnacle_cases(?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, caseId);
            rs = st.executeQuery();
            rs = st.getResultSet();
            
            while(rs.next()){
                BinnaclesBean binnaclesBean = new BinnaclesBean();
                
                binnaclesBean.setId(rs.getInt("id"));
                binnaclesBean.setCaseId(caseId);
                binnaclesBean.setCommentary(rs.getString("comentario"));
                binnaclesBean.setPercent(rs.getDouble("percent"));
                binnaclesBean.setCreatedAt(rs.getString("created_at"));
                
                list.add(binnaclesBean);
                
                System.out.println(binnaclesBean.getCommentary());
                
            }
            
            return list;
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
    
    public boolean insertBinnacle(BinnaclesBean binnaclesBean){
        Integer rows = 0;
        try {
            String sql = "CALL  sp_insert_binnacle_cases(?, ?, ?)";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, binnaclesBean.getCaseId());
            st.setDouble(2, binnaclesBean.getPercent());
            st.setString(3, binnaclesBean.getCommentary());

            rows= st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RecentCasesModel.class).error("Error al obtener los datos",ex);
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentCasesModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
        return rows > 0;
    
    }

    public boolean removeBinnacle(int id) {
         Integer rows = 0;
        try {
            String sql = "delete from binnacle where id = ?";
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rows= st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RecentCasesModel.class).error("Error al obtener los datos",ex);
        }
        finally
        {
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(RecentCasesModel.class).error("Error al cerrar la conexiòn.",ex);
            }
        }
        return rows > 0;
    }
    
}
