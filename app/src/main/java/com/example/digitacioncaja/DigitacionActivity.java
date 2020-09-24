package com.example.digitacioncaja;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.digitacioncaja.Adaptador.PagerAdapater;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class DigitacionActivity extends AppCompatActivity  implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Spinner spinnerOrigen,spinnerDestino;
    private HashMap<Integer,String> mapPoblados = new HashMap<Integer, String>();
    private PagerAdapater pagerAdapter;
    private DBHelper dbHelper;
    int poblado_default;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Al momento de salir no se guardarán los datos ¿Desea salir?");
        builder.setTitle("Confirmar");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitacion);
        ((TextView)findViewById(R.id.txtGuia)).setText(getIntent().getExtras().getString("guia"));
        ((TextView)findViewById(R.id.txtCodClienteCredito)).setText(getIntent().getExtras().getString("codclientecredito"));
        poblado_default = getIntent().getExtras().getInt("pobladoorigin");


        getSupportActionBar().setTitle("Digitación de caja");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*           Iniciamos la conexion        */
        dbHelper = new DBHelper(this);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        spinnerOrigen = findViewById(R.id.spinnerOrigen);
        spinnerDestino = findViewById(R.id.spinnerDestino);
        ((Button)findViewById(R.id.btnGuardar)).setOnClickListener(this);

        pagerAdapter = new PagerAdapater(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        llenarPoblados();
    }

    private void llenarPoblados(){
        Cursor data = dbHelper.getPoblados();

        String[] spinnerArray = new String[data.getCount()+1];
        String[] spinnerArray2 = new String[data.getCount()+1];
        spinnerArray[0] = "Selecionar Origen";
        spinnerArray2[0] = "Selecionar Destino";

        int indice = 1;
        while(data.moveToNext()){
            mapPoblados.put(indice,data.getString(0));
            spinnerArray[indice] = data.getString(2);
            /*try {
                spinnerArray[indice] = data.getString(1);
            }catch (Exception e){
                spinnerArray[indice] = "No se cargo correctamente";
            }*/
            indice++;
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adapter);
        spinnerOrigen.setSelection(poblado_default);

        System.arraycopy(spinnerArray,0,spinnerArray2,0,spinnerArray.length);
        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestino.setAdapter(adapter2);

        mensajeOrigen(spinnerOrigen.getSelectedItem().toString()); //Le indicamos que poblado tiene por default
    }


    private void mensajeOrigen(String poblado){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Su origen por defecto es: \n"+poblado);
        builder.setTitle("Origen");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnGuardar){
            ContentValues values = new ContentValues();
            if (!validarCampos(values))
                return;

            Intent intent = new Intent();
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    private boolean  validarCampos(ContentValues values){
        if(spinnerOrigen.getSelectedItemPosition() == 0)
        {
            dialogValidacion("Debe seleccionar el origen","Origen");
            return false;
        }

        String idorigen = mapPoblados.get(spinnerOrigen.getSelectedItemPosition());
        values.put("idorigen",idorigen);

        if(spinnerDestino.getSelectedItemPosition() == 0)
        {
            dialogValidacion("Debe seleccionar el Destino","Destino");
            return false;
        }

        String iddestino = mapPoblados.get(spinnerOrigen.getSelectedItemPosition());
        values.put("iddestino",iddestino);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragmentRemitente = fragmentManager.getFragments().get(0);
        String nombreRemitente = ((TextInputEditText)fragmentRemitente.getView().findViewById(R.id.txtNombreRemitente)).getText().toString();
        String direccionRemitente = ((TextInputEditText)fragmentRemitente.getView().findViewById(R.id.txtDireccionRemitente)).getText().toString();
        String telefonoRemitente = ((TextInputEditText)fragmentRemitente.getView().findViewById(R.id.txtTelefonoRemitente)).getText().toString();

        if(nombreRemitente.isEmpty()){dialogValidacion("Debe ingresar el nombre del remitente","Remitente");return false;}
        if(direccionRemitente.isEmpty()){dialogValidacion("Debe ingresar la dirección del remitente","Remitente");return false;}
        if(telefonoRemitente.isEmpty()){dialogValidacion("Debe ingresar el teléfono del remitente","Remitente");return false;}

        values.put("nombre_remitente",nombreRemitente);
        values.put("direccion_remitente",direccionRemitente);
        values.put("telefono_remitente",telefonoRemitente);

        Fragment fragmentDestinatario = fragmentManager.getFragments().get(1);
        String nombreDestinatario = ((TextInputEditText)fragmentDestinatario.getView().findViewById(R.id.txtNombreDestinatario)).getText().toString();
        String direccionDestinatario = ((TextInputEditText)fragmentDestinatario.getView().findViewById(R.id.txtDireccionDestinario)).getText().toString();
        String telefonoDestinatario = ((TextInputEditText)fragmentDestinatario.getView().findViewById(R.id.txtTelefonoDestinatario)).getText().toString();

        if(nombreDestinatario.isEmpty()){dialogValidacion("Debe ingresar el nombre del destinatario","Destinatario");return false;}
        if(direccionDestinatario.isEmpty()){dialogValidacion("Debe ingresar la dirección del destinatario","Destinatario");return false;}
        if(telefonoDestinatario.isEmpty()){dialogValidacion("Debe ingresar el teléfono del destinatario","Destinatario");return false;}

        values.put("nombre_destinatario",nombreDestinatario);
        values.put("direccion_destinatario",direccionDestinatario);
        values.put("telefono_destinatario",telefonoDestinatario);

        return true;
    }

    private  void dialogValidacion(String mensaje,String titulo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setTitle(titulo);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
