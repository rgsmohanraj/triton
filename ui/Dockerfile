FROM node:16.20.1-alpine3.18 as node
WORKDIR /app
COPY .  .
RUN npm install -g @angular/cli
RUN npm install
RUN ng build  --configuration=dev
FROM nginx:alpine
COPY --from=node /app/dist/triton  /usr/share/nginx/html
EXPOSE 80
