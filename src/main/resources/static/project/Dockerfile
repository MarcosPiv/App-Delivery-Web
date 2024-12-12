FROM node:20-alpine3.21

COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
