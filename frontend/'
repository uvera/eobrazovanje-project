FROM node:lts-alpine as builder
RUN mkdir /app
COPY package.json /app
WORKDIR /app
RUN npm install

FROM node:lts-alpine
COPY --from=builder /app .
WORKDIR /app
ENTRYPOINT ["npx", "ng serve --host 0.0.0.0"]

