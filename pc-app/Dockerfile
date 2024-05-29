FROM node

WORKDIR /app

COPY package.json .
COPY yarn-lock.json .

RUN rm -rf node_modules
RUN yarn

COPY . .

## EXPOSE [Port you mentioned in the vite.config file]

EXPOSE 3000/tcp

CMD ["npm", "run", "dev"]