# REST API Spring Boot (Kotlin): Курсы и ученики

Тестовое задание. Реализована авторизация и аутентификация с помощью Spring Security с ролями. Реализован ручной маппинг принимаемых и отдаваемых объектов. Добавлен планировщик, удаляющий старые курсы годовой давности каждый месяц. Тестовые данные загружены.

Добавлены два профиля. `dev` включает базу данных H2; `prod` включает базу данных PostgreSQL. Настраивается в properties.yaml

Развернуто с помощью Docker Compose на порту 9600. 

**Тестовые данные**

| Логин | Пароль     | 
| :-------- | :------- |
|log1 |pp1 |
|log2 |pp2 |
|log3 |pp3 |
|log4 |pp4 |

Также созданы 2 курса с `ID=1` и `ID=2`.

## Документация 

Расписаны все API запросы

### User - пользователи

<details>

### Авторизация
<summary>GET /auth Авторизация</summary>

`GET /auth`

#### Описание
Принимает логин и пароль. По умолчанию доступны несколько профилей по ранее показанным логинам и паролям. Возвращает токен доступа в виде Barear.


#### Request Params

| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `login` | `string` | **Обязательный**. Логин пользователя |
| `password` | `string` | **Обязательный**. Пароль |

#### Response Body (JSON)
```json
200 ОК
{
    "token": "Barear eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJpZCI6MSwic3ViIjoibG9nMSIsImlhdCI6MTcyODUxODEzMCwiZXhwIjoxNzI4NjYyMTMwfQ.qN5o7DbUigON77EHRTZ2oROiaCL3L49AOvugmmH_IUE"
}
```

</details>
<details>
<summary>GET /users Просмотр профиля</summary>

### Просмотр профиля
`GET /users`

#### Описание
Возвращает профиль по токену доступа

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |

#### Response Body (JSON)
```json
200 ОК
{
    "id": "1",
    "username": "Andrey Vasilev",
    "login": "test"
    "type": "Студент",
    "age": 20,
    "description": "A brief description about the user."
}
```

</details>
<details>
<summary>POST /users Создание пользователя</summary>

### Создание пользователя
`POST /users`

#### Описание
Создает пользователя. UserType принимает значениям ADMIN / TEACHER / STUDENT / NONE.  Возвращает ID. Description опционально.

#### Request Body (JSON)
```json
{
    "name": "Andrey Vasilev",
    "login": "test",
    "age": 20,
    "password": "qwerty123",
    "description": null,
    "userType": "STUDENT"
}
```
#### Response Body (Plain text)
```json
200 ОК
1
```

</details>
<details>
<summary>PUT /users Изменение профиля</summary>

### Изменение профиля
`PUT /users`

#### Описание
Принимает тот же объект, что и создание пользователя, можно поменять значения полей.

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |

#### Request Body (JSON)
```json
{
    "name": "Andrey Vasilev",
    "login": "test",
    "age": 20,
    "password": "qwerty123",
    "description": "Добавлено описание!",
    "userType": "STUDENT"
}
```
#### Response Body
```json
200 ОК
```

</details>
<details>
<summary>DELETE /users Удаление пользователя</summary>

### Удаление пользователя
`DELETE /users`

#### Описание
Принимает тот же объект, что и создание пользователя, можно поменять значения полей.

**Обязательно нужно иметь роль ADMIN, чтобы выполнить запрос**

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |

#### Request Params

| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `login` | `string` | **Обязательный**. Логин пользователя, которого нужно удалить |

#### Response Body
```json
200 ОК
```

</details>


### Course - курсы

<details>
<summary>GET /courses/{id} Просмотр ифнормации о курсе</summary>

### Просмотр ифнормации о курсе
`GET /courses/{id}`

#### Описание
Возвращает основную информацию о курсе

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |


#### Response Body (JSON)
```json
200 ОК
{
    "id": 1,
    "name": "Курс по математике",
    "description": "Для 5 классов",
    "dateStart": "2024-10-10T00:00:00.000+00:00",
    "dateEnd": "2024-11-10T00:00:00.000+00:00",
    "creator": {
        "id": 1,
        "name": "Андрей Васильев",
        "age": 20,
        "type": "Cтудент",
        "description": null
    }
}
```

</details>
<details>
<summary>GET /courses/list/{id} Просмотр списка учеников</summary>

### Просмотр списка учеников
`GET /courses/list/{id}`

#### Описание
Возвращает список участников курса (учеников)

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |


#### Response Body (JSON)
```json
200 ОК
[
    {
        "id": 1,
        "name": "Андрей Васильев",
        "age": 20,
        "type": "Cтудент",
        "description": null
    }
]
```

</details>
<details>
<summary>POST /courses Создание курса</summary>

### Создание курса
`POST /courses`

#### Описание
Создает Курс. Дата начала должна быть больше текущей. Дата конца должна быть больше даты начала.

#### Request Body (JSON)
```json
{
    "name": "Курс по математике",
    "description": "Для 5 классов",
    "dateStart": "2024-10-10",
    "dateEnd": "2024-11-10"
}
```
#### Response Body (Plain text)
```json
200 ОК
1
```

</details>
<details>
<summary>PUT /courses Добавление учеников на курс</summary>

### Добавление учеников на курс
`PUT /courses`

#### Описание
Принимает ID курса и ID пользователя, которые добавятся в список учеников курса.

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |

| Key | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `courseId` | `Long` | **Обязательный**. ID конкретного курса |
| `studentId` | `Long` | **Обязательный**. ID конкретного пользователя |

#### Response Body
```json
200 ОК
```

</details>
<details>
<summary>DELETE /courses/{id} Удаление курса</summary>

### Удаление курса
`DELETE /courses/{id}`

#### Описание
Удаляет курс.

#### Headers
| Key             | Value          | Description                    |
|-----------------|----------------|--------------------------------|
| Authorization   | `Bearer <token>`   | JWT токен |


#### Response Body
```json
200 ОК
```
</details>

---

Также написаны тесты для UserController, но пока что они работают до версии проекта ET-9.

---
#### Андрей Васильев
### 2024 / Kotlin / Spring Boot / Maven / PostgreSQL / H2 / Docker

