package p3;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;

public class Programa {
	String titulo;
	String edad;
	String hora;
	String resumen;
	int lenTitulo; 
	
	public Programa(){}

	public Programa(String titulo, String edad, String hora, String resumen, int lenTitulo) {
		this.titulo = titulo;
		this.edad = edad;
		this.hora = hora;
		this.resumen = resumen;
		this.lenTitulo = lenTitulo;
	}

	public static ArrayList<Programa> ordenarLista(ArrayList<Programa> lista)throws IOException{
		int key;
		TreeMap<Integer, Programa> orden = new TreeMap<Integer, Programa>();
		ArrayList<Programa> sortedList = new ArrayList<Programa>();
		for(int i = 0; i < lista.size(); i++){
			key = Integer.parseInt(lista.get(i).getLenTitulo() + "" +lista.get(i).getHora().replace(":", ""));
			orden.put(key, lista.get(i));
		};
		for(Map.Entry<Integer, Programa> entry : orden.entrySet()){
			sortedList.add(entry.getValue());
		}
		return sortedList;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getLenTitulo() {
		return lenTitulo;
	}
	public void setLenTitulo(int lenTitulo) {
		this.lenTitulo = lenTitulo;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
}
