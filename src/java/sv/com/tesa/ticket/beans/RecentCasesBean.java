/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.beans;

/**
 *
 * @author eduar
 */
public class RecentCasesBean 
{
    private String id;
    private String titulo;
    private String creadoPor;
    private String AsignadoA;
    private String limite;
    private Double avance;
    private String ultimoCambio;
    

    public RecentCasesBean() {
    }

    public RecentCasesBean(String id) {
        this.id=id;
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
