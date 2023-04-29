package dgtic.modulo11.controller;

import dgtic.modulo11.modelo.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named("altaventa")
@ViewScoped
public class AltaVenta implements Serializable {
    private boolean skip;
    private String filtro;
    private List<Usuario> datos;
    private Usuario seleccionUsuario;
    private Venta venta = new Venta();

    @PostConstruct
    public void init() {
        datos = new ArrayList<>();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String buscarUsuario() {
        if (filtro.length() >= 3) {
            Client cliente = ClientBuilder.newClient();
            WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/usuario/filtro/").path(filtro);
            List<Usuario> datos = rootUri.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Usuario>>() {});
            this.datos = new ArrayList<>(datos);
            cliente.close();
            System.out.println(this.datos.get(0).toString());
        }
        return null;
    }

    public void onRowSelect(SelectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario seleccionado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        seleccionUsuario = event.getObject();
    }

    public void onRowUnselect(UnselectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario cancelado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        seleccionUsuario = null;
    }


    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Usuario> getDatos() {
        return datos;
    }

    public void setDatos(List<Usuario> datos) {
        this.datos = datos;
    }

    public Usuario getSeleccionUsuario() {
        return seleccionUsuario;
    }

    public void setSeleccionUsuario(Usuario seleccionUsuario) {
        this.seleccionUsuario = seleccionUsuario;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirmar";
        } else {
            //registramos usuario a venta
            if (event.getNewStep().equals("Venta")) {
                this.venta.setUsuario(this.seleccionUsuario);
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

    public void salvar() {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/venta/");
        Venta nuevaVenta = rootUri.path("salvar").request(MediaType.APPLICATION_JSON)
                .post(Entity.json(this.venta), Venta.class);
        this.seleccionUsuario.colocarVenta(nuevaVenta);
        System.out.println(this.seleccionUsuario.getNombre());
        System.out.println(this.seleccionUsuario.getEmail());
        System.out.println(this.seleccionUsuario.getRfc());
        FacesMessage msg = new FacesMessage("Se pudo realizar");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        cliente.close();
    }
}
