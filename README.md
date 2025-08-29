# HHUDDLE 

Eine Hausverwaltungsplattform, die es Bewohnern ermöglicht sich zu vernetzen und Informationen auszutauschen und Hausverwaltungen ermöglicht, wichtige Informationen zu teilen. 

## Features 
- Benutzer-Registrierung, Login und Passwortänderung für Bewohner eines Hauses und Hausverwaltungen mittels Email-Adresse und Passwort 
- Gesonderte Interfaces und Features für Bewohner und Hausverwaltungen 

Hausbewohner: 
- Posten von Beiträgen auf sogenannte "Boards" wie Eventboard, Schwarzes Brett, Paketfinder inkl. Daten, Uhrzeiten, Text und Bilder --> Digitalisierung von Whiteboards, die in Eingangabereichen von Wohnhäusern hängen
- Kontakt der Beitragsersteller anonymisiert über einen Chat (Beitragsersteller und Chatuser werden nur über Top Nummern identifiziert, nicht über Namen) --> ein Teilen von persönlichen Daten zur Kontaktherstellung mit anderen Hausbewohnern wird nicht benötigt 
- Abrufen von wichtigen Informationen geteilt durch Hausverwaltung (bspw. "Wasser wird zwischen 13 Uhr und 15 Uhr abgestellt")

Hausverwaltung: 
- Anlegen neuer Häuser inkl. Erstellung von Tops & Gemeinschaftsräumen
- Verwaltung von Gemeinschaftsräumen (Anpassungen von Titel und Beschreibung, Hinzufügen von Bildern) 
- Top-Verwaltung --> Hausverwaltung kann Usern Tops mittels Email Adresse zuordnen
- Teilen von Informationen für Bewohner durch Erstellen von Beiträgen 



## Tech Stack
- Frontend: Vue.js, Vuetify 
- Backend: Spring Boot
- Datenbank: SQL, phpMyAdmin
- Auth: JSON Web Tokens (JWT)

## 🚀 Installation & Setup



### Vorraussetzungen 

- Node.js und npm
- IntelliJ IDEA (oder eine andere Java IDE)
- MySQL Datenbank & phpMyAdmin (XAMPP)
- Java 17+ (für Spring Boot Backend)
- Geklontes Repository 

### 1. Frontend vorbereiten

Wechsle ins Frontend-Verzeichnis in einer beliebigen IDE (zb. Visual Studio Code) und installiere Vue.js & Vuetify sowie alle Abhängigkeiten. Wechsle dafür ins Terminal und gib folgendes ein: 

```bash
npm install vue vuetify
npm install
```


### 2. Backend starten 

Öffne das Backend in einer beliebigen IDE. In Intellij, öffne die Project Structue und wähle unter "Project" die corretto-17 SDK aus. Suche dann nach der pom.xml File --> Rechtsklick --> Add as Maven project. Gehe nun zur HHuddleApplication File und starte die Application. Gegebenenfalls muss eine Lombok Annotation aktiviert werden. 

### 3. Datenbank starten

Öffne das XAMPP Control Panel. Unter "Actions", klicke auf "Start" bei Apache und MySQL. Bei MySAQL, klicke auf "Admin". Es öffnet sich der Localhost von phpMyAdmin. Falls noch keine Datenbank vorhanden ist, erstelle eine Datenbank mit dem Namen "hhuddle". 

### 4. Seite öffnen 

Wechsle in die IDE, wo das Frontend geöffnet ist. Im Terminal gib folgendes ein: 

```bash
npm run dev
```
Im Terminal, öffne nun via CTRL + Klick den localhost Link. Das Projekt sollte sich nun im Browser öffnen. 

### 5. Login 

Es gibt zwei Testuser, einer für einen Bewohner und einer für eine Hausverwaltung.

Bewohner
Mail: residenttest@gmx.at
Passwort: test 

Hausverwaltung
Mail: test@hausverwaltungstest.at
Passwort: admin







## Verbesserungsmöglichkeiten




## Autorinnen
- Ksenia Resch - Backend
- Raffaela Kay - Backend
- Huda Dawoud - Frontend 
- Emilia Adler - Frontend
