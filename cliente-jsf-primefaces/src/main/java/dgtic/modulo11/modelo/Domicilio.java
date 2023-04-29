package dgtic.modulo11.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class Domicilio {

    private Integer id;
    @NotBlank
    private String calle;
    @NotBlank
    private String numExterior;
    @NotBlank
    private String numInterior;
    @NotNull
    private Colonia colonia=new Colonia();
    @NotNull
    private Usuario usuario=new Usuario();
    @NotNull
    private TipoDomicilio tipoDomicilio=new TipoDomicilio();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoDomicilio getTipoDomicilio() {
        return tipoDomicilio;
    }

    public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }



}
