package dgtic.modulo11.controller;

import dgtic.modulo11.modelo.TipoDomicilio;
import dgtic.modulo11.modelo.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @PostConstruct
    public void init() {
        datos = new ArrayList<>();

    }


    public String buscarUsuario() {
        if (filtro.length() >= 3) {
            Client cliente = ClientBuilder.newClient();
            WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/usuario/filtro/").path(filtro);
            List<Usuario> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                    .readEntity(new GenericType<List<Usuario>>() {
                    });
            this.datos=new ArrayList<>();
            this.datos=datos;

            cliente.close();
            System.out.println(this.datos.get(0).toString());

        }
        return null;
    }
    public void onRowSelect(SelectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario seleccionado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onRowUnselect(UnselectEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Usuario cancelado", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
}
