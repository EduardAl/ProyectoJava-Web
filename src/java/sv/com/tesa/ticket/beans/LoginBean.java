/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.beans;

/**
 *
 * @author vaselinux
 */
public class LoginBean {
    private static int id;
    private static String rol;
    private static String nombre;
    private static String correo;
    private static String jefe;
    private static String departamento;
    private String error;
    public LoginBean()
    {
    }
    public static void LoginBeanToNull()
    {
        id = 0;
        rol = null;
        nombre = null;
        correo = null;
        jefe = null;
        departamento = null;
    }
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        LoginBean.id = id;
    }
    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        LoginBean.rol = rol;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        LoginBean.nombre = nombre;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        LoginBean.correo = correo;
    }

    public static String getJefe() {
        return jefe;
    }

    public static void setJefe(String jefe) {
        LoginBean.jefe = jefe;
    }

    public static String getDepartamento() {
        return departamento;
    }

    public static void setDepartamento(String departamento) {
        LoginBean.departamento = departamento;
    }

    public String getError() {
        return error;
    }

    public void setError(String Error) {
        this.error = Error;
    }
}
