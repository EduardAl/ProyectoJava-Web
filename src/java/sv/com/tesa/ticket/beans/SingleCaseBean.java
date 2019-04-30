/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.beans;

/**
 *
 * @author Reyes Alexander
 */
public class SingleCaseBean {

    private String id;
    private String titulo;
    private String creadoPor;
    private String AsignadoA;
    private String limite;
    private Double avance;
    private String ultimoCambio;
    private String estado;
    private String tester;
    private String descripcion;
    private String FechaCreacion;
    private String produccion;

    
    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the tester
     */
    public String getTester() {
        return tester;
    }

    /**
     * @param tester the tester to set
     */
    public void setTester(String tester) {
        this.tester = tester;
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
     * @return the FechaCreacion
     */
    public String getFechaCreacion() {
        return FechaCreacion;
    }

    /**
     * @param FechaCreacion the FechaCreacion to set
     */
    public void setFechaCreacion(String FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    /**
     * @return the produccion
     */
    public String getProduccion() {
        return produccion;
    }

    /**
     * @param produccion the produccion to set
     */
    public void setProduccion(String produccion) {
        this.produccion = produccion;
    }

    public SingleCaseBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getAsignadoA() {
        return AsignadoA;
    }

    public void setAsignadoA(String AsignadoA) {
        this.AsignadoA = AsignadoA;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public Double getAvance() {
        return avance;
    }

    public void setAvance(Double avance) {
        this.avance = avance;
    }

    public String getUltimoCambio() {
        return ultimoCambio;
    }

    public void setUltimoCambio(String ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }
    
}
