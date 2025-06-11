# Despliegue Docker:

**Para realizar un despliegue rápido del proyecto con Docker Desktop debemos seguir los siguientes pasos:**

1. Realizar un "git clone https://github.com/Jorgedehaz/FantasyOne" en el lugar deseado.
2. Acceder mediante el terminal (CMD) a la ubicación del proyecto.
3. Ejecutar "docker compose build --no-cache" 
4. Ejecutar "docker compose up"
5. Acceder en el navegador a https://localhost y deberiamos visualizar el front del proyecto.

**Extra:**

Si deseamos hacer peticiones en POSTMAN o modificar la BD directamente podremos hacerlo mediante:

1. Debemos hacer peticiones a localhost:8080 . Ejemplo: "http://localhost:8080/api/auth/login"
2. Debemos establecer una conexión a localhost:3307 en MySQL o "mysql -h 127.0.0.1 -P 3307 -u fantasy -p" desde el CMD

Si deseamos inicializar resultados de prueba para comprobar el funcionamiento de graáficas, clasificación, etc:

(Loguearse en la cuenta de POSTMAN, tiene la carpeta guardada con las peticiones y pruebas)

1.Debemos realizar POST http://localhost:8080/api/carreras de las carreras indicadas abajo.
2. Debemos realizar un POST http://localhost:8080/api/resultados/initialize


[
  {
    "nombreGP": "Australian Grand Prix",
    "circuito": "Albert Park",
    "fecha": "2024-03-17",
    "temporada": 2024,
    "meetingKey": 1219,
    "externalId": "1219"
  },
  {
    "nombreGP": "Bahrain Grand Prix",
    "circuito": "Bahrain International Circuit",
    "fecha": "2024-03-31",
    "temporada": 2024,
    "meetingKey": 1220,
    "externalId": "1220"
  },
  {
    "nombreGP": "Chinese Grand Prix",
    "circuito": "Shanghai International Circuit",
    "fecha": "2024-04-14",
    "temporada": 2024,
    "meetingKey": 1221,
    "externalId": "1221"
  },
  {
    "nombreGP": "Miami Grand Prix",
    "circuito": "Miami International Autodrome",
    "fecha": "2024-05-05",
    "temporada": 2024,
    "meetingKey": 1222,
    "externalId": "1222"
  },
  {
    "nombreGP": "Monaco Grand Prix",
    "circuito": "Monaco",
    "fecha": "2024-05-15",
    "temporada": 2024,
    "meetingKey": 1223,
    "externalId": "1223"
  }
]
