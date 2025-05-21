package co.edu.uniquindio.billeteravirtual.billeteravirtual.Controller;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Factory.ModelFactory;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;

import java.util.List;

public class CrudCategoriaController {
    ModelFactory modelFactory;

    public CrudCategoriaController(){
        modelFactory = ModelFactory.getInstancia();
    }

    public List<CategoriaDto> obtenerCategorias() {
        return modelFactory.obtenerCategorias();
    }

    public CategoriaDto agregarCategoriaDto(String nombre, String descripcion) {
        return modelFactory.agregarCategoriaDto(nombre,descripcion);
    }

    public boolean eliminarCategoria(String nombre) {
        return modelFactory.eliminarCategoria(nombre);
    }

    public boolean actualizarCategoria( String nombre, String descripcion) {
        return modelFactory.actualizarCategoria(nombre,descripcion);
    }

    public CategoriaDto actualizarCategoriaDto( String nombre, String descripcion) {
        return modelFactory.actualizarCategoriaDto( nombre,descripcion);
    }
}
