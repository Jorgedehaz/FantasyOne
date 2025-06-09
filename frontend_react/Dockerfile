# 1) Build stage
FROM node:18-alpine AS builder
WORKDIR /app

# Copiamos package.json / lockfile y montamos sólo dependencias
COPY package*.json ./
# En AWS a veces falla por memoria, así que forzamos modo CI:
ENV CI=true
RUN npm ci

# Copiamos el resto y lanzamos el build
COPY . .
RUN npm run build

# 2) Nginx stage para servir el dist estático
FROM nginx:1.24-alpine
# Borra la configuración por defecto y copia la tuya
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
# Copia los archivos compilados
COPY --from=builder /app/build /usr/share/nginx/html

# Exponemos el 80 (redistribuye de 5173 a 80 para producción)
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
