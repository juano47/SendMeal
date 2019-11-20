package frsf.isi.dam.delaiglesia.sendmeal;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrarseUnitTest<val> {

    @Mock EditText edtNombreMock;
    @Mock EditText edtMailPlatoMock;
    @Mock EditText edtClaveMock;
    @Mock EditText edtRepetirClaveMock;
    @Mock EditText edtTarjetaNumeroMock;
    @Mock EditText edtTarjetaFechaMock;
    @Mock EditText edtTarjetaCcvMock;
    @Mock EditText edtAliasCbuMock;
    @Mock EditText edtCbuMock;
    @Mock RadioGroup radioGroupTipoCuentaMock;
    @Mock Editable edtMock;

    @Test
    public void validarCamposNoVaciosTest() {
        //El método solo valida que no haya campos vacios

        //Todos los campos tienen un valor distinto de vacio
        doReturn("4").when(edtMock).toString();
        doReturn(edtMock).when(edtNombreMock).getText();
        doReturn(edtMock).when(edtMailPlatoMock).getText();
        doReturn(edtMock).when(edtClaveMock).getText();
        doReturn(edtMock).when(edtRepetirClaveMock).getText();
        doReturn(edtMock).when(edtTarjetaNumeroMock).getText();
        doReturn(edtMock).when(edtTarjetaFechaMock).getText();
        doReturn(edtMock).when(edtTarjetaCcvMock).getText();
        doReturn(edtMock).when(edtAliasCbuMock).getText();
        doReturn(edtMock).when(edtCbuMock).getText();

        Registrarse registrarseActivity = new Registrarse();
        registrarseActivity.txtNombre = edtNombreMock;
        registrarseActivity.txtMail = edtMailPlatoMock;
        registrarseActivity.txtClave = edtClaveMock;
        registrarseActivity.txtRepetirClave = edtRepetirClaveMock;
        registrarseActivity.txtTarjetaNumero = edtTarjetaNumeroMock;
        registrarseActivity.txtTarjetaFecha = edtTarjetaFechaMock;
        registrarseActivity.txtTarjetaCcv = edtTarjetaCcvMock;
        registrarseActivity.txtAliasCbu = edtAliasCbuMock;
        registrarseActivity.txtCbu = edtCbuMock;
        registrarseActivity.radioGroupTipoCuenta = radioGroupTipoCuentaMock;
        registrarseActivity.validarDatos();

        assertTrue("Nombre", registrarseActivity.validacionNombreVacio);
        assertTrue("Email", registrarseActivity.validacionMailVacio);
        assertTrue("Clave no vacía", registrarseActivity.validacionClaveVacio);
        assertTrue("Repetir clave no vacía", registrarseActivity.validacionRepetirClaveVacio);
        assertTrue("Tarjeta número", registrarseActivity.validacionTarjetaNumeroVacio);
        assertTrue("Tarjeta Ccv", registrarseActivity.validacionTarjetaCcvVacio);
        assertTrue("Tipo cuenta seleccionada", registrarseActivity.validacionTipoCuentaSeleccionada);

    }

    @Test
    public void validarCuentaSeleccionadaTest() {

        //necesario solo para evitar nulls
        doReturn("algo").when(edtMock).toString();
        doReturn(edtMock).when(edtNombreMock).getText();
        doReturn(edtMock).when(edtMailPlatoMock).getText();
        doReturn(edtMock).when(edtClaveMock).getText();
        doReturn(edtMock).when(edtRepetirClaveMock).getText();
        doReturn(edtMock).when(edtTarjetaNumeroMock).getText();
        doReturn(edtMock).when(edtTarjetaFechaMock).getText();
        doReturn(edtMock).when(edtTarjetaCcvMock).getText();
        doReturn(edtMock).when(edtAliasCbuMock).getText();
        doReturn(edtMock).when(edtCbuMock).getText();

        Registrarse registrarseActivity = new Registrarse();
        registrarseActivity.txtNombre = edtNombreMock;
        registrarseActivity.txtMail = edtMailPlatoMock;
        registrarseActivity.txtClave = edtClaveMock;
        registrarseActivity.txtRepetirClave = edtRepetirClaveMock;
        registrarseActivity.txtTarjetaNumero = edtTarjetaNumeroMock;
        registrarseActivity.txtTarjetaFecha = edtTarjetaFechaMock;
        registrarseActivity.txtTarjetaCcv = edtTarjetaCcvMock;
        registrarseActivity.txtAliasCbu = edtAliasCbuMock;
        registrarseActivity.txtCbu = edtCbuMock;
        registrarseActivity.radioGroupTipoCuenta = radioGroupTipoCuentaMock;

        //acá empieza el test
        registrarseActivity.radioGroupTipoCuenta.check(-1);
        registrarseActivity.validarDatos();

        assertTrue("Cuenta seleccionda", registrarseActivity.validacionTipoCuentaSeleccionada);

    }
}
