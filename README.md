
# Ghost Net Fishing

Dieses Projekt unterstützt die Erkennung und Analyse von Geisternetzen.  

---

# Einrichtung des Projekts für eine lokale Entwicklungsumgebung beschrieben.

## Voraussetzungen

- Java Development Kit (JDK), empfohlen: Version 17+
- Maven
- Eclipse IDE (mit Payara Tools Plugin)
- Payara Server 6.2025.6
- Docker & Docker Compose

---

## Lokale Einrichtung

### 1. Repository klonen

```bash
git clone https://github.com/mariovooo/ghost-net-fishing.git
```

### 2. Payara Server installieren

- Die Installationsdatei von **Payara Server 6.2025.6** (Community Edition reicht) können hier heruntergeladen werden:  
  https://www.payara.fish/downloads/
- Anschließend in ein lokales Verzeichnis entpacken, z.B.:

```bash
~/payara6
```

### 3. Payara Tools in Eclipse installieren

- In Eclipse über **Help** → **Eclipse Marketplace** die **Payara Tools** installieren.
- Nach der Installation in **Servers** → **New Server** den Payara Server mit dem zuvor installierten Pfad registrieren.

### 4. Payara Server konfigurieren

#### 4.1 Payara Admin Console öffnen

- Der Payara Server (`domain1`) starten.
- Die Admin Console unter folgendem Link öffnen:

```
http://localhost:4848
```

#### 4.2 H2 Connection Pool (`h2pool`) anlegen

1. Navigieren zu:  
   **Resources** → **JDBC** → **JDBC Connection Pools**
2. Falls nicht schon standardmäßig angelegt: Über **New...** einen neuen Pool erstellen:
   - **Pool Name:** `h2pool`
   - **Resource Type:** `javax.sql.DataSource`
   - **Database Driver Vendor:** `H2`
3. Mit **Next** und anschließend **Finish** abschließen.
4. Den existierenden bzw. soeben erstellten Pool öffnen und im Tab **Additional Properties** die Property **URL** wie folgt erweitern:

```
jdbc:h2:file:~/ghostnetdb;DB_CLOSE_DELAY=-1;MODE=LEGACY
```

5. Änderungen speichern.

#### 4.3 JDBC Resource anlegen

1. Navigieren zu:  
   **Resources** → **JDBC** → **JDBC Resources**
2. Über **New...** eine neue Resource anlegen:
   - **JNDI Name:** `jdbc/ghostnetDS`
   - **Pool Name:** `h2pool` auswählen.
3. Speichern.

### 5. Projekt bauen

Im Projektverzeichnis folgenden Befehl ausführen:

```bash
mvn clean install
```

Alternativ in Eclipse im Projektexplorer Rechtsklick auf das Root-Verzeichnis (hier GhostNetFishing) **Run As** → **Maven clean** und **Maven install**

> Troubleshoot: Externe Imports sind in Eclipse standardmäßig deaktiviert; Bei Bedarf erzwingen. Sollten externe Abhängigkeiten fehlen, sind diese bei Bedarf in der `pom.xml` zu ergänzen.

### 6. Docker-Container starten

Mit folgendem Befehl wird der Docker-Stack gestartet:

```bash
docker-compose.yml up
```

### 7. Anwendung deployen

- Das Deployment kann direkt aus Eclipse erfolgen.

> Troubleshoot: Falls Eclipse einen Fehler beim Deployment verursacht, sollte die WAR-Datei manuell über die Payara Admin Console deployt werden:

1. Navigieren zu:  
   **Applications** → **Deploy...**
2. Die WAR-Datei aus dem Verzeichnis auswählen:

```bash
<Projektverzeichnis>/target/GhostNetFishing.war
```

3. Den **Context Root** prüfen oder anpassen, z.B. `/GhostNetFishing`.
4. Mit **OK** das Deployment starten.

---

## Hinweise

- Die H2-Datenbank wird lokal unter folgendem Pfad als Datei gespeichert:

```bash
~/ghostnetdb
```

- Nach dem erfolgreichen Deployment ist die Anwendung erreichbar unter:

```
http://localhost:8080/GhostNetFishing
```

- Keycloak Admin Console ist erreichbar unter:

```
http://localhost:8081/admin/
```

- Payara Server Console ist erreichbar unter:

```
http://localhost:4848/
```

---
