/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author eduar
 */
public class Validaciones 
{
private static int entero;
    private static double decimal;

    public static boolean esEntero(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            return true;
        } catch (NumberFormatException a) {
            Logger.getLogger(Validaciones.class).error("Se ingresó un dato no "
                    + "entero - Validaciones esEntero",a);
            return false;
        }
    }

    public static boolean esEnteroPositivo(String cadena) {
        try {
            entero = Integer.parseInt(cadena.trim());
            return entero <= 0;
        } catch (NumberFormatException a) {
            Logger.getLogger(Validaciones.class).error("Se ingresó un dato no "
                    + "entero positivo - Validaciones esEnteroPositivo",a);
            return false;
        }
    }

    public static boolean isEmpty(String mensaje) {
        return mensaje.trim().equals("");
    }

    public static boolean esDecimal(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            return true;
        } catch (NumberFormatException a) {
            Logger.getLogger(Validaciones.class).error("Se ingresó un dato no "
                    + "decimal - Validaciones esDecimal",a);
            return false;
        }
    }

    public static boolean esDecimalPositivo(String cadena) {
        try {
            decimal = Double.parseDouble(cadena.trim());
            return decimal <= 0;
        } catch (NumberFormatException a) {
            Logger.getLogger(Validaciones.class).error("Se ingresó un dato no "
                    + "decimal positivo - Validaciones esDecimalPositivo",a);
            return false;
        }
    }
    public static boolean esCorreoElectronico(String cadena)
    {
        
        Pattern pat = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher mat = pat.matcher(cadena);
        return mat.matches();
    } 
    
    public static boolean esfechaFutura(String fecha)
    {
        try {
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = formato.parse(fecha);
            Date fechaHoy = new Date();
            
            return fechaHoy.before(parsedDate);
        } catch (Exception e) {
            return false;
        }
    }
}
