package com.fzamperin.reflection;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeitorXML {
	
	private File file = null;
	
	public LeitorXML(File file) {
		this.file = file;
	}

	private NodeList ReadComponents(File file) {
		File xmlFile = file;
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
		NodeList nList = doc.getElementsByTagName("component");
		return nList;
	}

	public void runComponent() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		NodeList nList = ReadComponents(this.file);
		try {
			System.out.println("Numero de Componentes: " + nList.getLength());
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Componente: " + eElement.getAttribute("type"));
					NodeList ListParameters = eElement.getElementsByTagName("parameter");
					System.out.println("Numero de Parametros: "+ ListParameters.getLength());
					Object[] ListaDeParametros = new Object[ListParameters.getLength()];
					for(int temp3 = 0; temp3 < ListParameters.getLength(); temp3++) {
						Node ParameterNode = ListParameters.item(temp3);
						if(ParameterNode.getNodeType() == Node.ELEMENT_NODE) {
							Element ParameterElement = (Element) ParameterNode;
							ListaDeParametros[temp3] = ParameterElement.getTextContent();
						}
					}
					NodeList ListComponents = eElement.getElementsByTagName("component");
					System.out.println("Numero de Componentes Internos: "+ ListComponents.getLength());
					//Componentes Internos são executados aqui
					Component[] ListaDeComponentes = new Component[ListComponents.getLength()];
					for(int temp4 = 0; temp4 < ListComponents.getLength(); temp4++) {
						Node ComponentNode = ListComponents.item(temp4);
						if(ComponentNode.getNodeType() == Node.ELEMENT_NODE) {
							Element ComponentElement = (Element) ComponentNode;
							ListaDeComponentes[temp4] = (Component) Class.forName(ComponentElement.getAttribute("type")).newInstance();
							NodeList NodeListParametros2 = ComponentElement.getElementsByTagName("parameter");
							System.out.println("Parâmetros Internos: " + NodeListParametros2.getLength());
							Object[] ListaDeParametros2 = new Object[NodeListParametros2.getLength()];
							for(int temp5 = 0; temp5 < NodeListParametros2.getLength(); temp5++) {
								Node ParameterNode = NodeListParametros2.item(temp5);
								if(ParameterNode.getNodeType() == Node.ELEMENT_NODE) {
									Element parameterElement = (Element) ParameterNode;
									if(parameterElement.getTextContent().startsWith("#")) {
										if(parameterElement.getTextContent().toLowerCase().contains("parameter")) {
											Integer NumeroParametro = Integer.parseInt(parameterElement.getTextContent().substring(10));
											NumeroParametro--;
											ListaDeParametros2[temp5] = ListaDeParametros[NumeroParametro];
										}
										if(parameterElement.getTextContent().toLowerCase().contains("output")) {
											Integer NumeroOutput = Integer.parseInt(parameterElement.getTextContent().substring(7));
											NumeroOutput--;
											ListaDeComponentes[NumeroOutput].run();
											ListaDeParametros2[temp5] = ListaDeComponentes[NumeroOutput].getOutput();
										}
									}
									else {
										ListaDeParametros2[temp5] = (Object) parameterElement.getTextContent();
									}
								}
							}
							ListaDeComponentes[temp4].setParameter(ListaDeParametros2);
						}
					}
					int ultimo = ListaDeComponentes.length;
					ultimo = ultimo-1;
					ListaDeComponentes[ultimo].run();
					System.out.println("Resultado: " + ListaDeComponentes[ultimo].getOutput());
					break;
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}