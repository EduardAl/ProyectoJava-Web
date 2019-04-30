/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.beans;
/**
 *
 * @author Edu
 */
public class DepartmentBean {

    private String id;
    private String nombreDept;

    public DepartmentBean()
    {
        
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
     * @return the nombreDept
     */
    public String getNombreDept() {
        return nombreDept;
    }

    /**
     * @param nombreDept the nombreDept to set
     */
    public void setNombreDept(String nombreDept) {
        this.nombreDept = nombreDept;
    }

    
    
    
}
