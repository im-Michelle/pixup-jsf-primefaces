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
    private String filtro;
    private List<Usuario> datos;

    private Usuario selecionUsuario;
    private Venta venta;

    @PostConstruct
    public void init() {
        datos = new ArrayList<>();
        venta = new Venta();
    }

    public String buscarUsuario() {
        if (filtro.length() >= 3) {
            Client cliente = ClientBuilder.newClient();
            WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/usuario/filtro/").path(filtro);
            List<Usuario> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                    .readEntity(new GenericType<List<Usuario>>() {});
            this.datos = new ArrayList<>(datos);

            cliente.close();
            System.out.println(this.datos.get(0).toString());
        }
        return null;
    }

    public void onRowSelect(SelectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario seleccionado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.selecionUsuario = event.getObject();
    }

    public void onRowUnselect(UnselectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario cancelado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.selecionUsuario = null;
    }

    public String guardarVenta() {
        if (selecionUsuario != null) {
            venta.setUsuario(selecionUsuario);
            // Setear los demás atributos de la venta según la lógica de tu aplicación
            // ...

            // Guardar la venta en el servidor REST
            Client cliente = ClientBuilder.newClient();
            WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/venta");
            Response response = rootUri.request(MediaType.APPLICATION_JSON).post(Entity.json(venta));

            if (response.getStatus() == 201) {
                FacesMessage msg = new FacesMessage("Venta creada", "La venta ha sido creada exitosamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("Error", "Ha ocurrido un error al crear la venta");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            cliente.close();
        } else {
            FacesMessage msg = new FacesMessage("Error", "Debe seleccionar un usuario antes de guardar la venta");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
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

    public Usuario getSelecionUsuario() {
        return selecionUsuario;
    }

    public void setSelecionUsuario(Usuario selecionUsuario) {
        this.selecionUsuario = selecionUsuario;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
