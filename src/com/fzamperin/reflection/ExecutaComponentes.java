package com.fzamperin.reflection;

import java.util.ArrayList;

public class ExecutaComponentes {
	
	private ArrayList<Object> componentes;
	
	public ExecutaComponentes(ArrayList<Object> componentes) {
		this.componentes = componentes;
	}
	
	public void lerComponentes() {
		for(Object objeto : componentes) {
			if(objeto instanceof Component) {
				ArrayList<Object> Lista = ((Component) objeto).parametros;
				for(Object objetofilho : Lista) {
					System.out.println("Parametro do Component: " + objetofilho);
				}
			}
			else {
				System.out.println("Components: " + objeto);
				ArrayList<Object> Lista = 
			}
		}
	}
}
