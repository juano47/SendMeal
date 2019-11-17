package frsf.isi.dam.delaiglesia.sendmeal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Registrarse extends AppCompatActivity {

    private TextInputLayout tilNombre;
    private TextInputLayout tilMail;
    private TextInputLayout tilClave;
    private TextInputLayout tilRepetirClave;
    private TextInputLayout tilTarjetaNumero;
    private TextInputLayout tilTarjetaFecha;
    private TextInputLayout tilTarjetaCcv;
    private TextInputLayout tilAliasCbu;
    private TextInputLayout tilCbu;

    private EditText txtNombre;
    private EditText txtMail;
    private EditText txtClave;
    private EditText txtRepetirClave;
    private EditText txtTarjetaNumero;
    private EditText txtTarjetaFecha;
    private EditText txtTarjetaCcv;
    private EditText txtAliasCbu;
    private EditText txtCbu;

    private TextView textViewCreditoInicial;

    private RadioGroup radioGroupTipoCuenta;
    private SeekBar seekBarCreditoInicial;
    private Switch switchEsVendedor;
    private CheckBox checkBoxTerminos;
    private Button botonRegistrarse;

    private String nombre;
    private String mail;
    private String clave;
    private String repetirClave;
    private String tarjetaNumero;
    private String tarjetaFecha;
    private String tarjetaCcv;
    private String aliasCbu;
    private String cbu;

    private int valorMinimoSeekBarCreditoInicial;
    private int mesTarjeta;
    private int anioTarjeta;

    boolean validacionNombreVacio;
    boolean validacionMailVacio;
    boolean validacionClaveVacio;
    boolean validacionRepetirClaveVacio;
    boolean validacionTarjetaNumeroVacio;
    boolean validacionTarjetaCcvVacio;
    boolean validacionTipoCuentaSeleccionada;
    boolean validacionClaveCorrecta;
    boolean validacionRepetirClaveCorrecto;
    boolean validacionSintaxisMailCorrecta;
    boolean validacionSintaxisFechaCorrecta;
    boolean validacionSintaxisCvvCorrecta;
    boolean validacionTarjetaVencimiento;
    boolean validacionAliasCbuVacio;
    boolean validacionCbuVacio;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //define la flecha para volver en la actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //por default ocultamos los datos de cbu para el vendedor. Lo podriamos haber hecho en el xml pero se rompía la vista de edición.
        //obtengo la referencia al layout que contiene los datos a mostrar/ocultar
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayoutDatosVendedor);
        constraintLayout.setVisibility(View.GONE);

        //necesario para setear el valor inicial del seekbar
        valorMinimoSeekBarCreditoInicial=100;

        validacionTarjetaCcvVacio = false;
        validacionTipoCuentaSeleccionada = false;
        validacionRepetirClaveCorrecto = false;
        validacionSintaxisMailCorrecta = false;
        validacionSintaxisFechaCorrecta = false;
        validacionSintaxisCvvCorrecta = false;
        validacionTarjetaVencimiento =false;
        //corresponde true porque incialmente estos campos están ocultos y no se necesita validación
        validacionAliasCbuVacio = true;
        validacionCbuVacio = true;


        //Referencias TILs
        tilNombre = (TextInputLayout)findViewById(R.id.til_nombre);
        tilMail = (TextInputLayout) findViewById(R.id.til_email);
        tilClave = (TextInputLayout) findViewById(R.id.til_clave);
        tilRepetirClave = (TextInputLayout) findViewById(R.id.til_repetir_clave);
        tilTarjetaNumero = (TextInputLayout) findViewById(R.id.til_tarjeta_numero);
        tilTarjetaFecha = (TextInputLayout) findViewById(R.id.til_tarjeta_fecha);
        tilTarjetaCcv = (TextInputLayout) findViewById(R.id.til_tarjeta_ccv);
        tilAliasCbu = (TextInputLayout) findViewById(R.id.textInputLayoutAliasCbu);
        tilCbu = (TextInputLayout) findViewById(R.id.textInputLayoutCbu);

        //Referencias EditTexts
        txtNombre = tilNombre.getEditText();
        txtMail = tilMail.getEditText();
        txtClave = tilClave.getEditText();
        txtRepetirClave = tilRepetirClave.getEditText();
        txtTarjetaNumero = tilTarjetaNumero.getEditText();
        txtTarjetaFecha = tilTarjetaFecha.getEditText();
        txtTarjetaCcv = tilTarjetaCcv.getEditText();
        txtAliasCbu = tilAliasCbu.getEditText();
        txtCbu = tilCbu.getEditText();

        //TextView que muestra el credito inicial
        textViewCreditoInicial = (TextView) findViewById(R.id.textViewValorCreditoInicial);

        //RadioGroup Tipo de cuenta
        radioGroupTipoCuenta = (RadioGroup) findViewById(R.id.radioGroupTipoCuenta);
        //SeekBar Credito inicial
        seekBarCreditoInicial = (SeekBar) findViewById(R.id.seekBarCreditoInicial);
        ////Switch es vendedor
        switchEsVendedor = (Switch) findViewById(R.id.switchEsVendedor);
        //Checkbox Aceptar terminos y condiciones
        checkBoxTerminos = (CheckBox) findViewById(R.id.checkBoxTerminos);
        //Botón registrarse
        botonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);

        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override //método que se ejecuta mientras se está escribiendo
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilNombre.setError(null);
                validacionNombreVacio = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilMail.setError(null);
                validacionMailVacio = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtClave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilClave.setError(null);
                validacionClaveVacio = false;
                validacionClaveCorrecta = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtRepetirClave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilRepetirClave.setError(null);
                validacionRepetirClaveVacio = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtTarjetaNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                tilTarjetaNumero.setError(null);
                validacionTarjetaNumeroVacio = false;

                String working = s.toString();
                //consume caracteres si se escriben más de 16
                if (working.length()>16)
                    txtTarjetaNumero.setText(working.substring(16));
                else
                    tilTarjetaNumero.setError(null);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        txtTarjetaFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                boolean isValid = true;
                if (working.length()==2 && before ==0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
                        isValid = false;
                    } else {
                        mesTarjeta = Integer.parseInt(working);
                        working+="/";
                        txtTarjetaFecha.setText(working);
                        txtTarjetaFecha.setSelection(working.length());
                    }
                }

                if (working.length()!=5) {
                    isValid = false;
                }

                if (!isValid) {
                    tilTarjetaFecha.setError("Fecha inválida");
                } else {
                    anioTarjeta = Integer.parseInt(working.substring(3));
                    tilTarjetaFecha.setError(null);
                    validacionSintaxisFechaCorrecta = true;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        txtTarjetaCcv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String working = s.toString();
                //consume caracteres si se escriben más de 3
                if (working.length()>3)
                    txtTarjetaCcv.setText(working.substring(3));
                else
                    tilTarjetaCcv.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {    //comprueba que el campo mail perdió el foco
                    mail = txtMail.getText().toString();    //obtiene el campo con el texto actualizado
                    //creamos una expresión regular
                    Pattern patron = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.([a-zA-Z]{2,4})+$");
                    //comprobamos si lo escrito coincide con el patron de expresión regular o no
                    if (!patron.matcher(mail).matches()){
                        tilMail.setHintEnabled(false);
                        tilMail.setError("Escribe un correo válido");
                    }
                    else
                        validacionSintaxisMailCorrecta = true;
                }
            }
        });

        txtClave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    if (txtClave.length()<6 || txtClave.length()>20){
                        tilClave.setHintEnabled(false);
                        tilClave.setError("Clave inválida");
                    }
                    else
                        validacionClaveCorrecta = true;
                }
            }
        });

        txtRepetirClave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    clave = txtClave.getText().toString();
                    repetirClave = txtRepetirClave.getText().toString();
                    if (!clave.equals(repetirClave)){
                        tilRepetirClave.setHintEnabled(false);
                        tilRepetirClave.setError("Las claves ingresadas no coinciden");
                    }
                    else
                        validacionRepetirClaveCorrecto = true;
                }
            }
        });

        txtTarjetaCcv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    if(txtTarjetaCcv.length()<3)
                        tilTarjetaCcv.setError("CVV inválido");
                    else
                        validacionSintaxisCvvCorrecta = true;
                }
            }
        });

        radioGroupTipoCuenta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioButtonBase: seekBarCreditoInicial.setProgress(100-valorMinimoSeekBarCreditoInicial); break;
                    case R.id.radioButtonPremium: seekBarCreditoInicial.setProgress(250-valorMinimoSeekBarCreditoInicial); break;
                    case R.id.radioButtonFull: seekBarCreditoInicial.setProgress(500-valorMinimoSeekBarCreditoInicial); break;
                }
            }
        });

        //setamos el valor inicial del seekbar en el textView
        textViewCreditoInicial.setText("$ 100");
        seekBarCreditoInicial.setMax(1500 - valorMinimoSeekBarCreditoInicial);
        seekBarCreditoInicial.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
                //forma de establecer el mínimo, no se puede asignar un valor distinto de 0 como mínimo de un seekbar
                int valor = i + valorMinimoSeekBarCreditoInicial;
                textViewCreditoInicial.setText("$ " + valor);

                //según el valor que tome el seekbar actualizamos el tipo de cuenta en el radioGroup
                if(valor<250)
                    radioGroupTipoCuenta.check(R.id.radioButtonBase);
                else if (valor<500)
                    radioGroupTipoCuenta.check(R.id.radioButtonPremium);
                else
                    radioGroupTipoCuenta.check(R.id.radioButtonFull);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        switchEsVendedor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean chequeado) {
                //obtengo la referencia al layout que contiene los datos a mostrar/ocultar
                ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayoutDatosVendedor);
                if(chequeado){
                    //si el switch esta activado se muestran los campos "Alias CBU" y "CBU"
                    constraintLayout.setVisibility(View.VISIBLE);

                    //hago las validaciones de campo vacio en tiempo real
                    validacionAliasCbuVacio = false;
                    validacionCbuVacio = false;
                    txtAliasCbu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            aliasCbu = txtAliasCbu.getText().toString();
                            if(!hasFocus){
                                if (aliasCbu.equals("")){
                                    tilAliasCbu.setError("Completá este dato");
                                }
                                else{
                                    tilAliasCbu.setError(null);
                                    validacionAliasCbuVacio = true;
                                }
                            }
                        }
                    });

                    txtCbu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            cbu = txtCbu.getText().toString();
                            if(!hasFocus){
                                if (cbu.equals("")){
                                    tilCbu.setError("Completá este dato");
                                }
                                else{
                                    tilCbu.setError(null);
                                    validacionCbuVacio = true;
                                }
                            }
                        }
                    });
                }
                else {
                    //si el switch esta desactivado se ocultan los campos "Alias CBU" y "CBU"
                    constraintLayout.setVisibility(View.GONE);
                    //se colocan las validaciones de campos vacios en true porque no nos interesan
                    validacionAliasCbuVacio = true;
                    validacionCbuVacio = true;
                }
            }
        });

        //Checkbox Aceptar terminos y condiciones
        checkBoxTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //habilita el boton registrarme cuando se marca el check (el boton está desabilitado por default por xml)
                if (checkBoxTerminos.isChecked())
                    botonRegistrarse.setEnabled(true);
                else
                    botonRegistrarse.setEnabled(false);
            }
        });

        //Botón registrarse
        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sacamos el foco de todos los editText para que se apliquen las validaciones que estan en los metodos setOnFocusChangeListener
                checkBoxTerminos.setFocusable(true);
                checkBoxTerminos.setFocusableInTouchMode(true);
                checkBoxTerminos.requestFocus();
                validarDatos();

                Log.e("validacionNombreVacio ", String.valueOf(validacionNombreVacio));
                Log.e("validacionMailVacio ", String.valueOf(validacionMailVacio));
                Log.e("validacionClaveVacio ", String.valueOf(validacionClaveVacio));
                Log.e("RepetirClaveVacio ", String.valueOf(validacionRepetirClaveVacio));
                Log.e("TarjetaNumeroVacio ", String.valueOf(validacionTarjetaNumeroVacio));
                Log.e("TarjetaCcvVacio ", String.valueOf(validacionTarjetaCcvVacio));
                Log.e("TipoCuentaSeleccionada ", String.valueOf(validacionTipoCuentaSeleccionada));
                Log.e("SintaxisMailCorrecta ", String.valueOf(validacionSintaxisMailCorrecta));
                Log.e("RepetirClaveCorrecto ", String.valueOf(validacionRepetirClaveCorrecto));
                Log.e("SintaxisFechaCorrecta ", String.valueOf(validacionSintaxisFechaCorrecta));
                Log.e("valTarjetaVencimiento ", String.valueOf(validacionTarjetaVencimiento));
                Log.e("valClaveCorrecta ", String.valueOf(validacionClaveCorrecta));
                Log.e("valAliasCbuVacio ", String.valueOf(validacionAliasCbuVacio));
                Log.e("validacionCbuVacio ", String.valueOf(validacionCbuVacio));

                //verificamos si las banderas de validacion están todas en true para confirmar campos como válidos
                if(validacionNombreVacio&&validacionMailVacio&&validacionClaveVacio&&validacionRepetirClaveVacio&&validacionTarjetaNumeroVacio
                &&validacionTarjetaCcvVacio&&validacionTipoCuentaSeleccionada&&validacionSintaxisMailCorrecta&&validacionRepetirClaveCorrecto
                &&validacionSintaxisFechaCorrecta && validacionTarjetaVencimiento&&validacionClaveCorrecta&&validacionAliasCbuVacio&&validacionCbuVacio){
                    Toast.makeText(getApplicationContext(),"Ingreso de datos correctos", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Ingreso de datos incorrectos o algún campo vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validarDatos() {
        //Setear variables
        nombre = txtNombre.getText().toString();
        mail = txtMail.getText().toString();
        clave = txtClave.getText().toString();
        repetirClave = txtRepetirClave.getText().toString();
        tarjetaNumero = txtTarjetaNumero.getText().toString();
        tarjetaFecha = txtTarjetaFecha.getText().toString();
        tarjetaCcv = txtTarjetaCcv.getText().toString();
        aliasCbu = txtAliasCbu.getText().toString();
        cbu = txtCbu.getText().toString();

        //Verificar campos vacios
        if(nombre.equals(""))      {
            tilNombre.setError("No olvides tu nombre"); //setea un mensaje de error en la etiqueta flotante inferior del TextInputLayout
        }
        else
            validacionNombreVacio = true;

        if (mail.equals("")) {
            tilMail.setError("Completá este dato");
            tilMail.setHintEnabled(false);      //obliga al hint a mantenerse dentro del editText y no mostrarse como etiqueta flotante
                                                // para evitar demasiadas etiquetas flotantes visualmente
        }
        else
            validacionMailVacio=true;

        if (clave.equals("")) {
            tilClave.setError("Completá este dato");
            tilClave.setHintEnabled(false);
        }
        else
            validacionClaveVacio = true;

        if (repetirClave.equals("")) {
            tilRepetirClave.setError("Completá este dato");
            tilRepetirClave.setHintEnabled(false);
        }
        else
            validacionRepetirClaveVacio = true;

        if (tarjetaNumero.equals("")) {
            tilTarjetaNumero.setError("Completá este dato");
            tilTarjetaNumero.setHintEnabled(false);
        }
        else
            validacionTarjetaNumeroVacio = true;

        if (tarjetaFecha.equals("")) {
            tilTarjetaFecha.setError("Completá este dato");
            tilTarjetaFecha.setHintEnabled(false);
        }

        if (tarjetaCcv.equals(""))  {
            tilTarjetaCcv.setError("Completá este dato");
            tilTarjetaCcv.setHintEnabled(false);
        }
        else
            validacionTarjetaCcvVacio = true;


        //Verificar si hay un tipo de cuenta seleccionado
        if(radioGroupTipoCuenta.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(),"Seleccioná un tipo de cuenta",Toast.LENGTH_SHORT).show();
        }
        else
            validacionTipoCuentaSeleccionada = true;

        //Verificar que si ingresó una tarjeta de crédito la fecha de vencimiento por lo menos sea
        //superior a los próximos 3 meses.
        int currentYear = Calendar.getInstance().get(Calendar.YEAR) - 2000;
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        if(anioTarjeta>currentYear)
            validacionTarjetaVencimiento = true;
        else if (anioTarjeta==currentYear && (mesTarjeta - currentMonth) >3 )
            validacionTarjetaVencimiento = true;
        else if(anioTarjeta !=0 && mesTarjeta!=0) //solo mostramos el mensaje si ingreso algún valor
            Toast.makeText(getApplicationContext(),"Tu tarjeta se encuentra vencida o próxima a vencer. Ingresa otra ",Toast.LENGTH_SHORT).show();
    }

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
}
