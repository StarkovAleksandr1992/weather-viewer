# Weather viewer application

Учебный проект создан на
основании [технического задания](https://github.com/zhukovsd/java-backend-learning-course/blob/main/Projects/WeatherViewer/index.md),
подготовленного [Сергеем Жуковым](https://github.com/zhukovsd).  
Проект реализован с использованием принципов чистой архитектуры.
Для доменных сущностей написаны простые unit тесты.  

## Технолоигии
* Spring boot starter web
* Spring boot starter data jpa
* Thymeleaf
* JUnit
* Mockito
* Bcrypt
* Mapstruct
* Lombok

## Использование

### Добавьте в переменные окружения:

* **$DB_USERNAME и $DB_PASSWORD** - учетные данные для подключения к базе данных.
* **$OPEN_WEATHER_API_KEY** - ключ для доступа к сервисам Open weather map

## Запуск

```bash
docker-compose up
```
