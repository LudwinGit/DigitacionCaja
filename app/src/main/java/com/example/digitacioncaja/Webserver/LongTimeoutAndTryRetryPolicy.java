package com.example.digitacioncaja.Webserver;
import com.android.volley.DefaultRetryPolicy;
public class LongTimeoutAndTryRetryPolicy extends DefaultRetryPolicy{
    public static final int TIMEOUT_MS = 2000;
    public static final int RETRIES_PHONE_ISP = 3;

    public LongTimeoutAndTryRetryPolicy(int retries) {
        super(TIMEOUT_MS, retries, DEFAULT_BACKOFF_MULT);
    }
}
