package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model.Enums;

public enum TipoCuenta {
    AHORRO, CORRIENTE;

    @Override
    public String toString() {
        switch (this) {
            case AHORRO:
                return "Cuenta de Ahorros";
            case CORRIENTE:
                return "Cuenta Corriente";
            default:
                return super.toString();
        }
    }
}
