package com.example.demo.controller;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;


//Business-Logik: csv. wird verarbeitet ( korrekte Bahnhöfe und Koordinaten auslesen), die Distanzen berechnen und entsprechende Daten zurückgeben
public class CaseApplicationCountDistance {
	
	//Array-List soll .csv Daten enthalten
	List<String[]> r; 	
	
	//Array soll city-Strings aus API-Call enthalten
	String[] c;
	
	//Rückgabedatensatz
	static String [] di=new String[4];
	
	//Konstruktur: CSV-reader Object mit Übergabe des City-Strings aus GET Methode 
	//implizieter Konstruktoraufruft des JSON-Objekts
	//gleichzeitiger Methodenaufruf zur Distanzberechnung 
	public CaseApplicationCountDistance (String[] cities) throws IOException {
		c= cities;
		String fileName = "src\\main\\resources\\DBBhf.csv";
       try (@SuppressWarnings("deprecation")
    
    //CSV-Reder Object
    //.csv liegt in Source-Ordner im Projekt
	CSVReader reader = new CSVReader(new FileReader(fileName),';')) {
              r = reader.readAll();
        }       
       distanceInKm(coordCity1(),coordCity2());
       CaseApplicationCounter ();
	}
	
	//Methode gibt String-Arrays mit Städtenamen und Koordinaten zu den API-Call-String 1 zurück, z.B "FF"
	private String[] coordCity1 () {
	
		String [] coordcity1=new String[3];
        for (int i = 1; i < r.size(); i++) {
            if (r.get(i)[1].equals(c[0].toString())) {
                coordcity1[0]= (r.get(i)[3]);
                coordcity1[1]= (r.get(i)[5]);
                coordcity1[2]= (r.get(i)[6]);            	
                break;
            }
        }	
		return coordcity1;	
		
	}
	
	//Methode gibt String-Arrays mit Städtenamen und Koordinaten zu den API-Call-String 2 zurück, z.B "BLS"
	private String[] coordCity2 () {
		String [] coordcity2 = new String[3];
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i)[1].equals(c[1].toString())) {
                coordcity2[0]= (r.get(i)[3]);
                coordcity2[1]= (r.get(i)[5]);
                coordcity2[2]= (r.get(i)[6]);            	
                break;
            }
        }	
		return coordcity2;		
	}
	
	//mit String-Arrays aus coordCity1 und coordCity2 werden Koordinaten extrahiert, 
	//vorverarbeitet, mit Haversine-Formel Distanz berechnet und gerundet. Übergabe des Differenz- und Städtedatensatzes an JSON-Object als Strings
	private static String [] distanceInKm (String [] coord1, String [] coord2) {
				
		String city1=coord1[0];
		String city2=coord2[0];
		
		//Städte aus coorcity-Methoden zu Datensatz hinzufügen
		di[0]=city1;
		di[1]=city2;
		
		//Koordinaten
		double lat1= Double.parseDouble(coord1[1].replace(",", "."));  
		double lon1= Double.parseDouble(coord1[2].replace(",", "."));
		
		double lat2= Double.parseDouble(coord2[1].replace(",", "."));
		double lon2= Double.parseDouble(coord2[2].replace(",", "."));
		
	    int radius = 6371;
	    
	    //Grad-Differenz der Koordinaten beider Städte Berechnen
	    double lat = Math.toRadians(lat2 - lat1);
	    double lon = Math.toRadians(lon2- lon1);	    
	    
	    //Haversine-Formel: Großkeisabstand zwischen zwei Punkten
	    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = radius * c;
	   
	    //Runden der Gleitkommazahl auf Ganzzahl
	    int rnd = (int) Math.round(d);
	    
	    //Distanz zum Rückgabedatensatz hinzufügen
	    di[2]=String.valueOf(rnd);

	    return di;
	}

	 // Konstruktur JSON-Object mit Bahnhofnahmen und berechneter Distanz, die über GET Methode abgefragt werden
	public JSONObject CaseApplicationCounter () { 			
		String fr=di[0];
		String to=di[1];
		String dis=di[2];
				
		JSONObject req = new JSONObject();
		req.put("from:", fr);
		req.put("to:", to);
		req.put("distance:", dis);
		req.put("unit:", "km");
		
	    System.out.println(req.toString());
		return req;
	}	
}
