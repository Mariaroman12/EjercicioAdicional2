package com.politecnicomalaga;

import java.util.HashMap;
import java.util.Map;

public class Component extends SparePart{
	
	private Map<Integer, SparePart> misSp;

	public Component(int code, String text, double price) {
		super(code, text, price);
		 this.misSp = new HashMap<>();
	}

	
	//Añadir
	public boolean addSpare(SparePart sp) {
        if (!haySpare(sp.getCode())) {
        	misSp.put(sp.getCode(), sp);
            return true;
        }     
        
        return false;
    }
	
	//Eliminar
	public boolean EliminarSpare(int code) {
        if (!haySpare(code)) {
        	misSp.remove(code);
            return true;
        }     
        
        return false;
    }
	
	//Buscar
	public boolean haySpare(int code) {
		for(Integer codigo : misSp.keySet()) {
            if (misSp.containsKey(code)) {
                return true;
            }
        }
        return false;
    }
	
	//Mostrar
	public SparePart buscaSpare(int code) {
        return misSp.get(code);
        
    }
	

}
