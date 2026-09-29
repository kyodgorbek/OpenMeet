# 🚀 OpenMeet

**OpenMeet** is a modern Android video conferencing and AI-assisted messaging application built with **Kotlin, Jetpack Compose, Material 3, Clean Architecture, MVI, Koin, Coroutines, Flow, Ktor, Room, DataStore, Stream Video, Stream Chat, and Groq AI**.

The project is designed to demonstrate production-oriented Android engineering around **real-time communication, video calling, messaging, Android lifecycle management, notifications, background execution, OEM compatibility, performance, security, testing, CI/CD, and release engineering**.

---

## ✨ Features

### 📹 HD Video Calling

* Real-time audio/video calling
* Stream Video Android SDK
* WebRTC-based communication through Stream Video
* Jetpack Compose video calling UI
* Stream `VideoTheme`
* Camera and microphone permissions
* Call lifecycle handling
* Audio/video track management
* Call state management
* Runtime permission handling

Stream Video's Android SDK provides the client-side video calling functionality, including camera, microphone, call state, and WebRTC-related functionality.

---

### 💬 Real-Time Chat

* Stream Chat Android SDK
* Real-time messaging
* Compose-based chat UI
* Chat state management using MVI
* Message handling
* Stream connection management
* AI-assisted messaging

Stream Chat is responsible for the real-time messaging infrastructure while OpenMeet manages the application architecture and UI.

---

### 🤖 AI Assistance

OpenMeet integrates Groq AI to provide intelligent conversational assistance.

Current AI architecture:

```text
Android Application
        │
        ▼
     Ktor
        │
        ▼
   AI Backend
        │
        ▼
      Groq
```

For production deployment, the Groq API key should remain on the backend and must not be embedded in the Android application.

---

### 🎨 Modern Android UI

* 100% Jetpack Compose
* Material 3
* Custom application theme
* Responsive UI architecture
* Compose-based navigation
* State-driven UI
* MVI-based feature state

---

### 🔐 Authentication & Security

OpenMeet is designed to use secure server-side authentication for production Stream integration.

Production architecture:

```text
Android
   │
   │ authenticated request
   ▼
OpenMeet Backend
   │
   │ Stream API Secret
   ▼
Stream Token
   │
   ▼
Android Stream SDK
```

Stream's documentation states that production user tokens are generated on the server because token signing requires the Stream API secret. The API secret must not be shipped inside a mobile application.

> **Development note:** A development token or locally configured token can be used while prototyping, but production deployments should use a backend token provider.

---

### 🔒 Runtime Permissions

The application handles runtime permissions for:

* `CAMERA`
* `RECORD_AUDIO`

Permission state is integrated into the calling flow so the application can respond appropriately when permissions are:

* granted
* denied
* revoked
* unavailable

---

# 📱 Android Production Engineering

OpenMeet is intentionally designed around real-world Android engineering problems rather than only demonstrating UI development.

The project targets areas such as:

* Android lifecycle
* background execution
* notifications
* incoming call handling
* foreground services
* network changes
* camera lifecycle
* microphone lifecycle
* audio routing
* battery optimization
* OEM-specific Android behavior
* performance
* crash/ANR investigation
* CI/CD
* release signing
* Google Play release workflow

The goal is to demonstrate the ability to own Android end-to-end in a production consumer application.

---

# 📱 OEM Compatibility

Android devices do not all behave identically.

OpenMeet is designed to account for Android OEM fragmentation, particularly for applications that depend on:

* background execution
* push notifications
* incoming calls
* camera
* microphone
* audio
* network connectivity
* battery management

Target OEM families include:

| OEM      | Examples       |
| -------- | -------------- |
| Google   | Pixel / AOSP   |
| Samsung  | Galaxy         |
| Xiaomi   | Xiaomi / Redmi |
| OnePlus  | OnePlus        |
| OPPO     | Find / Reno    |
| Vivo     | Vivo           |
| Motorola | Moto           |

### Important

OEM compatibility is an engineering target and testing strategy.

A device should only be described as **verified** after testing on the actual physical device.

---

# 🔧 OEM Reliability Areas

