package com.ugb.miprimeraaplicacion;

import android.util.Base64;

public class utilidades {
    static String url_consulta = "http://172.20.10.2:5984/yomi/_design/javier/_view/javier";
    static String url_mto = "http://172.20.10.2:5984/yomi";
    static String user = "yomi";
    static String passwd = "1234";
    static String credencialesCodificadas = Base64.encodeToString((user + ":" + passwd).getBytes(), Base64.NO_WRAP);

    public static String generarUnicoId() {
        return java.util.UUID.randomUUID().toString();
    }
}
