package co.edu.uniquindio.billeteravirtual.billeteravirtual.Model;

public class Presupuesto {
    private static int contadorId = 0;
    private final int idPresupuesto;
    private String nombrePresupuesto;
    private double MontoAsignado;
    private double MontoGastado;

    private Cuenta cuentaAsociada;
    private Categoria categoriaAsociada;
    BilleteraVirtual ownedByBilleteraVirtual;

    public Presupuesto(String nombrePresupuesto,
                       double MontoAsignado,
                       double MontoGastado,
                       Cuenta cuentaAsociada,
                       Categoria categoriaAsociada) {
        this.idPresupuesto = ++contadorId;
        this.nombrePresupuesto = nombrePresupuesto;
        this.MontoAsignado = MontoAsignado;
        this.MontoGastado = MontoGastado;
        this.cuentaAsociada = cuentaAsociada;
        this.categoriaAsociada = categoriaAsociada;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public String getNombrePresupuesto() {
        return nombrePresupuesto;
    }

    public void setNombrePresupuesto(String nombrePresupuesto) {
        this.nombrePresupuesto = nombrePresupuesto;
    }

    public double getMontoAsignado() {
        return MontoAsignado;
    }

    public void setMontoAsignado(double montoAsignado) {
        MontoAsignado = montoAsignado;
    }

    public double getMontoGastado() {
        return MontoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        MontoGastado = montoGastado;
    }

    public Cuenta getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public Categoria getCategoriaAsociada() {
        return categoriaAsociada;
    }

    public void setCategoriaAsociada(Categoria categoriaAsociada) {
        this.categoriaAsociada = categoriaAsociada;
    }

    public BilleteraVirtual getOwnedByBilleteraVirtual() {
        return ownedByBilleteraVirtual;
    }

    public void setOwnedByBilleteraVirtual(BilleteraVirtual ownedByBilleteraVirtual) {
        this.ownedByBilleteraVirtual = ownedByBilleteraVirtual;
    }
}
