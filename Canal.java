package p3;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.io.IOException;
import java.util.ArrayList;

public class Canal {
	String nombre;
	String idioma;
	String grupo;

	public Canal(){}

	public Canal(String nombre, String idioma, String grupo){ 
		this.nombre =  nombre;
		this.idioma = idioma;
		this.grupo = grupo;
	}
	/*
	public Canal(String grupo, String idc, String nombre, String duracion){
		this.grupo =  grupo;
		this.idc = idc;
		this.nombre = nombre;
		this.duracion = duracion;
	}
	*/
	public static ArrayList<Canal> ordenarNombre(ArrayList<Canal> lista)throws IOException{	
		ArrayList<Canal> sortedList = new ArrayList<Canal>();
		ArrayList<String> nombres = new ArrayList<String>();
		int index = 0;
		String aux = "";
		int comp1, comp2 = 0;
		for(int i = 0; i < lista.size(); i++){
			aux = lista.get(i).getNombre();
			nombres.add(aux);
		}
		Collections.sort(nombres);
		for(int i = 0; i < nombres.size(); i++){
			Sint25P3.escribir("nombre: " + nombres.get(i));
			for(int j = 0; j < lista.size(); j++){
				if(nombres.get(i).contains(lista.get(j).getNombre())){
					Sint25P3.escribir("Metemos Canal en la lista");
					sortedList.add(lista.get(j));
				}
			}
		}	
		Sint25P3.escribir("Numero de canales ordenadas: "+ sortedList.size());
		return sortedList;
	}
	/*
	public static ArrayList<Canal> ordenarPorgrupo(ArrayList<Canal> lista) {
		TreeMap<String,Canal> canales = new TreeMap<String, Canal>();
		int tiempo = 0; 
		for(int i = 0; i < lista.size(); i++) {
			canales.put(lista.get(i).getgrupo(), lista.get(i));
		}
		lista.clear();
		for(String key : canales.keySet()){
			lista.add(canales.get(key));
		}
		Collections.reverse(lista);
		return lista;
	}
	*/
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
}
