package frsf.isi.dam.delaiglesia.sendmeal;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


import java.util.ArrayList;

import frsf.isi.dam.delaiglesia.sendmeal.Auxiliares.ImageUtil;
import frsf.isi.dam.delaiglesia.sendmeal.Dao.Repository;
import frsf.isi.dam.delaiglesia.sendmeal.domain.Plato;

public class NuevoItem extends AppCompatActivity {

    private TextInputLayout til_idPlato;
    private TextInputLayout til_nombrePlato;
    private TextInputLayout til_descripcionPlato;
    private TextInputLayout til_precioPlato;
    private TextInputLayout til_caloriasPlato;

    private EditText editTextIdPlato;
    private EditText editTextNombrePlato;
    private EditText editTextDescripcionPlato;
    private EditText editTextPrecioPlato;
    private EditText editTextCaloriasPlato;

    private String idPlato;
    private String nombrePlato;
    private String descripcionPlato;
    private String precioPlato;
    private String caloriasPlato;

    private boolean validacionIdVacio;
    private boolean validacionNombreVacio;
    private boolean validacionDescripcionVacio;
    private boolean validacionPrecioVacio;
    private boolean validacionCaloriasVacio;

    private Button buttonGuardar;
    private Button buttonSacarFoto;

    ArrayList<Plato> platos;

    ImageView foto;
    Bitmap imageBitmap;

    Context context;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_item);

        context = this;

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        validacionIdVacio = false;
        validacionNombreVacio = false;
        validacionDescripcionVacio = false;
        validacionPrecioVacio = false;
        validacionCaloriasVacio = false;

        //refencias TIL's
        til_idPlato = (TextInputLayout) findViewById(R.id.til_idPlato);
        til_nombrePlato = (TextInputLayout) findViewById(R.id.til_nombrePlato);
        til_descripcionPlato = (TextInputLayout) findViewById(R.id.til_descripcionPlato);
        til_precioPlato = (TextInputLayout) findViewById(R.id.til_precioPlato);
        til_caloriasPlato = (TextInputLayout) findViewById(R.id.til_caloriasPlato);

        //referencias EditText's
        editTextIdPlato = til_idPlato.getEditText();
        editTextNombrePlato = til_nombrePlato.getEditText();
        editTextDescripcionPlato = til_descripcionPlato.getEditText();
        editTextPrecioPlato = til_precioPlato.getEditText();
        editTextCaloriasPlato = til_caloriasPlato.getEditText();

        foto = findViewById(R.id.imageViewFoto);


        //acción a realizar al llegar un plato para ser editado desde la lista de platos
        //obtenemos la fila a editar
        final Integer fila = (Integer) getIntent().getSerializableExtra("fila");
        //seteamos los valores en pantalla solo si venimos desde la lista
        if(fila!=null){
            platos = (ArrayList<Plato>) getIntent().getSerializableExtra("listaPlatos");
            Plato plato = platos.get(fila);
            editTextIdPlato.setText(plato.getId().toString());
            editTextNombrePlato.setText(plato.getTitulo());
            editTextDescripcionPlato.setText(plato.getDescripcion());
            editTextPrecioPlato.setText(plato.getPrecio().toString());
            editTextCaloriasPlato.setText(plato.getCalorias().toString());
        }

        //acción a realizar al llegar un plato desde una notificación de oferta
        Plato plato = (Plato) getIntent().getSerializableExtra("plato");
        Intent intent = getIntent();

        //seteamos los valores en pantalla solo si venimos desde la notificacion
        if(plato!=null){
            editTextIdPlato.setText(plato.getId().toString());
            editTextNombrePlato.setText(plato.getTitulo());
            editTextDescripcionPlato.setText(plato.getDescripcion());
            editTextPrecioPlato.setText(plato.getPrecio().toString());
            editTextCaloriasPlato.setText(plato.getCalorias().toString());

            //deshabilitamos todos los campos editables
            editTextIdPlato.setEnabled(false);
            editTextNombrePlato.setEnabled(false);
            editTextDescripcionPlato.setEnabled(false);
            editTextPrecioPlato.setEnabled(false);
            editTextCaloriasPlato.setEnabled(false);
        }

        editTextIdPlato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_idPlato.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editTextNombrePlato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_nombrePlato.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextDescripcionPlato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_descripcionPlato.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPrecioPlato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_precioPlato.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextCaloriasPlato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                til_caloriasPlato.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Botón Guardar
        buttonGuardar = (Button) findViewById(R.id.buttonGuardarNuevoItem);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDatos();
                if(validacionIdVacio&&validacionNombreVacio&&validacionDescripcionVacio&&validacionPrecioVacio&&validacionCaloriasVacio){

                    //sino sacó una foto seteamos una imagen por default

                    if (imageBitmap==null) {
                        foto.buildDrawingCache();
                        imageBitmap = foto.getDrawingCache();
                    }
                    //convertimos la foto a String para guardar en el API REST
                    String fotoBase64String = ImageUtil.convert(imageBitmap);
                    //Creamos el plato
                    Plato plato = new Plato(Integer.valueOf(idPlato), nombrePlato, descripcionPlato, Double.valueOf(precioPlato), Integer.valueOf(caloriasPlato), false, fotoBase64String);

                    Intent intentResultado = new Intent();
                    //verificamos si se llamo la actividad desde la lista comprobando si fila!=null en el getIntent
                    if ( getIntent().getSerializableExtra("fila")!=null){

                        Repository.getInstance().actualizarPlato(plato, miHandler);

                    }
                    //si se llamo desde Home:
                    else{
                        Repository.getInstance().crearPlato(plato, miHandler);
                        finish();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Ingreso de datos incorrectos o algún campo vacío", Toast.LENGTH_SHORT).show();
            }
        });

        //Botón Sacar foto
        //teniendo en cuenta que solo necesitamos mostrar una miniatura del plato, solo trabajaremos con el thumbnail
        buttonSacarFoto = findViewById(R.id.buttonAgregarFoto);
        buttonSacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

    }

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            Log.d("APP_2","Vuelve al handler"+msg.arg1);

            switch (msg.arg1 ){
                case Repository._UPDATE_PLATO:
                    Intent i = new Intent(NuevoItem.this,ListaItems.class);
                    startActivity(i);
                    break;
            }
        }
    };

    public void validarDatos() {
        //setear variables
        idPlato = editTextIdPlato.getText().toString();
        nombrePlato = editTextNombrePlato.getText().toString();
        descripcionPlato = editTextDescripcionPlato.getText().toString();
        precioPlato = editTextPrecioPlato.getText().toString();
        caloriasPlato = editTextCaloriasPlato.getText().toString();

        //verificar campos vacios
        if (idPlato.equals("")){
            til_idPlato.setError("Completá este dato");
        }
        else {
            validacionIdVacio = true;
        }

        if (nombrePlato.equals("")){
            til_nombrePlato.setError("Completá este dato");
        }
        else {
            validacionNombreVacio = true;
        }

        if (descripcionPlato.equals("")){
            til_descripcionPlato.setError("Completá este dato");
        }
        else {
            validacionDescripcionVacio = true;
        }

        if (precioPlato.equals("")){
            til_precioPlato.setError("Completá este dato");
        }
        else {
            validacionPrecioVacio = true;
        }

        if (caloriasPlato.equals("")){
            til_caloriasPlato.setError("Completá este dato");
        }
        else{
            validacionCaloriasVacio = true;
        }

    }

    //flecha volver en la actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
// Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }


}