# Stage 1: build
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Stage 2: serve with nginx
FROM nginx:alpine
# Copiamos el build de Vite al folder de nginx
COPY --from=build /app/dist /usr/share/nginx/html
# Opcional: si quieres customizar nginx, añade tu nginx.conf
EXPOSE 80
CMD ["nginx","-g","daemon off;"]
