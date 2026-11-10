package com.hojaldrita.util;

/**
 * Utilidad pequena para escapar texto antes de mostrarlo en las paginas JSP.
 * Asi si alguien escribe <script> en el nombre de un producto, el navegador
 * lo muestra como texto y no lo ejecuta.
 */
public class Html {

    public static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
