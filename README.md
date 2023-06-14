# DBCaseApplication
#Webservice: Distanzrechner Bahnhöfe
##Technologien / Softwarekomponenten:
-Java (neueste Version)
  -Eclipse (neueste Version)
  -Springboot: Spring Initialir verwendet
    -Maven Project Management
    -Apache Tomcat Anwendungsserver
  -Zusätzliche Importe über pom.xml:
    -org.json: um JSON-Response zu erstellen
    -com.opencsv: um csv einzulesen und verarbeiten zu können
 ##Klassen:
  -Spring-Application: Startet Springboot-Application (u.a. Tomcat-Server) 
  -Controller: Eigentliche Webservice Implementierung, Mapping auf GET-Methode, Übergabe der Parameter an Business-Logik
  -Count-Distance: Business-Logik, die csv. verarbeitet ( korrekte Bahnhöfe und Koordinaten auslesen), die Distanzen berechnet und entsprechende Daten zurückgibt
 ##Die Business-Logik:
  ###Konstruktoren:
    -CaseApplicationCountDistance: CSV-reader Object mit Übergabe des City-Strings aus GET Methode , implizieter Konstruktoraufruft des JSON-Objekts, gleichzeitiger Methodenaufruf zur Distanzberechnung 
    -JSONObject CaseApplicationCounter(): Erstellt JSNO-Response die über Rest API abgefragte Daten enthält und gibt diese zurück  
  ###Methoden:
    -coordCity1 und coordcity 2: Geben String-Arrays mit Städtenamen und Koordinaten zu den API-Call-Strings zurück
    -distanceInKM: mit String-Arrays aus coordCity1 und coordCity2 werden Koordinaten extrahiert, vorverarbeitet, mit Haversine-Formel Distanz berechnet und gerundet. Übergabe des Differenz- und       Städtedatensatzes an JSON-Object als Strings
  
  
