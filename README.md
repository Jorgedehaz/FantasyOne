# FantasyOne Project

This repository contains a Spring Boot backend and a React frontend served by Nginx.

## Running with Docker Compose

1. Build and start all services:
   ```bash
   docker compose up --build
   ```
2. Access the application at `http://localhost`.

If the frontend shows a blank page, ensure the `frontend` image was built correctly and that `/usr/share/nginx/html/index.html` exists inside the running container.

Backend and frontend services are configured to restart automatically. Check container logs with `docker compose logs <service>` if they exit unexpectedly.
