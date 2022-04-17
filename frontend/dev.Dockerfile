FROM node:lts as installcache
WORKDIR /app
ADD package.json package.json
RUN npm install
FROM node:lts
WORKDIR /app
COPY --from=installcache /app ./
ADD . . 
ENTRYPOINT ["npm", "start", "--host 0.0.0.0"]

