package co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Login.Autenticador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.mappers.BilleteraVirtualMappingImpl;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Cuenta;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IBilleteraVirtualMapping;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Utils.DataUtils;

import java.util.List;

public class ModelFactory {
    private static ModelFactory modelFactory;
    private BilleteraVirtual billeteraVirtual;
    private Autenticador autenticador;
    private IBilleteraVirtualMapping mapper;

    public static ModelFactory getInstancia() {
        if(modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
       mapper = new BilleteraVirtualMappingImpl();

        billeteraVirtual = DataUtils.inicializarDatos();
        autenticador = new Autenticador(billeteraVirtual);
    }

    public boolean autenticarUsuario(String correo, String clave) {
        return autenticador.autenticarUsuario(correo,clave);
    }

    public boolean autenticarAdministrador(String correo, String clave) {
        return autenticador.autenticarAdmin(correo,clave);
    }

    public Autenticador getAutenticador() {
        return autenticador;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return billeteraVirtual.agregarCuenta(cuenta);
    }

    public List<Cuenta> obtenerCuentas() {
        return billeteraVirtual.getListaCuentas();
    }

    public boolean eliminarCuenta(int id) {
        return billeteraVirtual.eliminarCuenta(id);
    }

    public List<CategoriaDto> obtenerCategorias() {
        return mapper.getCategoriaDto(billeteraVirtual.getListaCategorias());
    }

    public boolean agregarCategoria(String nombre, String descripcion) {
        return billeteraVirtual.agregarCategoria(mapper.categoriaDtoToCategoria(nombre, descripcion));
    }

    public CategoriaDto agregarCategoriaDto(String nombre, String descripcion) {
        return mapper.categoriaToCategoriaDto(billeteraVirtual.agregarCategoria(nombre, descripcion));
    }

    public boolean eliminarCategoria(int idCategoria) {
        return billeteraVirtual.eliminarCategoria(idCategoria);
    }
}
