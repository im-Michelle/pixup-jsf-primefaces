package dgtic.modulo11.controller;

import dgtic.modulo11.modelo.*;
import jakarta.annotation.PostConstruct;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("usuarioalta")
@ViewScoped
public class AltaUsuario implements Serializable {
    private boolean skip;
    private Usuario usuario = new Usuario();
    private Domicilio domicilio = new Domicilio();

    private Map<String, String> estado = new HashMap<>();
    private String estadoSeleccionado;

    private Map<String, String> municipio = new HashMap<>();
    private String municipioSeleccionado;

    private Map<String, String> colonia = new HashMap<>();
    private String coloniaSeleccionado;

    private Map<String, String> tipoDomicilio = new HashMap<>();
    private String tipoDomicilioSeleccionado;


    @PostConstruct
    public void inicio() {
        this.buscarEstado();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Map<String, String> getEstado() {
        return estado;
    }

    public void setEstado(Map<String, String> estado) {
        this.estado = estado;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public Map<String, String> getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Map<String, String> municipio) {
        this.municipio = municipio;
    }

    public String getMunicipioSeleccionado() {
        return municipioSeleccionado;
    }

    public void setMunicipioSeleccionado(String municipioSeleccionado) {
        this.municipioSeleccionado = municipioSeleccionado;
    }

    public Map<String, String> getColonia() {
        return colonia;
    }

    public void setColonia(Map<String, String> colonia) {
        this.colonia = colonia;
    }

    public String getColoniaSeleccionado() {
        return coloniaSeleccionado;
    }

    public void setColoniaSeleccionado(String coloniaSeleccionado) {
        this.coloniaSeleccionado = coloniaSeleccionado;
    }

    public Map<String, String> getTipoDomicilio() {
        return tipoDomicilio;
    }

    public void setTipoDomicilio(Map<String, String> tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }

    public String getTipoDomicilioSeleccionado() {
        return tipoDomicilioSeleccionado;
    }

    public void setTipoDomicilioSeleccionado(String tipoDomicilioSeleccionado) {
        this.tipoDomicilioSeleccionado = tipoDomicilioSeleccionado;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirmar";
        } else {
            //registramos usuario a domicilio
            if (event.getNewStep().equals("Domicilio")) {
                this.domicilio.setUsuario(this.usuario);

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

    public void onEstadoChange() {
        if (estadoSeleccionado != null && !"".equals(estadoSeleccionado)) {
            municipio = new HashMap<>();
            this.buscarMunicipio(this.estadoSeleccionado);
            //this.domicilio.setIdEstado(Integer.valueOf(estadoSeleccionado));
        }
    }

    public void onMunicipioChange() {
        if (municipioSeleccionado != null && !"".equals(municipioSeleccionado)) {
            colonia = new HashMap<>();
            this.buscarColonia(municipioSeleccionado);
            //this.domicilio.setIdMunicipio(Integer.valueOf(municipioSeleccionado));
        }
    }

    public void onColoniaChange() {
        if (coloniaSeleccionado != null && !"".equals(coloniaSeleccionado)) {
            //this.domicilio.getColonia().setId(Integer.valueOf(coloniaSeleccionado));
            this.buscarColoniaUsuarioRegistrada(coloniaSeleccionado);
            tipoDomicilio = new HashMap<>();
            this.buscarTipoDomicilio();
        }
    }

    public void ontipoDomicilioChange() {
        if (tipoDomicilioSeleccionado != null && !"".equals(tipoDomicilioSeleccionado)) {
            //this.domicilio.getTipoDomicilio().setId(Integer.valueOf(tipoDomicilioSeleccionado));
            this.buscarTipoDomicilioRegistrada(tipoDomicilioSeleccionado);
        }
    }

    private void buscarEstado() {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/estado/").path("todos");
        List<Estado> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<List<Estado>>() {
                });
        for (Estado fila : datos) {
            estado.put(fila.getNombre(), String.valueOf(fila.getId()));
        }
        cliente.close();
    }

    private void buscarMunicipio(String idEstado) {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/municipio/relacion/").path(idEstado);
        List<Municipio> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<List<Municipio>>() {
                });
        for (Municipio fila : datos) {
            municipio.put(fila.getNombre(), String.valueOf(fila.getId()));
        }
        cliente.close();

    }

    private void buscarColonia(String idMunicipio) {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/colonia/relacion/").path(idMunicipio);
        List<Colonia> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<List<Colonia>>() {
                });
        for (Colonia fila : datos) {
            colonia.put(fila.getNombre(), String.valueOf(fila.getId()));
        }
        cliente.close();

    }

    private void buscarColoniaUsuarioRegistrada(String idColonia) {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/colonia/").path(idColonia);
        Colonia datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<Colonia>() {
                });
        this.domicilio.setColonia(datos);
        cliente.close();

    }

    private void buscarTipoDomicilio() {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/tipodomicilio/").path("todos");
        List<TipoDomicilio> datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<List<TipoDomicilio>>() {
                });
        for (TipoDomicilio fila : datos) {
            tipoDomicilio.put(fila.getDescripcion(), String.valueOf(fila.getId()));
        }
        cliente.close();
    }

    private void buscarTipoDomicilioRegistrada(String idTipoDomicilio) {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/tipodomicilio/").path(idTipoDomicilio);
        TipoDomicilio datos = rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<TipoDomicilio>() {
                });
        this.domicilio.setTipoDomicilio(datos);
        cliente.close();
    }

    public void salvar() {
        Client cliente = ClientBuilder.newClient();
        WebTarget rootUri = cliente.target("http://localhost:8080/rest-pixup/api/usuario/");
        Usuario usuario = this.domicilio.getUsuario();
        Entity<Usuario> entity = Entity.entity(usuario, MediaType.APPLICATION_JSON);
        Usuario nuevoUsuario = rootUri
                .path("salvar")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(usuario), Usuario.class);


        this.domicilio.setUsuario(nuevoUsuario);
        System.out.println(Entity.json(this.domicilio));
        System.out.println(this.domicilio.getColonia());
        System.out.println(this.domicilio.getTipoDomicilio());
        System.out.println(this.domicilio.getUsuario());

        Client cliente2 = ClientBuilder.newClient();
        WebTarget rootUri2 = cliente2.target("http://localhost:8080/rest-pixup/api/domicilio/");

        Entity<Domicilio> entity2 = Entity.entity(this.domicilio, MediaType.APPLICATION_JSON);
        Domicilio nuevoDomicilio = rootUri2
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(this.domicilio), Domicilio.class);

        FacesMessage msg = new FacesMessage("Se pudo realizar");
        FacesContext.getCurrentInstance().addMessage(null, msg);


        cliente.close();

    }
}
