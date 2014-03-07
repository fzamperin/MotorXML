package com.fzamperin.reflection;

import java.util.ArrayList;

public class Components {
	
	private String Nome;
	private ArrayList<Object> parametros;
	private ArrayList<Object> components;
	
	public ArrayList<Object> getParametros() {
		return parametros;
	}
	
	public void setParametro(Object parametro) {
		this.parametros.add(parametro);
	}
	
	public ArrayList<Object> getComponents() {
		return components;
	}
	
	public void setComponent(Object component) {
		this.components.add(component);
	}
	
	public String getNome() {
		return Nome;
	}
	
	public void setNome(String nome) {
		Nome = nome;
	}
	
}
