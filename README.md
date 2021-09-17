# task-PutInByte
Тестовое приложение управления пользователями.

Есть возможность запустить по ссылке:

При запуске автоматически создается пользователь с правами админа

login: admin@gmail.com

password: admin

https://putinbyte.herokuapp.com 

## Поддерживаемые запросы:

http://localhost:8080/ - основная страница со списком пользователей

http://localhost:8080/admin    -профиль

http://localhost:8080/edit    -изменить профиль

http://localhost:8080/newPass    -новый пароль

http://localhost:8080/admin/addUser - добавить пользователя

http://localhost:8080/admin/user/update/{id} - изменить пользователя

http://localhost:8080/admin/user/delete/{id} - удалить пользователя


## Для проекта использовал:

*Spring Boot

*Bootstrap

*Thymeleaf

*h2-database