The project is designed to investigate and handle issues involving:

### Background execution

* Doze
* App Standby
* OEM battery restrictions
* background process termination
* foreground services

### Notifications

* notification channels
* notification permission
* notification delivery
* incoming call notifications
* notification actions
* deep links

### Calling

* camera lifecycle
* microphone lifecycle
* audio routing
* Bluetooth
* network transitions
* reconnection
* Activity recreation
* application backgrounding

### Device differences

* Android API-level differences
* manufacturer-specific behavior
* device-specific performance
* power management
* notification behavior

---

# 🔔 Notifications

The production architecture supports a dedicated notification layer for:

* chat notifications
* incoming calls
* notification channels
* notification actions
* deep links
* Android 13+ notification permission

The final implementation should keep notification handling separate from individual Compose screens and ViewModels.

---

# ⚙️ Background Execution

OpenMeet is designed to use Android's appropriate background execution mechanisms.

### WorkManager

For:

* deferrable work
* synchronization
* scheduled operations

### Foreground Service

For appropriate user-visible ongoing operations such as active calls where required by the Android platform and application behavior.

The project avoids using unrestricted background services for ordinary application functionality.

---

# 🌐 Network Architecture

The application uses **Ktor Client** for asynchronous networking.

Current networking components include:

```text
network/
├── GroqApi.kt
└── KtorClient.kt
```

Coroutines and Flow are used for asynchronous operations and state propagation.

The architecture is designed to handle:

* connection failures
* request failures
* retry scenarios
* connection state
* asynchronous API responses

---

# 💾 Local Storage

OpenMeet currently uses:

### Room

For structured local application data.

```text
AppDatabase
UserDao
UserEntity
```

### DataStore

For application preferences and settings.

```text
PreferencesManager
```

Examples include:

* application preferences
* theme configuration
* user settings

---

# 🧠 Architecture

The current implementation uses Clean Architecture principles together with MVVM/MVI patterns.

High-level structure:

```text
Presentation
     │
     ▼
ViewModel / MVI
     │
     ▼
Domain Repository
     │
     ▼
Repository Implementation
     │
     ├── Stream
     ├── Ktor
     ├── Room
     └── DataStore
```

The current repository is organized primarily inside the `app` module.

This README intentionally describes the **current implementation** rather than claiming a future multi-module migration that has not yet been completed.

---

# 📂 Current Project Structure

```text
OpenMeet/
│
├── .github/
│   └── workflows/
│       └── android_ci.yml
│
├── app/
│   ├── build.gradle.kts
│   │
│   └── src/main/java/com/yodgorbek/openmeet/
│       │
│       ├── OpenMeetApplication.kt
│       ├── MainActivity.kt
│       │
│       ├── di/
│       │   └── AppModule.kt
│       │
│       ├── domain/
│       │   └── repository/
│       │       ├── CallRepository.kt
│       │       ├── ChatRepository.kt
│       │       └── UserRepository.kt
│       │
│       ├── data/
│       │   ├── repository/
│       │   │   ├── CallRepositoryImpl.kt
│       │   │   ├── ChatRepositoryImpl.kt
│       │   │   └── UserRepositoryImpl.kt
│       │   │
│       │   ├── datastore/
│       │   │   └── PreferencesManager.kt
│       │   │
│       │   └── local/
│       │       ├── AppDatabase.kt
│       │       ├── UserDao.kt
│       │       └── UserEntity.kt
│       │
│       ├── network/
│       │   ├── GroqApi.kt
│       │   ├── KtorClient.kt
│       │   └── StreamTokenUtils.kt
│       │
│       ├── presentation/
│       │   ├── call/
│       │   │   ├── CallScreen.kt
│       │   │   └── CallViewModel.kt
│       │   │
│       │   ├── chat/
│       │   │   ├── ChatScreen.kt
│       │   │   ├── ChatViewModel.kt
│       │   │   └── ChatContract.kt
│       │   │
│       │   ├── home/
│       │   │   └── HomeScreen.kt
│       │   │
│       │   └── login/
│       │       ├── LoginScreen.kt
│       │       └── LoginViewModel.kt
│       │
│       └── ui/
│           ├── navigation/
│           │   └── OpenMeetNavGraph.kt
│           │
│           └── theme/
│               ├── Color.kt
│               ├── Theme.kt
│               └── Type.kt
│
├── gradle/
│   └── libs.versions.toml
│
├── build.gradle.kts
├── settings.gradle.kts
├── local.properties
└── README.md
```

