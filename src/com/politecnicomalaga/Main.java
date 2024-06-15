package com.politecnicomalaga;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;


public class Main {
	private Map<Integer, Component> componentes = new HashMap();

	public static void main(String args[]) {
		Main main = new Main();
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("\nBienvenido al sistema de piezas de coche:");
			System.out.println("1. Dar de alta un SpareParts o components");
			System.out.println("2. Buscar pieza y mostrarla");
			System.out.println("3. Grabar a fichero una pieza");
			System.out.println("4. Cargar pieza desde JSON");
			System.out.println("5. Salir");
			System.out.print("Por favor, seleccione una opción: ");
			int opcion = Integer.valueOf(sc.nextLine());

			switch (opcion) {
			case 1:
				main.tipoPiezaAgregar(sc);
				break;
			case 2:
				main.tipoPiezaBuscar(sc);
				break;

			case 3:
				System.out.println("Introduce el nombre del fichero para guardar la pieza");
                String fileN = sc.nextLine();
                main.grabarAJSON(fileN);
                
				break;

			case 4:
				System.out.println("Introduce el nombre del fichero para guardar la pieza");
                fileN = sc.nextLine();
                main.cargarJSON(fileN);
				break;

			case 5:
				System.out.println("¡Hasta luego!");
				return;

			default:
				System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
			}

		}
	}

	public void tipoPiezaAgregar(Scanner sc) {
		System.out.println("Seleccione el tipo de pieza a crear:");
		System.out.println("1. SparePart");
		System.out.println("2. Component");
		int tipoPieza = Integer.valueOf(sc.nextLine());

		switch (tipoPieza) {
		case 1:
			agregarSparePart(sc);
			break;
		case 2:
			agregarComponent(sc);
			break;
		default:
			System.out.println("Opción no valida");
		}
	}	
		
		
	public void tipoPiezaBuscar(Scanner sc) {
		System.out.println("Seleccione el tipo de pieza a crear:");
		System.out.println("1. SparePart");
		System.out.println("2. Component");
		int tipoPieza = Integer.valueOf(sc.nextLine());

		switch (tipoPieza) {
		case 1:
			System.out.println("Introduce el codigo del Spare");
	        int codiguito = Integer.valueOf(sc.nextLine());
	        buscarSpare(codiguito);
			break;
		case 2:
			System.out.println("Introduce el codigo del Componente");
	        codiguito = Integer.valueOf(sc.nextLine());
			buscarComponent(codiguito);
			break;
		default:
			System.out.println("Opción no valida");
		}
	}	
		

	public void agregarSparePart(Scanner sc) {
		System.out.println("Agregue el código del SparePart:");
		int codigo = Integer.valueOf(sc.nextLine());
		System.out.println("Agregue la descripción:");
		String descripcion = sc.nextLine();
		System.out.println("Agregue el precio:");
		double precio = sc.nextDouble();
		sc.nextLine();
		SparePart sp = new SparePart(codigo, descripcion, precio);
		Component c = new Component(codigo, descripcion, precio);
		c.addSpare(sp);
		componentes.put(codigo, c);
		System.out.println("Pieza de Repuesto agregada.");
	}

	public void agregarComponent(Scanner sc) {
		System.out.println("Agregue el código del Component:");
		int codigo = Integer.valueOf(sc.nextLine());
		System.out.println("Agregue la descripción:");
		String descripcion = sc.nextLine();
		System.out.println("Agregue el precio:");
		double precio = sc.nextDouble();
		sc.nextLine();
		Component c = new Component(codigo, descripcion, precio);
		componentes.put(codigo, c);
		boolean mas = true;
		while (mas) {

			System.out.print("Ingrese el código de la parte: ");
			int code = Integer.valueOf(sc.nextLine());
			System.out.print("Ingrese la descripción de la parte: ");
			String desc = sc.nextLine();

			System.out.print("Ingrese el precio de la parte: ");
			precio = sc.nextDouble();
			sc.nextLine();

			SparePart part = new SparePart(code, desc, precio);
			c.addSpare(part);

			System.out.print("¿Desea agregar otra parte? (Si/No)");
			String respuesta = sc.nextLine();
			if (respuesta.equalsIgnoreCase("No")) {
				mas = false;
			}

		}
		System.out.println("Componente agregado correctamente");

	}

	
	public void buscarSpare(int code) {
		for (Component c : componentes.values()) {
			System.out.println(c.buscaSpare(code));
		}
	}
	public void buscarComponent(int code) {
		for (Component c : componentes.values()) {
			if(c.getCode()==code) {
				System.out.println("Código: " + c.getCode() + " Descripción: " + c.getText() + " Precio: " + c.getPrice());
			}
		}
	}
	
	private void grabarAJSON(String fileN) {
       Gson gson = new Gson();
       try (FileWriter fw = new FileWriter(fileN + ".json")) {
            gson.toJson(componentes.values(), fw);
            System.out.println("Exportación exitosa");
        } catch (IOException e) {
            System.out.println("Error al exportar" + e.getMessage());
        }
    }
	
	private void cargarJSON(String fileN) {
        if (fileN.isEmpty()) {
            System.out.println("La ruta del archivo esta vacía");
            return;
        }
        SparePart esto = ControladorFichero.importar(fileN);
        if (esto != null && esto instanceof Component) {
            componentes.put(esto.getCode(), (Component)esto);
            System.out.println("Importación exitosa");
            System.out.println(esto);
        }
      }
	
	// Añadir
	public boolean addComponent(Component c) {
		if (!hayComponent(c.getCode())) {
			componentes.put(c.getCode(), c);
			return true;
		}

		return false;
	}

	// Eliminar
	public boolean EliminarComponent(int code) {
		if (!hayComponent(code)) {
			componentes.remove(code);
			return true;
		}

		return false;
	}

	// Buscar
	public boolean hayComponent(int code) {
		for (Integer codigo : componentes.keySet()) {
			if (componentes.containsKey(code)) {
				return true;
			}
		}
		return false;
	}

	// Mostrar
	public Component buscaComponent(int code) {
		for (Integer codigo : componentes.keySet()) {
			if (componentes.containsKey(code)) {
				return componentes.get(code);
			}
		}
		return null;
	}

}
