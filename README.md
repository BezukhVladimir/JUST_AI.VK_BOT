# VK BOT (JUST AI)

## Тестовое задание
Необходимо выполнить интеграцию с BotAPI VK: https://vk.com/dev/bots_docs

В рамках задания нужно создать бота, который будет цитировать присланный ему текст.

Минимальные требования к реализации:
- нужно приложить ссылку на git репозиторий с исходниками;
- реализация должна быть на языке Kotlin или Java. Использование Kotlin будет преимуществом, но не является обязательным;
- должен использоваться фреймворк Spring Boot;
- нельзя использовать готовые библиотеки для реализации VkApi.

Все остальные критерии (такие, как структура кода, покрытие тестами, конфигурация, документация и др) являются опциональными, но будут также оцениваться.

При реализации может потребоваться использование внешних https адресов для локальной машины. Для этого можно использовать ngrok.

## Инструменты

- Kotlin, Spring Boot
- JUnit 5, MockK, AssertJ
- SLF4J
- Gradle, Docker

## Установка и развертывание

1. **Клонируйте репозиторий**:
```bash
git clone <URL_репозитория>
cd <имя_папки_репозитория>
```

2. **Задайте переменные окружения в файле конфигурации**
` src/main/resources/application.yml `

![Confirmation token](https://raw.githubusercontent.com/BezukhVladimir/JUST_AI.VK_BOT/main/docs/confirmation_token.jpg) <p>
![Access token](https://raw.githubusercontent.com/BezukhVladimir/JUST_AI.VK_BOT/main/docs/access_token.jpg) <p>

```yml
vk:
    confirmation-token: <your_confirmation_token>
    access-token: <your_access_token>
```

3. **Соберите Docker-образ:**
```bash
docker build -t vkbot .
```

4. **Запустите контейнер:**
```bash
docker run -p 8080:8080 vkbot
```

После запуска контейнера, вы сможете получить доступ к вашему боту по адресу http://localhost:8080. Для использования внешних https адресов для локальной машины можно использовать [ngrok](https://ngrok.com/).

## License
<a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />These projects are licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.

Developed by <b><a href="https://github.com/BezukhVladimir">Bezukh Vladimir</a></b>. All right reserved.
