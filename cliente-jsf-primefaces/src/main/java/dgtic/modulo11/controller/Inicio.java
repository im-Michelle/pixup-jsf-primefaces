package dgtic.modulo11.controller;

import dgtic.modulo11.modelo.TipoDomicilio;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdk.jfr.Name;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class Inicio {

    private Map<String,String> tipoDomicilio=new HashMap<>();
    private String dato;


    @Produces
    @Model
    public String mensaje(){
        return "Recogiendo la informacion";
    }

    @Produces
    @RequestScoped
    @Named(value = "tipoDomicilio")
    public Map<String, String> getTipoDomicilio() {
        Client cliente= ClientBuilder.newClient();
        WebTarget rootUri=cliente.target("http://localhost:8090/pixup/api/").path("tiposDomicilio/todos");
        List<TipoDomicilio> datos=rootUri.request(MediaType.APPLICATION_JSON).get(Response.class)
                .readEntity(new GenericType<List<TipoDomicilio>>(){});
        for (TipoDomicilio fila:datos) {
            tipoDomicilio.put(fila.getDescripcion(),String.valueOf(fila.getId()));
        }
        cliente.close();
        prueba();
        return tipoDomicilio;
    }
    private void prueba(){
        Client cliente= ClientBuilder.newClient();
        WebTarget rootUri=cliente.target("http://localhost:8090/pixup/api/").path("tiposDomicilio/uno");
        TipoDomicilio datos=rootUri.request(MediaType.APPLICATION_JSON)
                .get(TipoDomicilio.class);
        System.out.println(datos.toString());
        cliente.close();
    }


    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
