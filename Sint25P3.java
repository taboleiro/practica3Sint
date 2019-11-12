package p3;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Enumeration;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sint25P3 extends HttpServlet{
	static Element raiz;

	PrintWriter out;
	static String password = "taboleiro8";
	Boolean ini = true;
  	static File logFile;
	LinkedHashMap<String, Document> filesXML = new LinkedHashMap<String, Document>();
	LinkedList<Element> MMLs = new LinkedList<Element>();

	public static LinkedHashMap<String, ArrayList<String>> warnings = new LinkedHashMap<String, ArrayList<String>>();
	public static LinkedHashMap<String, ArrayList<String>> errores = new LinkedHashMap<String, ArrayList<String>>();
	public static LinkedHashMap<String, ArrayList<String>> erroresFatales = new LinkedHashMap<String,  ArrayList<String>>();

	static String dir = "http://gssi.det.uvigo.es/users/agil/public_html/SINT/19-20/";
	static String dirXML = "tvml-2004-12-01.xml";

	//public void init(ServletConfig config){
	public void init(ServletConfig config){
		String dirFile;
		try{
			String sFichero = "doc.txt";
			ServletContext application = config.getServletContext();
			String pathToFile = "file:///" + application.getRealPath("/iml.xsd");
			URL dirSchema = new URL(pathToFile.replace("\\", "/"));
			pathToFile = application.getRealPath("/doc.txt");
			logFile = new File(pathToFile.replace("\\", "/"));
			if(!logFile.exists()) logFile.createNewFile();			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			dirFile = dir + dirXML;
			escribir(dirFile);
			Document doc = dBuilder.parse(dirFile);
			doc.getDocumentElement().normalize();
			validateXML(dirSchema);
			//printTree(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String pfase = request.getParameter("pfase");
		if(pfase == null){
			pfase = "01";
		}else{
			escribir("la fase actual es: "+pfase);
		}
		if(request.getParameter("p") == null){
			Errores.errorWindow(response, "np", request.getParameter("auto"));
		}else if(!request.getParameter("p").equals(password)){
			Errores.errorWindow(response, "wp", request.getParameter("auto"));
		}else{
			switch(pfase){
				case "01":
					pantalla01(request,response);
					break;
				case "02":
					pantalla02(request,response);
					break;
				case "11":
					escribir("queremos entrar en 11");
					pantalla11(request,response);
					break;
				case "12":
					pantalla12(request,response);
					break;
				case "13":
					pantalla13(request,response);
					break;
				default:
					pantalla01(request,response);
					break;
			}
		}
	}


	
	public ArrayList<String> getC1Fechas()throws IOException{
		ArrayList<String> fechas = new ArrayList<String>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		String fecha;
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					fecha = xpath.evaluate("//Fecha", doc);
					usedFiles.add(key);
					fechas.add(fecha);
				}
			}
		}catch(Exception e){
			escribir(e.toString());
			e.printStackTrace();
		}
		Collections.sort(fechas);
		return fechas;
	}

	public ArrayList<Canal> getC1canales (String fecha)throws IOException{
		ArrayList<Canal> canalList= new ArrayList<Canal>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		Canal canal;
		NodeList prog, canales;
		String nombre, idioma, grupo;
		try{
			escribir("***    GETC1CANALES    ***");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fileXML;
			Document doc;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					escribir("fecha "+fecha);
					escribir("fecha prog:"+xpath.evaluate("//Programacion/Fecha", doc));
					canales = (NodeList)xpath.evaluate("Programacion[Fecha='"+fecha+"']/Canal", doc, XPathConstants.NODESET);
					if(canales.getLength() != 0){
						escribir("canales tiene elementos");
						for(int i = 0; i < canales.getLength(); i ++){
							nombre = xpath.evaluate("NombreCanal", canales.item(i));
							escribir(nombre);
							idioma = xpath.evaluate("@lang", canales.item(i));
							grupo = xpath.evaluate("Grupo", canales.item(i));
							canal = new Canal(nombre, idioma, grupo);
							canalList.add(canal);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Canal.ordenarNombre(canalList);
	}

	public ArrayList<Programa> getC1Peliculas(String fecha, String canal)throws IOException{
		ArrayList<Programa> programaList= new ArrayList<Programa>();
		ArrayList<String> usedFiles = new ArrayList<String>();
		Programa programa = new Programa();
		NodeList prog, programas;
		String titulo, edad, hora, resumen;
		try{

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fileXML;
			Document doc;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			for(String key : filesXML.keySet()){
				doc = filesXML.get(key);
				escribir("******************************");
				escribir("******************************");
				escribir("******************************");
				escribir("Estamos usando el fichero: "+key);
				escribir("******************************");
				escribir("******************************");
				escribir("******************************");
				if(!usedFiles.contains(key)){
					usedFiles.add(key);
					programas = (NodeList)xpath.evaluate("Programacion[Fecha='"+fecha+"']/Canal[NombreCanal='"+canal+"']/Programa[Categoria='Cine']", doc, XPathConstants.NODESET);
					escribir("estamos seleccionando los programas");
					if(programas.getLength() != 0){
						for(int i = 0; i < programas.getLength(); i ++){
							titulo = xpath.evaluate("NombrePrograma", programas.item(i));
							escribir(titulo);
							edad = xpath.evaluate("@edadminima", programas.item(i));
							hora = xpath.evaluate("HoraInicio", programas.item(i));
							resumen = xpath.evaluate("Text", programas.item(i));
							programa = new Programa(titulo, edad, hora, resumen, titulo.length());
							programaList.add(programa);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return programa.ordenarLista(programaList);	
	}


	public void validateXML(URL dirSchema)throws IOException{
		ArrayList<String> usedXML = new ArrayList<String>();
		ArrayList<String> namesXML = new ArrayList<String>();
		namesXML.add(dirXML);
		ArrayList<String> aux = new ArrayList<String>();
		File file;
		Errores fallos;
		String nameXML = new String();
		ArrayList<Document> validXMLs = new ArrayList<Document>();
		escribir("validando XML. Numero en lista = "+namesXML.size());
		Enumeration<String> enm = Collections.enumeration(namesXML);
		Document doc;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		while(enm.hasMoreElements()){
			nameXML = enm.nextElement();
			escribir(nameXML);
			if (!usedXML.contains(nameXML)){
				usedXML.add(nameXML);
				try{
					SchemaFactory sFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
					Schema schema = sFactory.newSchema(dirSchema);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setSchema(schema);
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					fallos = new Errores();
					dBuilder.setErrorHandler(fallos);
					if (!nameXML.startsWith("http://")){
						nameXML = dir+nameXML;
					}
					doc = dBuilder.parse(nameXML);
					if(fallos.getErrors().size() != 0 || fallos.getWarnings().size() != 0 || fallos.getFatalErrors().size() != 0){
						if(fallos.getErrors().size() != 0){
							aux = fallos.getErrors();
							errores.put(nameXML, aux);
							for(int x= 0; x < fallos.getErrors().size(); x++){
								escribir(errores.get(nameXML).get(x));
							}
						}
						if(fallos.getWarnings().size() != 0){
							aux = fallos.getWarnings();
							warnings.put(nameXML, aux);
						}
						if(fallos.getFatalErrors().size() != 0){
							aux = fallos.getFatalErrors();
							erroresFatales.put(nameXML, aux);
						}
					}
					else{
						NodeList fileNames = (NodeList)xpath.evaluate("//OtraEmision/TVML", doc, XPathConstants.NODESET);
						//NodeList fileNames = doc.getElementsByTagName("IML");
						for(int i = 0; i < fileNames.getLength(); i++){
							if(!namesXML.contains(fileNames.item(i).getTextContent()) && fileNames.item(i).getTextContent().contains("xml")){
								namesXML.add(fileNames.item(i).getTextContent());
							}
							enm = Collections.enumeration(namesXML);
							doc.getDocumentElement().normalize();
							filesXML.put(nameXML,doc);
						}
					}
				}catch(SAXParseException e1){
					ArrayList<String> excepcion = new ArrayList<String>();
					excepcion.add(e1.getMessage());
					escribir(e1.getMessage());
					erroresFatales.put(nameXML, excepcion);
				}catch(IOException e){
					e.printStackTrace();
				}catch(SAXException e2){
					e2.printStackTrace();
				}catch(ParserConfigurationException e3){
					e3.printStackTrace();
				}catch(XPathExpressionException e4){
					e4.printStackTrace();
				}
			}
		}
		return;
	}

	public static void escribir(String linea) throws IOException {
		FileWriter txt = new FileWriter(logFile,true);
		txt.write(linea);
		txt.write(" \r\n");
		txt.close();
		return;
	}

	/*********************************************************************************/
	/*********************************************************************************/
	/***************** DEFINICION DE LAS FUNCIONES DE PANTALLAS **********************/
	/*********************************************************************************/
	/*********************************************************************************/

	public void pantalla01(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			if(request.getParameter("auto") == null){
				RequestDispatcher requestDispatcherObj = request.getRequestDispatcher("/pantalla01.jsp");
				requestDispatcherObj.forward(request, response);
			}else{
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<service>");
				out.println("<status>OK</status>");
				out.println("</service>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		return;
	}

	public void pantalla02(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			if(request.getParameter("auto") == null){
				out.println("<!Doctype html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel='stylesheet' type='text/css' href='iml.css'>");
				out.println("<title>Errores</title>");
				out.println("<body>");
				out.println("<form method='GET' action='' accept-charset='utf-8'>");
				out.println("<h1> Servicio de consulta de canales</h2>");
				out.println("<ul>");
				out.println("<h2>Se han encontrado "+warnings.size()+" ficheros con warnings:</h2>");
				out.println("<br>");
				for(String key : warnings.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					for(int i = 0; i < warnings.get(key).size(); i++){
						out.println("<li type='circle'>"+warnings.get(key)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("<h2>Se han encontrado "+errores.size()+" ficheros con errores:</h2>");
				out.println("<br>");
				for(String key : errores.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					for(int i = 0; i < errores.get(key).size(); i++){
						out.println("<li type='circle'>"+errores.get(key).get(i)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("<h2>Se han encontrado "+erroresFatales.size()+" ficheros con errores fatales:</h2>");
				out.println("<br>");
				for(String key : erroresFatales.keySet()) {
					out.println("<li type='disc'>"+key+"</li>");
					out.println("<ul>");
					//out.println("len: "+erroresFatales.get(key).size());
					for(int i = 0; i < erroresFatales.get(key).size(); i++){
						out.println("<li type='circle'>"+erroresFatales.get(key).get(i)+"</li>");
					}
					out.println("</ul>");
				}
				out.println("</ul>");
				out.println("<br>");
				out.println("<input type='submit' id='atras' value='atras' onclick='document.forms[0].pfase.value=11'>");
				out.println("</form>");
				out.println("<div class='footer' name='nameAutor'>Pablo Táboas Rivas</div>");
				out.println("</body>");
				out.println("</html>");
			}else{
				String aux = "";
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<errores>");
				out.println("	<warnings>");
				for(String key : warnings.keySet()) {
					out.println("		<warning>");
					out.println("			<file>"+key+"</file>");
					for(int i = 0; i < warnings.get(key).size(); i++){
						aux += warnings.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");
					out.println("		</warning>");
				}
				out.println("	</warnings>");
				aux = "";
				out.println("	<errors>");
				for(String key : errores.keySet()) {
					out.println("		<error>");
					out.println("			<file>"+key+"</file>");
					for(int i = 0; i < errores.get(key).size(); i++){
						aux += errores.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");
					out.println("		</error>");
				}
				out.println("	</errors>");
				aux = "";
				out.println("	<fatalerrors>");
				for(String key : erroresFatales.keySet()) {
					out.println("		<fatalerror>");
					out.println("			<file>"+key+"</file>");
					for(int i = 0; i < erroresFatales.get(key).size(); i++){
						aux += erroresFatales.get(key).get(i)+"; ";
					}
					out.println("			<cause>"+aux+"</cause>");
					out.println("		</fatalerror>");
				}
				out.println("	</fatalerrors>");
				out.println("</errores>");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return;
	}
	public void pantalla11(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ArrayList<String> fechas;
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			fechas = getC1Fechas();
			request.setAttribute("fechas", fechas);
			RequestDispatcher requestDispatcherObj;
			if(request.getParameter("auto") == null){
				requestDispatcherObj = request.getRequestDispatcher("/pantalla11.jsp");
				requestDispatcherObj.forward(request, response);
			}else{
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<dias>");
				for(int i = 0; i < fechas.size(); i++) {
					out.println("	<dia>"+fechas.get(i)+"</dia>");
				}
				out.println("</dias>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			escribir(e.toString());
		} catch (ServletException e1) {
			escribir(e1.toString());
		}
		return;
	}

	public void pantalla12(HttpServletRequest request,HttpServletResponse response) {
		ArrayList<Canal> canales;
		RequestDispatcher requestDispatcherObj;
		try {
			if(request.getParameter("pdia") == null){
				Errores.errorWindow(response, "pdia", request.getParameter("auto"));
			}

			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			canales = getC1canales(request.getParameter("pdia"));
			request.setAttribute("canales", canales);
			if(request.getParameter("auto") == null){
				requestDispatcherObj = request.getRequestDispatcher("/pantalla12.jsp");
				requestDispatcherObj.forward(request, response);
			}else{
				response.setContentType("text/xml; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				out.println("<?xml version='1.0' encoding='utf-8' ?>");
				out.println("<canales>");
				for(int i = 0; i < canales.size(); i++) {
					out.println("<canal idioma='"+canales.get(i).getIdioma()+"' grupo='"+canales.get(i).getGrupo()+"'>"+canales.get(i).getNombre()+"</canal>");
				}
				out.println("</canales>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e){
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return;
	}
	public void pantalla13(HttpServletRequest request,HttpServletResponse response) {
		ArrayList<Programa> peliculas = new ArrayList<Programa>();
		RequestDispatcher requestDispatcherObj;
		try {
			if(request.getParameter("pdia") == null){
				Errores.errorWindow(response, "pdia", request.getParameter("auto"));
			}else if(request.getParameter("pcanal") == null){
				Errores.errorWindow(response, "pcanal", request.getParameter("auto"));
			}else{
				peliculas = getC1Peliculas(request.getParameter("pdia"), request.getParameter("pcanal"));
				request.setAttribute("peliculas", peliculas);
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				out = response.getWriter();
				if(request.getParameter("auto") == null){
					requestDispatcherObj = request.getRequestDispatcher("/pantalla13.jsp");
					requestDispatcherObj.forward(request, response);
				}else{
					response.setContentType("text/xml; charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					out = response.getWriter();
					out.println("<?xml version='1.0' encoding='utf-8' ?>");
					out.println("<peliculas>");
					for(int i = 0; i < peliculas.size(); i++) {
						out.println("<pelicula edad='"+peliculas.get(i).getEdad()+"' hora='"+peliculas.get(i).getHora()+"' resumen='"+peliculas.get(i).getResumen()+"'>"+peliculas.get(i).getTitulo()+"</pelicula>");
					}
					out.println("</peliculas>");
				}
			}
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
