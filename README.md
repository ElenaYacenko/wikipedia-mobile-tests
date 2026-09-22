# Wikipedia Mobile Tests

Автотесты для onboarding-экранов Wikipedia (Appium + JUnit 5 + Selenide).

## Стек технологий

- Java 21
- Appium Java Client 9.3.0
- Selenide 7.5.0 + Selenide Appium
- JUnit 5
- Allure
- Owner (для конфигов)

## Требования

- Java 21
- Node.js 20+
- Appium Server 3.x
- Android Studio + эмулятор Pixel 4 (API 30)
- ADB в PATH

## Установка

1. Склонируй репозиторий:
   ```bash
   git clone https://github.com/ElenaYacenko/wikipedia-mobile-tests.git
   cd wikipedia-mobile-tests
   ```
   
2. Скачай APK Wikipedia:
   https://github.com/wikimedia/apps-android-wikipedia/releases

Положи файл как src/test/resources/apps/wikipedia.apk

3. Настрой конфиги:
- src/test/resources/config/emulation.properties — для эмулятора
- src/test/resources/config/real.properties — для реального устройства
- src/test/resources/config/browserstack.properties — для BrowserStack
- src/test/resources/config/secret.properties — секреты (не коммитится)

Шаблоны смотри в *.example файлах.