---

# 🛠️ Technology Stack

| Category             | Technology              |
| -------------------- | ----------------------- |
| Language             | Kotlin 2.0+             |
| UI                   | Jetpack Compose         |
| Design               | Material 3              |
| Architecture         | Clean Architecture      |
| Presentation         | MVVM + MVI              |
| Dependency Injection | Koin                    |
| Async                | Kotlin Coroutines       |
| Reactive State       | Flow / StateFlow        |
| Networking           | Ktor Client             |
| Database             | Room                    |
| Preferences          | DataStore               |
| Video                | Stream Video            |
| Messaging            | Stream Chat             |
| WebRTC               | Stream Video/WebRTC     |
| AI                   | Groq                    |
| Build                | Gradle Kotlin DSL       |
| Dependencies         | Version Catalog         |
| CI/CD                | GitHub Actions          |
| Testing              | JUnit / Android testing |

---

# 🔌 Stream Video

Stream Video provides the video calling layer.

The Android client is responsible for the client-side calling experience including joining calls, camera/microphone interaction, call state and rendering.

Stream's Android Video SDK includes Compose UI components and observable state for video calling.

Production authentication should use a backend-generated token.

---

# 💬 Stream Chat

Stream Chat provides the real-time messaging infrastructure.

The application uses the Stream Chat Android SDK for:

* real-time messaging
* chat connections
* messaging UI
* Stream-managed messaging infrastructure

Stream's Android Chat SDK is designed as the client side of the integration, while authentication and other server-side configuration belong on the backend.

---

# 🤖 Groq AI

The application integrates Groq AI for conversational assistance.

The Android application communicates through the Ktor networking layer.

For production:

```text
OpenMeet Android
       │
       ▼
OpenMeet Backend
       │
       ▼
Groq
```

The Groq API key must remain server-side.

---

# 🏗️ Future Multi-Module Architecture

The current application is working in an `app`-centric structure.

A future architectural evolution can introduce dedicated modules such as:

```text
:core:common
:core:designsystem
:core:network
:core:database
:core:datastore
:core:security
:core:notifications
:core:connectivity
:core:performance
:core:oem
:core:testing

:data:stream
:data:remote
:data:local

:domain:auth
:domain:chat
:domain:contacts
:domain:calling
:domain:ai
:domain:profile

:feature:auth
:feature:home
:feature:contacts
:feature:chat
:feature:calling
:feature:ai
:feature:profile
:feature:settings
:feature:notifications
```

This migration should only happen when the boundaries provide genuine architectural value.

The current working application remains the source of truth.

---

# 🧪 Testing Strategy

The project is intended to support multiple testing layers.

### Unit Tests

* ViewModels
* repositories
* use cases
* business logic
* networking
* state reducers

### UI Tests

* Compose UI
* navigation
* permissions
* chat flows
* calling flows

### Integration Tests

* Room
* Ktor
* Stream integration
* authentication

### Real Device Tests

Particularly important for:

* calling
* notifications
* background execution
* camera
* microphone
* audio
* OEM behavior

---

# 📱 Recommended OEM Test Matrix

```text
Google Pixel
Samsung Galaxy
Xiaomi / Redmi
OnePlus
OPPO
Vivo
Motorola
```

For each physical device, validate:

```text
✓ App startup
✓ Login
✓ Chat
✓ Push notifications
✓ Incoming call
✓ Outgoing call
✓ Camera
✓ Microphone
✓ Speaker
✓ Bluetooth
✓ Backgrounding
✓ Screen lock
✓ Network transition
✓ Reconnection
✓ Process recreation
✓ Battery restrictions
```

Do not mark a device as verified until physical testing has been completed.

---

# ⚡ Performance

Performance engineering is an important part of the OpenMeet roadmap.

