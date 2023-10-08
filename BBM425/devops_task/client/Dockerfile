FROM node:18.17.1-slim AS client-builder
WORKDIR /app/client
COPY client /app/client
RUN npm install
CMD npm run dev