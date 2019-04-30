/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.beans;

import java.sql.Timestamp;

/**
 *
 * @author Edu
 */
public class CaseBean {


    private String id;
    private int idSolicitud;
    private int empleadoAsignado;
    private int estadoCaso;
    private String fechaLimite;
    private String descripcion;
    private double porcentajeAvance;
    private int tester;
    private String fechaCreacion;
    private String fechaActualizacion;
    private String fechaImplementacion;
    private String departamento;
    
    public CaseBean()
    {
        
    }
    
    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the idSolicitud
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * @param idSolicitud the idSolicitud to set
     */
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * @return the empleadoAsignado
     */
    public int getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    /**
     * @param empleadoAsignado the empleadoAsignado to set
     */
    public void setEmpleadoAsignado(int empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    /**
     * @return the estadoCaso
     */
    public int getEstadoCaso() {
        return estadoCaso;
    }

    /**
     * @param estadoCaso the estadoCaso to set
     */
    public void setEstadoCaso(int estadoCaso) {
        this.estadoCaso = estadoCaso;
    }

    /**
     * @return the fechaLimite
     */
    public String getFechaLimite() {
        return fechaLimite;
    }

    /**
     * @param fechaLimite the fechaLimite to set
     */
    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the porcentajeAvance
     */
    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }

    /**
     * @param porcentajeAvance the porcentajeAvance to set
     */
    public void setPorcentajeAvance(double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    /**
     * @return the tester
     */
    public int getTester() {
        return tester;
    }

    /**
     * @param tester the tester to set
     */
    public void setTester(int tester) {
        this.tester = tester;
    }

    /**
     * @return the fechaCreacion
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaActualizacion
     */
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the fechaImplementacion
     */
    public String getFechaImplementacion() {
        return fechaImplementacion;
    }

    /**
     * @param fechaImplementacion the fechaImplementacion to set
     */
    public void setFechaImplementacion(String fechaImplementacion) {
        this.fechaImplementacion = fechaImplementacion;
    }
    
}
