FROM node:19.9.0

WORKDIR /usr/src/app

# Install app dependencies
COPY package*.json ./

# RUN npm install
# If you are building your code for production
RUN npm ci --omit=dev

ENV PORT=3000
ENV MONGODB_URI='mongodb://bn_parse:bitnami123@host:port/bitnami_parse'

EXPOSE 3000

COPY . .

CMD [ "node", "app.js" ]

