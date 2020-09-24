package com.example.digitacioncaja;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.digitacioncaja.Webserver.AppController;
import com.example.digitacioncaja.Webserver.Conexion;
import com.example.digitacioncaja.Webserver.DefaultExclusionStrategy;
import com.example.digitacioncaja.Webserver.Poblado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener, Response.Listener,Response.ErrorListener {

    private DBHelper dbHelper;
    private int LAUNCH_SECOND_ACTIVITY ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.btnDigitacion)).setOnClickListener(this);

        /*Cargar los poblados a la base local*/
        /*dbHelper = new DBHelper(this);
        CargarPoblados();*/
    }

    private void CargarPoblados(){
        Conexion conexion = new Conexion();
        Request<?> request = conexion.getRequest(this,this);
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDigitacion){
            Intent intent = new Intent(this,DigitacionActivity.class);
            intent.putExtra("guia","WSA782245U");
            intent.putExtra("codclientecredito","CREDITO CODIGO");
            intent.putExtra("pobladoor" +
                    "igin",5);
            this.startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LAUNCH_SECOND_ACTIVITY){
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "El proceso fue cancelado", Toast.LENGTH_SHORT).show();
            }else if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "El proceso fue realizado con exito", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("ErrorResponse",error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new DefaultExclusionStrategy());
        Gson gson = builder.create();

        Poblado[] poblados = gson.fromJson(response.toString(),Poblado[].class );

        for (Poblado poblado: poblados){
            Log.d("Resputesa:",dbHelper.InsertarPoblado(Integer.parseInt(poblado.getCoddes()),poblado.getDesdes(),poblado.getCodage(),poblado.getCodpai(),poblado.getDiaser()));
        }
    }
}