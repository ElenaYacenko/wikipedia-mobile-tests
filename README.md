# Wikipedia Mobile Tests

> Автотесты для onboarding-экранов и поиска Wikipedia.  
> Стек: **Appium + Selenide + JUnit 5 + Allure + Owner**.

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![Appium](https://img.shields.io/badge/Appium-9.3.0-blue)](https://appium.io/)
[![Selenide](https://img.shields.io/badge/Selenide-7.5.0-green)](https://selenide.org/)
[![JUnit5](https://img.shields.io/badge/JUnit-5.11.3-red)](https://junit.org/junit5/)
[![Allure](https://img.shields.io/badge/Allure-2.29.1-yellow)](https://allurereport.org/)

---

## 📋 Содержание

- [Стек технологий](#-стек-технологий)
- [Требования](#-требования)
- [Установка](#-установка)
- [Настройка конфигов](#-настройка-конфигов)
- [Запуск тестов](#-запуск-тестов)
   - [На эмуляторе](#на-эмуляторе)
   - [На реальном устройстве](#на-реальном-устройстве)
   - [На BrowserStack](#на-browserstack)
- [Структура проекта](#-структура-проекта)
- [Отчёты Allure](#-отчёты-allure)
- [Полезные команды](#-полезные-команды)

---

## 🛠 Стек технологий

| Технология | Версия | Назначение |
|------------|--------|------------|
| **Java** | 21 | Язык разработки |
| **Appium Java Client** | 9.3.0 | Управление мобильным драйвером |
| **Selenide** | 7.5.0 | Обёртка над WebDriver |
| **Selenide Appium** | 7.5.0 | Интеграция Selenide с Appium |
| **JUnit 5** | 5.11.3 | Фреймворк для тестов |
| **Allure** | 2.29.1 | Отчёты |
| **Owner** | 1.0.12 | Чтение `.properties` конфигов |
| **Gradle** | 8.5 | Сборка |

---

## 📦 Требования

Перед началом убедись, что установлено:

- ☕ **Java 21** — [скачать](https://adoptium.net/)
- 🟢 **Node.js 20+** — [скачать](https://nodejs.org/)
- 📱 **Appium Server 3.x** — установка: `npm install -g appium`
- 🎨 **Android Studio** + эмулятор **Pixel 4 (API 30)**
- 🔧 **ADB** в `PATH` (из `Android/Sdk/platform-tools`)
- 🔍 **Appium Inspector** (опционально, для отладки локаторов) — [скачать](https://github.com/appium/appium-inspector/releases)

**Проверка установки:**

```bash
java -version           # должно быть 21+
node --version          # должно быть 20+
appium --version        # должно быть 3.x
adb devices             # должен показать эмулятор или устройство
```

---

## 🚀 Установка

### 1. Клонируй репозиторий

```bash
git clone https://github.com/ElenaYacenko/wikipedia-mobile-tests.git
cd wikipedia-mobile-tests
```

### 2. Скачай APK Wikipedia

Перейди на [страницу релизов](https://github.com/wikimedia/apps-android-wikipedia/releases) и скачай последний **alpha** APK.

Положи файл сюда:

```
src/test/resources/apps/wikipedia.apk
```

> ⚠️ APK **не коммитится** в Git (он в `.gitignore`). Каждый разработчик скачивает его локально.

### 3. Установи зависимости

```bash
./gradlew build -x test
```

---

## ⚙️ Настройка конфигов

Все конфиги лежат в `src/test/resources/config/`. Шаблоны — в файлах `*.example`.

| Файл | Назначение | Коммитится? |
|------|-----------|-------------|
| `default.properties` | Общие настройки (appPackage, appActivity) | ✅ Да |
| `emulation.properties` | Для эмулятора (udid, deviceName) | ✅ Да |
| `real.properties` | Для реального устройства | ✅ Да |
| `browserstack.properties` | Для BrowserStack (device, osVersion) | ✅ Да |
| `secret.properties` | Секреты (логины, ключи, app ID) | ❌ **Нет** |

### Создание `secret.properties`

Скопируй `secret.properties.example` → `secret.properties` и заполни:

```properties
browserstack.user=ТВОЙ_ЛОГИН
browserstack.key=ТВОЙ_ACCESS_KEY
android.app=bs://ТВОЙ_APP_ID
```

> 🔒 **Важно:** `secret.properties` в `.gitignore` — он не попадёт в GitHub.

---

## ▶️ Запуск тестов

### На эмуляторе

**1. Запусти эмулятор** в Android Studio (AVD Manager → ▶).

**2. Запусти Appium Server** в отдельном окне:

```bash
appium server --base-path /wd/hub
```

**3. Запусти тесты:**

```bash
./gradlew test -DdeviceHost=emulation
```

---

### На реальном устройстве

**1. Подключи телефон** по USB, включи **USB-отладку** в настройках разработчика.

**2. Проверь, что устройство видно:**

```bash
adb devices
```

**3. Впиши `udid` в `real.properties`:**

```properties
udid=ТВОЙ_UDID_ИЗ_ADB
deviceName=ТВОЕ_УСТРОЙСТВО
platformVersion=ТВОЯ_ВЕРСИЯ_ANDROID
```

**4. Запусти тесты:**

```bash
./gradlew test -DdeviceHost=real
```

---

### На BrowserStack

**1. Загрузи APK в BrowserStack:**

- Зайди на [app-automate.browserstack.com](https://app-automate.browserstack.com/)
- **App Automate → App Management → Upload App**
- Загрузи `wikipedia.apk`
- Скопируй `bs://...` ID

**2. Впиши ID и credentials в `secret.properties`:**

```properties
browserstack.user=ТВОЙ_ЛОГИН
browserstack.key=ТВОЙ_ACCESS_KEY
android.app=bs://ТВОЙ_APP_ID
```

**3. Запусти тесты:**

```bash
./gradlew test -DdeviceHost=browserstack
```

---


## 📊 Отчёты Allure

После запуска тестов сгенерируй Allure-отчёт:

```bash
./gradlew allureServe
```

Откроется браузер с интерактивным отчётом:

- ✅ Визуализация шагов (`@Step`)
- 📸 Скриншоты при падении
- 📉 Графики и статистика
- 🔍 Детали ошибок

![Снимок экрана 2026-09-23 130904.png](images/%D0%A1%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA%20%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-09-23%20130904.png)
![Снимок экрана 2026-09-23 130848.png](images/%D0%A1%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA%20%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202026-09-23%20130848.png)

**Простой HTML-отчёт** (без Allure) доступен здесь:

```
build/reports/tests/test/index.html
```

---

## 🔧 Полезные команды

### Очистка данных Wikipedia (для повторного онбординга)

```bash
# PowerShell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" -s emulator-5554 shell pm clear org.wikipedia.alpha

# cmd
"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" -s emulator-5554 shell pm clear org.wikipedia.alpha

# bash / Mac
adb -s emulator-5554 shell pm clear org.wikipedia.alpha
```

### Запуск конкретного теста

```bash
./gradlew test -DdeviceHost=emulation --tests "wikipedia.tests.OnboardingTest"
./gradlew test -DdeviceHost=emulation --tests "wikipedia.tests.SearchTest"
```

### Переопределение параметров через `-D`

```bash
./gradlew test -DdeviceHost=browserstack \
    -Dbrowserstack.user=xxx \
    -Dbrowserstack.key=yyy
```

### Просмотр зависимостей

```bash
./gradlew dependencies --configuration testRuntimeClasspath
```

---

## 👤 Автор

**Elena Yacenko**

- GitHub: [@ElenaYacenko](https://github.com/ElenaYacenko)

---

## 📄 Лицензия

Этот проект создан в учебных целях. Приложение Wikipedia принадлежит [Wikimedia Foundation](https://www.wikimedia.org/).