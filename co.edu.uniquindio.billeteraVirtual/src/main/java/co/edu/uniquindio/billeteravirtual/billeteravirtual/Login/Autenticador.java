package co.edu.uniquindio.billeteravirtual.billeteravirtual.Login;

import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Administrador;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.BilleteraVirtual;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Usuario;
import co.edu.uniquindio.billeteravirtual.billeteravirtual.Service.IAutenticar;

public class Autenticador implements IAutenticar {

    private BilleteraVirtual billeteraVirtual;
    private Usuario usuarioAutenticado = null;
    private Administrador administradorAutenticado = null;

    public Autenticador(BilleteraVirtual billeteraVirtual) {
        this.billeteraVirtual = billeteraVirtual;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public Administrador getAdminAutenticado() {
        return administradorAutenticado;
    }

    @Override
    public boolean autenticarUsuario(String correo, String clave) {
        for (Usuario usuario1 : billeteraVirtual.getListaUsuarios()) {
            if (usuario1.getCorreo() != null &&
                    usuario1.getClave() != null &&
                    usuario1.getCorreo().equals(correo) &&
                    usuario1.getClave().equals(clave)) {
                usuarioAutenticado = usuario1;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean autenticarAdmin(String correo, String clave) {
        for (Administrador administrador : billeteraVirtual.getListaAdministradores()){
            if (administrador.getCorreo().equals(correo) && administrador.getClave().equals(clave)){
                administradorAutenticado = administrador;
                return true;
            }
        }
        return false;
    }
}