Recommended tooling includes:

* Android Profiler
* Macrobenchmark
* Baseline Profiles
* startup measurement
* CPU profiling
* memory profiling
* jank analysis

Important user journeys:

```text
Application Startup
Login
Home
Open Chat
Open Call
Join Call
```

Performance numbers should only be published when measured on real hardware.

---

# 🐛 Production Debugging

OpenMeet is designed to make Android-specific issues easier to investigate.

A useful diagnostic report should include:

```text
Device Manufacturer
Device Model
Android Version
SDK Version
Application Version
Network State
Battery Optimization State
Notification Permission
Camera State
Microphone State
Audio Route
Call State
Steps to Reproduce
Expected Result
Actual Result
Logs
```

This is especially useful for OEM-specific issues involving calling, notifications and background execution.

---

# 🔄 CI/CD

The project includes GitHub Actions:

```text
.github/workflows/android_ci.yml
```

The CI pipeline currently supports:

1. Checkout
2. JDK setup
3. Gradle caching
4. Unit testing
5. Debug APK build
6. Artifact upload

Example:

```bash
./gradlew testDebugUnitTest
```

and:

```bash
./gradlew assembleDebug
```

Production release signing should use CI secrets rather than committing credentials to the repository.

---

# 🚀 Getting Started

## Prerequisites

* Android Studio Ladybug or newer
* JDK 17
* Android SDK
* Android device or emulator
* Minimum SDK: 26

For OEM testing, physical devices are recommended.

---

## Configuration

Create `local.properties` for local development.

Example:

```properties
sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

Do not commit secrets.

For production Stream authentication, use a backend token provider.

Stream recommends server-side token generation because the API secret is required to sign user tokens.

---

# 🛠️ Build

Build the debug APK:

```bash
./gradlew assembleDebug
```

Run unit tests:

```bash
./gradlew testDebugUnitTest
```

Install on a connected device:

```bash
./gradlew installDebug
```

Run the application from Android Studio:

```text
Run → Run 'app'
```

---

# 🔐 Security Rules

Never commit:

```text
STREAM_API_SECRET
GROQ_API_KEY
keystore files
signing passwords
access tokens
authentication credentials
```

The Android application should contain only the configuration required by the client SDK.

Production Stream tokens must be generated by a trusted backend.

---

# 🎯 Engineering Goals

OpenMeet is designed to demonstrate practical Android engineering across:

### Android

* Kotlin
* Jetpack Compose
* Material 3
* Android lifecycle
* runtime permissions
* notifications
* background execution
* foreground services
* camera
* microphone
* audio
* networking

### Real-Time Communication

* Stream Video
* Stream Chat
* WebRTC
* real-time state
* connection management
* reconnection

### Architecture

* Clean Architecture
* MVVM
* MVI
* Repository pattern
* Dependency Injection
* Gradle modularization

### AI

* Groq
* Ktor
* asynchronous AI operations
* secure backend integration

### Production Engineering

* OEM compatibility
* notification reliability
* background restrictions
* battery optimization
* performance
* crash/ANR investigation
* real-device testing
* CI/CD
* release engineering

---

# 🏆 Portfolio Objective

OpenMeet is intended to demonstrate that an Android engineer can build and maintain more than a Compose UI.

The project focuses on the difficult parts of Android production development:

```text
Android Lifecycle
       +
Calling
       +
Notifications
       +
Background Execution
       +
OEM Fragmentation
       +
Network Reliability
       +
Performance
       +
Security
       +
Testing
       +
CI/CD
       +
Release Engineering
```

The long-term goal is to evolve OpenMeet into a production-style Android communication platform that can be used as a practical demonstration of senior-level Android engineering.

---

# 📜 License

This project is licensed under the MIT License.

See [LICENSE](LICENSE) for details.

---

# 👨‍💻 Author

**Yodgorbek Komilov**

Senior Android Engineer

Specializing in:

* Kotlin
* Android
* Jetpack Compose
* Kotlin Multiplatform
* AI-powered mobile applications
* Clean Architecture
* MVI
* Real-time communication
* Android performance
* Mobile system design
