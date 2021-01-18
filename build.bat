call mvn clean package -Dmaven.test.skip=true
call docker build -t payments .
call docker-compose up -d --build
timeout /t 10
call mvn test
call docker-compose down