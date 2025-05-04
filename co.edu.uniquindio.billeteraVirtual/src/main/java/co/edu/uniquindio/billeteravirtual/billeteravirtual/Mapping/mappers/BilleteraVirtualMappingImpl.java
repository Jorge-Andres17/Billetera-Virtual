package co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.mappers;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Mapping.dto.CategoriaDto;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Categoria;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IBilleteraVirtualMapping;

import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtualMappingImpl implements IBilleteraVirtualMapping {

        @Override
        public List<CategoriaDto> getCategoriaDto(List<Categoria> listaCategoria) {
            if(listaCategoria == null){
                return null;
            }
            List<CategoriaDto> listaCategoriaDto = new ArrayList<CategoriaDto>(listaCategoria.size());
            for (Categoria categoria : listaCategoria) {
                listaCategoriaDto.add(categoriaToCategoriaDto(categoria));
            }

            return listaCategoriaDto;
        }

        @Override
        public CategoriaDto categoriaToCategoriaDto(Categoria categoria) {
            return new CategoriaDto(
                    categoria.getIdCategoria(),
                    categoria.getNombre(),
                    categoria.getDescripcion());
        }

        @Override
        public Categoria categoriaDtoToCategoria(String nombre, String descripcion) {
            return Categoria.builder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .build();
        }
}

