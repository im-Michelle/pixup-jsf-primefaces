package dgtic.modulo11.controller;

import dgtic.modulo11.modelo.Pago;
import dgtic.modulo11.modelo.Usuario;
import dgtic.modulo11.modelo.Venta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdk.jfr.Name;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dgtic.modulo11.modelo.Pago;
import dgtic.modulo11.modelo.Usuario;
import dgtic.modulo11.modelo.Venta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdk.jfr.Name;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Name("altapago")
@ViewScoped
public class AltaPago implements Serializable {

    private boolean skip;
    private String filtro;
    private List<Venta> datos;
    private Usuario usuarioSeleccionado;

    @PostConstruct
    public void init() {
        datos = new ArrayList<>();
    }

    private String idVenta;
    private Venta ventaSeleccionada;

    public String buscarVenta() {
        if (filtro.length() >= 3) {
            Client cliente = ClientBuilder.newClient();
            WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/venta/filtro/").path(filtro);
            List<Venta> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                    .readEntity(new GenericType<List<Venta>>() {});
            this.datos = new ArrayList<>(datos);

            cliente.close();
            System.out.println(this.datos.get(0).toString());
        }
        return null;
    }

    public void onRowSelect(SelectEvent<Venta> event) {
        FacesMessage msg = new FacesMessage("Venta seleccionada", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.ventaSeleccionada = event.getObject();
    }

    public void onRowUnselect(UnselectEvent<Venta> event) {
        FacesMessage msg = new FacesMessage("Venta cancelado", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.ventaSeleccionada = null;
    }


    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<Venta> getDatos() {
        return datos;
    }

    public void setDatos(List<Venta> datos) {
        this.datos = datos;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public Venta getVentaSeleccionada() {
        return ventaSeleccionada;
    }

    public void setVentaSeleccionada(Venta ventaSeleccionada) {
        this.ventaSeleccionada = ventaSeleccionada;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
}
