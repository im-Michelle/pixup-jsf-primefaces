package dgtic.modulo11.modelo;

import java.util.List;

public class Colonia {
    private Integer id;
    private String nombre;
    private String cp;
    private Municipio municipio;

    private List<Domicilio> domicilios;

    public Colonia() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Domicilio> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    @Override
    public String toString() {
        return "Colonia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cp='" + cp + '\'' +
                ", municipio=" + municipio +
                '}';
    }
}
