# Film Manager App 🎬

A Java Swing + SQL Server desktop app for managing films.  
Supports login, roles (Admin/User), film CRUD, image storage, and RSS import.

## Features
- Login/Register with hashed passwords
- Admin dashboard (reset DB, import from RSS)
- Film management with image preview
- MSSQL via Docker (cross-platform)

## Database
- Download docker desktop:<br> https://www.docker.com/products/docker-desktop/
- Run this command in terminal: <br>
  ```docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Supersifra98" -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest```
- Run ```docker ps``` to check if it is running 
- Use a SQL client to connect (from your Mac): 
  - IntelliJ or Apache Netbeans directly
  - Azure Data Studio
  - DBeaver
  - TablePlus
- Connection settings: 
  - Server: localhost 
  - Port: 1433 
  - User: sa Password: 
  - YourStrong!Passw0rd (or whatever you chose)<br>

## How to Run
1. Clone the repo
2. Start Docker
3. Run scripts for creating tables and procedures
4. Run `LoginView.java`

## Default Admin Login
Username: **admin**<br>
Password: **admin123**

## TODO
1. Use JTabbedPane or JSplitPane to organize views
2. Export films to XML using JAXB (export_films.xml)
3. Connect RSS import
4. Add basic Actor/Genre/Director support (model + table + CRUD)

### Optional
1. UI improvements (logos, spacing, color theme)
2. File chooser to select images (instead of typing path)
3. Confirm delete dialog for each film 
4. Search or filter films in the table 
5. Settings panel for theme, language, etc.

