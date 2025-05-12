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

    public boolean agregarCategoria(String nombre, String descripcion) {
        return modelFactory.agregarCategoria(nombre,descripcion);
    }

    public CategoriaDto agregarCategoriaDto(String nombre, String descripcion) {
        return modelFactory.agregarCategoriaDto(nombre,descripcion);
    }

    public boolean eliminarCategoria(int idCategoria) {
        return modelFactory.eliminarCategoria(idCategoria);
    }

    public boolean actualizarCategoria(int id, String nombre, String descripcion) {
        return modelFactory.actualizarCategoria(id,nombre,descripcion);
    }

    public CategoriaDto actualizarCategoriaDto(int i, String nombre, String descripcion) {
        return modelFactory.actualizarCategoriaDto(i, nombre,descripcion);
    }
}
