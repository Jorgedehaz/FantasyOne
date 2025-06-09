# 1) Build stage
FROM node:18-alpine AS builder
WORKDIR /app

COPY package*.json ./
ENV CI=true
RUN npm ci

COPY . .
RUN npm run build

# 2) Nginx stage
FROM nginx:1.24-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
