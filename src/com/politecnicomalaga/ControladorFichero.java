package com.politecnicomalaga;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;


public class ControladorFichero {

    public static SparePart importar(String fileName) {
    	Gson gson = new Gson();
        try (FileReader fr = new FileReader(fileName)) {
            if (fileName.contains("component")) {
                return gson.fromJson(fr, Component.class);
            } else {
                return gson.fromJson(fr, SparePart.class);
            }
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        } catch (JsonIOException e) {
            System.err.println("Error" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        return null;
    }
	

}