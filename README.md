# Capstone News App - Jetpack Compose

The original of this project is [Capstone News App](https://github.com/AscentionOne/CapstoneNewsApp) which is written in legacy View system + Kotlin. This project is complete rewritten in Compose(a modern toolkit for building native UI) and Kotlin. However, the app has the same functionalities as the original one. The purpose of the project is to adopt best practices of Modern Android Development(MAD) skills and demonstrate that I have the knowledge and can convert a View system project into using Jetpack Compose.

Android team at Google provide great support for popular library e.g. ViewModel, LiveData, Flow, Hilt (dependency injection)

# Feature

## Screenshots

<img src="/images/screenshots/screenshot_1.png" alt="Screen shot of Capstone News App" height="480"/>&emsp;<img src="/images/screenshots/screenshot_2.png" alt="Screen shot of Capstone News App" height="480"/>

## 🏠 Architecture

The beauty of Jetpack Compose is it work seemlessly with the architecture that developer is used to in create View system application. The most classic one is MVVM. Since in the original project is using MVVM here I will keep it the same. To know more about MVVM please check [Capstone News App](https://github.com/AscentionOne/CapstoneNewsApp).

Here I follow the common architecture principle recommended by Google.

Separation of concern: keep the business logic in the data layer(repository) and UIs in the UI layer(Composable functions)

Single source of truth(SSOT): in this application I am using Room database as my SSOT. It is best practice to update the UIs only from immutable SSOT data. This keeps the data centralized and make changes to the data traceable and make bugs easier to spot.

Unidirectional data flow: unidirectional data flow (UDF) is a design pattern in which **state flows down** and **events flow up**. Where the updated UI state is reflected in the UI layer and the incoming event updates the UI state in **state holder**(ViewModel). This makes state decouple from the composable functions and makes it easier to test. Also less likely to create bug due to inconsistent state(state is updated in the composable function).

For more information check [here](https://github.com/AscentionOne/android-documentation/blob/master/android_notes.md#unidirectional-data-flow)

## ✨ UI

Compose is a declarative way of creating modern UI as appose to View-based UIs, which is imperetive. Compose let developer create UIs faster and cleaner so developers can focus more on the business logic and the architecture of the app. Android studio also provides **Compose Preview** (since Dolphin), **Multipreview** and **Live Edit** (Electric Eel) functionalities.

- Compose preview: let developer see the compose in the design view without needed to run the app. It also provides multiple options for custom configuration of the device.
- Multipreview: let developer quickly create group of previews for multiple device configurations. For example, adapt for different screen size.
- Live edit: in Dolphin version developer need to manually refresh the preview after making changes in composable function, but now in Electric Eel version preview update automatically.

Above all features makes developer iterate through UIs faster.

## State

Updating UIs is a bit different in Compose, instead of updating the UIs by getting the view ids first then mutate them, compose recreate the whole UI based on the different state. This is called recomposition. Her I am following the

### Uni Direction Data Flow

## Navigation

Persistance data, dependency injection, networking is the same as original project

## 🧪 Testing

Compose Navigation testing

## Future work:

- Update UI to Material 3 Design specification

# License

MIT License. See [license](LICENSE) for more information.
