package com.fzamperin.reflection;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;


public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException  {
		// TODO Auto-generated method stub
		/*JFileChooser chooser = new JFileChooser();
		int retorno = chooser.showSaveDialog(null);
		if (retorno==JFileChooser.APPROVE_OPTION){
			File file = new File(chooser.getSelectedFile().getAbsolutePath());
			XML xml = new XML(file);
			xml.openXML();
		}*/
		File file = new File("D:\\Repos\\Reflection\\src\\com\\fzamperin\\reflection\\Components.xml");
		XML xml = new XML(file);
		xml.openXML();
		ArrayList<Object> Lista = xml.getListaDeComponentes();
		for(Object objeto : Lista) {
			if(objeto instanceof Components) {
				Components component = (Components) objeto;
				ArrayList<Object> parametros = component.getParametros();
				for(Object objetoDentro : parametros) {
					System.out.println("Parametro Components: " + objetoDentro);
				}
				ArrayList<Object> componentes = component.getParametros();
				for(Object objetoComponente : componentes) {
					if(objetoComponente instanceof )
				}
				for(Object objetoDentro : parametros)
			}
			else {
				Component component = (Component) objeto;
				
			}
		}
	}

}