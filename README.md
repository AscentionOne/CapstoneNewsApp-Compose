# Capstone News App - Jetpack Compose

The original of this project is [Capstone News App](https://github.com/AscentionOne/CapstoneNewsApp) which is written in legacy View system + Kotlin. This project is complete rewritten in Compose(a modern toolkit for building native UI) and Kotlin. However, the app has the same functionalities as the original one. The purpose of the project is to adopt best practices of Modern Android Development(MAD) skills and demonstrate that I have the knowledge and can convert a View system project into using Jetpack Compose.

Android team at Google provide great support for popular library e.g. ViewModel, LiveData, Flow, Hilt(for dependency injection)

# Feature

## Screenshots

## 🏠 Architecture

The beauty of Jetpack Compose is it work seemlessly with the architecture that developer is used to in create View system application. The most classic one is MVVM. Since in the original project is using MVVM here I will keep it the same. To know more about MVVM please check [Capstone News App](https://github.com/AscentionOne/CapstoneNewsApp).

## ✨ UI

Compose is a declarative way of creating modern UI as appose to View-based UIs, which is imperetive way. Compose let developer create UIs faster and cleaner so developers can focus more on the business logic and the architecture of the app.

Another is instead of updating the UIs by getting the ids first then mutate them, compose recreate the whole UI based on the different state. This is called recomposition.

For the UI, I am combining View-based UIs and Compose UI. The purpose is to practice how to migrate and transition to Compose from traditional Views. Compose is a new way of building modern UI in Android. Compose have interoperability APIs to let developers add Compose to View-based design easier. For the Views, I am using pure Activity with XML layout.

## State

### Uni Direction Data Flow

## Navigation

Persistance data, dependency injection, networking is the same as original project

## 🧪 Testing

Compose Navigation testing

## Future work:

- Update UI to Material 3 Design specification

# License

MIT License. See [license](LICENSE) for more information.
