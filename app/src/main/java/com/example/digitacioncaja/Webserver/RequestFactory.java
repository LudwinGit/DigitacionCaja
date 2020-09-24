package com.example.digitacioncaja.Webserver;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public interface RequestFactory {
    public StringRequest getRequest(Response.Listener<String> responseListener,
                                    Response.ErrorListener errorListener);
}
