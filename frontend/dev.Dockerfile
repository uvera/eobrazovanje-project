FROM node:lts
WORKDIR /app
ADD package.json package.json
RUN npm install
ADD . /app
ENTRYPOINT ["npm", "start"]

