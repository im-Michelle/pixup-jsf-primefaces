package dgtic.modulo11.modelo;

import java.security.Principal;
import java.util.Date;

public class Venta {
    private Integer id;
    private Float costoTotal;
    private Date fecha;
    private Integer cantidadTotal;
    private Byte estatusEnvio;
    private Float costoEnvio;
    private Usuario usuario = new Usuario();
    private Pago pago = new Pago();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Integer cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Byte getEstatusEnvio() {
        return estatusEnvio;
    }

    public void setEstatusEnvio(Byte estatusEnvio) {
        this.estatusEnvio = estatusEnvio;
    }

    public Float getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Float costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
