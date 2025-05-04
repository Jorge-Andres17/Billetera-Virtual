package co.edu.uniquindio.billeteravirtual.billeteravirtual.Service;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;

import java.util.List;

public interface IBilleteraVirtualMapping {
    List<CategoriaDto> getCategoriaDto(List<Categoria> listaCategoria);
    CategoriaDto categoriaToCategoriaDto(Categoria categoria);
    Categoria categoriaDtoToCategoria(String nombre,String descripcion);
}

