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

import java.util.ArrayList;
import java.util.List;

@Name("altapago")
@ViewScoped
public class AltaPago {

    private boolean skip;
    private String filtro;
    private List<Venta> datos;

    private Venta seleccionVenta;
    private Pago pago = new Pago();

    @PostConstruct
    public void init() {
        datos = new ArrayList<>();
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Venta> getDatos() {
        return datos;
    }

    public void setDatos(List<Venta> datos) {
        this.datos = datos;
    }

    public Venta getSeleccionVenta() {
        return seleccionVenta;
    }

    public void setSeleccionVenta(Venta seleccionVenta) {
        this.seleccionVenta = seleccionVenta;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirmar";
        } else {
            //asignamos pago a venta
            if (event.getNewStep().equals("Pago")) {
                this.pago.setVenta(this.seleccionVenta);
            }
            return event.getNewStep();
        }
    }
    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }


    public void onRowSelect(SelectEvent<Venta> event) {
        FacesMessage msg = new FacesMessage("Venta seleccionada", String.valueOf(event.getObject().getUsuario()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.seleccionVenta = event.getObject();
    }

    public void onRowUnselect(UnselectEvent<Venta> event) {
        FacesMessage msg = new FacesMessage("Venta cancelada", String.valueOf(event.getObject().getUsuario()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.seleccionVenta = null;
    }

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

    public void salvar() {
        this.pago.setVenta(this.seleccionVenta);
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/pago/");
        Entity<Pago> entity = Entity.entity(pago, MediaType.APPLICATION_JSON);
        Pago nuevoPago = rootUri
                .path("salvar")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(pago), Pago.class);

        FacesMessage msg = new FacesMessage("Pago guardado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        cliente.close();
    }
}