call mvn clean package -Dmaven.test.skip=true

call docker build -t payments .

call docker-compose up -d rabbitMq

timeout /t 40

docker-compose up -d payments

call mvn test

call docker-compose down