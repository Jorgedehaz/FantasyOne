Despliegue Docker: 

Para realizar un despliegue rápido del proyecto con Docker Desktop debemos seguir los siguientes pasos: 

-1. Realizar un "git clone https://github.com/Jorgedehaz/FantasyOne" en el lugar deseado.
-2. Acceder mediante el terminal (CMD) a la ubicación del proyecto.
-3. Ejecutar "docker compose build --no-cache" 
-4. Ejecutar "docker compose up"
-5. Acceder en el navegador a https://localhost y deberiamos visualizar el front del proyecto.

Extra:

Si deseamos hacer peticiones en POSTMAN o modificar la BD directamente podremos hacerlo mediante:

-1. Debemos hacer peticiones a localhost:8080 . Ejemplo: "http://localhost:8080/api/auth/login"
-2. Debemos establecer una conexión a localhost:3307 en MySQL o "mysql -h 127.0.0.1 -P 3307 -u fantasy -p" desde el CMD
