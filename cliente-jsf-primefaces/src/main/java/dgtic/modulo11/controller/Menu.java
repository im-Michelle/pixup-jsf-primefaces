package dgtic.modulo11.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;


@Named("menu")
@RequestScoped
public class Menu {
	public String home(){
		return "usr/home?faces-redirect=true";
	}
	public String altaUsuario(){
		return "usr/alta-usuario?faces-redirect=true";
	}
	public String altaVenta(){
		return "usr/alta-venta?faces-redirect=true";
	}

}
