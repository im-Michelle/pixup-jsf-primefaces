package dgtic.modulo11.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;


@Named("vista")
@RequestScoped
public class Vistas {
	private List<String> imagenes;

	@PostConstruct
	public void inicio() {
		this.home();
	}
	public void home() {
		imagenes = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			imagenes.add("img/paisajes/nature"+i+".jpg");
		}	
	}
	public void ciencia() {	
		imagenes = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			imagenes.add("img/ciencia/ciencia"+i+".jpg");
		}
	}
	public void peliculas() {		
		imagenes = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			imagenes.add("img/peliculas/pelicula"+i+".jpg");
		}
	}
	public void aragon() {		
		imagenes = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			imagenes.add("img/aragon/aragon"+i+".jpg");
		}
	}

	public List<String> getImagenes() {
		return imagenes;
	}
	

}
