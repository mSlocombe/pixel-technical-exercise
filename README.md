**-- Installation Requirements --**  
The app has a minimum requirement of Android 7.0 as this will run on approximately 99.2% of devices as of December 1st 2025.
The app was created using Android Studio Panda 4 | 2025.3.4

**-- How the app works --**  
1. When the user enters the app, a request is made to http://api.stackexchange.com/2.2/users?page=1&pagesize=20&order=desc&sort=reputation&site=stackoverflow to retrieve the top 20 users by reputation.
2. The users are mapped to a StackOverflowUser; this is our domain model for api.stackexchange.com when we query site=stackoverflow
3. Meanwhile, the app users followed users are retrieved from Datastore
4. The StackOverflowUser objects are then combined with the DataStore followers and mapped to UserCardState objects to create the cards on the UserListScreen or an Error state if the API request failed
5. The UserListScreen displays either the user cards, or an error message telling the app user that "Something went wrong!"

https://github.com/user-attachments/assets/cdbeb863-330b-4ece-be5e-5180b4b7bf79



**-- Technical decisions--**  
At the request of "No 3rd party frameworks - we want to see what you can do!":
- 3rd party libraries have been kept to a minimum, only introducing Ktor for networking, Jetpack Datastore for persistent storage, and Jetpack Compose for UI
- I have intentionally restricted libraries to those provided by JetBrains (Kotlin libraries) and Jetpack (Android libraries)
- I chose Ktor as I have the most experience with this library, and compared to Retrofit, Ktor provides a pure-Kotlin interface.
- I chose Jetpack Datastore as this task has a very simple storage requirement of followed user IDs, and Jetpack Datastore allows for that with minimal setup.

**:app:mocking module**  
- I have created my own mocking module which contains easily changable version of each class to aid with testing. (This would normally be achieved using MockK or other mocking libraries)
- Alongside the mock versions, the app uses dependency injection to allow implementation replacement creating fast, isolated tests, that can be adjusted as required. (Can be achieved with Dagger/Hilt)

- Asynchronous image requesting is used for profile pictures to reduce the load time (I would normally use Coil for this)

- Although only available in English, Android resources have been used allowing for regionalisation.

- Card designs were prototyped in Figma
<img width="472" height="463" alt="figma_cards" src="https://github.com/user-attachments/assets/f3b5980c-28a0-44be-91a2-9a4ede63e392" />
