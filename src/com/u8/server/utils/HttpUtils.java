package com.u8.server.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ant on 2015/2/28.
 */
    public class HttpUtils{

    //解析URL格式参数为Map格式
    public static Map<String,String> parseUrlParams(String param){

        Map<String, String> data = new HashMap<String, String>();
        if(StringUtils.isEmpty(param)){

            return data;
        }

        if(param.startsWith("?")){
            param = param.substring(1);
        }

        String[] splits = param.split("&");
        for(String s : splits){
            String[] p = s.split("=");
            if(p.length == 2){
                data.put(p[0], p[1]);
            }
        }

        return data;

    }

    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));

            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
