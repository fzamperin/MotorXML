package com.fzamperin.reflection;

import java.util.ArrayList;

public abstract class Component {
	
	protected ArrayList<Object> parametros;
	
	public void setParameter(Object... listaDeParametros) {
	}
	
	public void run() {
	}
	
	public Object getOutput() {
		return null;
	}	
}