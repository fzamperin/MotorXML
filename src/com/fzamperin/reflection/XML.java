package com.fzamperin.reflection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML {
	
	private File file = null;
	private ArrayList<Object> listaDeComponentes = new ArrayList<Object>();
	
	public XML(File file) {
		this.file = file;
	}

	public void openXML() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		File xmlFile = this.file;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(xmlFile);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		lerXMLRecursao(doc,listaDeComponentes);
	}

	private void lerXMLRecursao(Node no, ArrayList<Object> listaDeComponentes) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		NodeList listaNo = no.getChildNodes();
		for(int contadorComponentesPai = 0; contadorComponentesPai < listaNo.getLength(); contadorComponentesPai++) {
			Node componentePaiNo = listaNo.item(contadorComponentesPai);
			if(componentePaiNo.getNodeType() == Node.ELEMENT_NODE) {
				Element elementComponentPai = (Element) componentePaiNo;
				if(elementComponentPai.getNodeName().toLowerCase().matches("component")) {
					//Caso o nodo seja um component
					if(elementComponentPai.hasAttribute("class")) {
						//Caso o nodo seja uma classe implementada
						System.out.println("Nodo Encontrado: " + elementComponentPai.getNodeName());
						if(elementComponentPai.getParentNode().getNodeName().matches("component")) {
							//Caso o component seja filho de components
							Components componentsAuxiliar = (Components) listaDeComponentes.get(contadorComponentesPai);
							Component componentAuxiliar = (Component) Class.forName(elementComponentPai.getAttribute("class")).newInstance();
							componentsAuxiliar.setComponent(componentAuxiliar);
							listaDeComponentes.add(componentsAuxiliar);
							lerXMLRecursao(componentePaiNo, listaDeComponentes);
						}
						if(elementComponentPai.getParentNode().getNodeName().matches("components")){
							//Caso components não seja filho de component
							Component componentAuxiliar = (Component) Class.forName(elementComponentPai.getAttribute("class")).newInstance();
							listaDeComponentes.add(componentAuxiliar);
							lerXMLRecursao(componentePaiNo, listaDeComponentes);
						}
					}
					else {
						//Caso o nodo seja um component não declarado
						Components componentesAuxiliar = new Components();
						componentesAuxiliar.setNome(elementComponentPai.getAttribute("type"));
						listaDeComponentes.add(componentesAuxiliar);
						lerXMLRecursao(componentePaiNo, listaDeComponentes);
					}
				}
				else {
					if(elementComponentPai.getNodeName().toLowerCase().matches("components")) {
						//Se for uma lista de components (faz nada)
						System.out.println("Nodo Encontrado: " + elementComponentPai.getNodeName());
						lerXMLRecursao(componentePaiNo, listaDeComponentes);
					}
					else {
						if(elementComponentPai.getNodeName().toLowerCase().matches("parameters")) {
							//Caso seja uma lista de parametros (faz nada)
							System.out.println("Nodo Encontrado: " + elementComponentPai.getNodeName());
							lerXMLRecursao(componentePaiNo, listaDeComponentes);
						}
						else {
							//Caso seja um parametro
							System.out.println("Nodo Encontrado: " + elementComponentPai.getNodeName());
							if(elementComponentPai.getParentNode().getNodeName().matches("component")) {
								//Caso seja um parametro de Component
								Component componenteAuxiliar = (Component) listaDeComponentes.get(contadorComponentesPai);
								componenteAuxiliar.parametros.add(elementComponentPai.getTextContent());
								lerXMLRecursao(componentePaiNo, listaDeComponentes);
							}
							if(elementComponentPai.getParentNode().getNodeName().matches("components")) {
								//Caso seja um parametro de Components
								Components componentsAuxiliar = (Components) listaDeComponentes.get(contadorComponentesPai);
								componentsAuxiliar.setParametro(elementComponentPai.getTextContent());
								lerXMLRecursao(componentePaiNo, listaDeComponentes);
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<Object> getListaDeComponentes() {
		return listaDeComponentes;
	}

	public void setListaDeComponentes(ArrayList<Object> listaDeComponentes) {
		this.listaDeComponentes = listaDeComponentes;
	}
}
