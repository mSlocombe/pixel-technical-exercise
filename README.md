**-- Installation Requirements --**  
The app has a minimum requirement of Android 7.0 as this will run on approximately 99.2% of devices as of December 1st 2025.

**-- How the app works --**


**-- Technical decisions--**  
At the request of "No 3rd party frameworks - we want to see what you can do!":
- 3rd party libraries have been kept to a minimum, only introducing Ktor for networking, Jetpack Datastore for persistent storage, and Jetpack Compose for UI
- I have intentionally restricted libraries to those provided by JetBrains (Kotlin libraries) and Jetpack (Android libraries)

**:app:mocking module**  
- I have created my own mocking module which contains easily changable version of each class to aid with testing. (This would normally be achieved using MockK or other mocking libraries)
- Alongside the mock versions, the app uses depedency injection to allow implementation replacement creating fast, isolated tests, that can be adjusted as required. (Can be achieved with Dagger/Hilt)

- Card designs were created using Figma
[!img]()
