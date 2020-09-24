package com.example.digitacioncaja.Webserver;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class Conexion {
    public Conexion(){

    }
    public StringRequest getRequest(Response.Listener<String> responseListener,
                                    Response.ErrorListener errorListener) {

        String url = "http://desarrollo.caexlogistics.com/webservices/AT_CaexmobileV4_0_8.php?function=pobladosDigitacion&pais=GT";
        /*final HashMap<String, String> credentials = new HashMap<String, String>();
        credentials.put("Num", numero);
        Log.e("credentials", credentials + ""); // Imprime el json que se envia*/

        StringRequest request = new StringRequest(Request.Method.GET, url,
                responseListener, errorListener) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=" + getParamsEncoding();
            }

            /*public byte[] getBody() throws AuthFailureError {
                try {
                    return new JSONObject(credentials).toString().getBytes(getParamsEncoding());

                } catch (UnsupportedEncodingException e) {

                }
                return null;
            }*/

        };

        request.setRetryPolicy(new LongTimeoutAndTryRetryPolicy(LongTimeoutAndTryRetryPolicy.RETRIES_PHONE_ISP));
        return request;
    }
}
