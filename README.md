# Crypto Wallet Info

## Project Notes

The following features and techniques have been implemented/used:
* Persisting data with `Room` for offline mode
* Network caching with `OkHttp`
* `Retrofit` for Rest API
* Repository pattern for updating the database based on the data from the network
* MVI architecture is used in the feature modules for separating responsibilities, utilising [Orbit2](https://github.com/babylonhealth/orbit-mvi)
* Unit-tests with `JUnit5`, `Mockito` and `Mockito kotlin` for [HomeViewModel](https://github.com/The-Shader/crypto_wallet_info/blob/develop/app/src/test/java/com/fireblade/cryptowallet/HomeViewModelTest.kt)
* Instrumented tests for [WalletDatabase](https://github.com/The-Shader/crypto_wallet_info/blob/develop/repository/src/androidTest/java/com/fireblade/repository/WalletDatabaseTest.kt)
* UI tests with `Espresso` and `Kakao` for [HomeActivity](https://github.com/The-Shader/crypto_wallet_info/blob/develop/app/src/androidTest/java/com/fireblade/cryptowallet/HomeScreenWalkThroughTests.kt)
* CoordinatorLayout for full screen comment scrolling
* `Dagger2` for dependency injection
* `RxJava3` streams for async data loading both from the database and from the network
* Using `AndroidX` libraries

Missing/can be improved:
* Better state handling with init-state and timeouts on failing to load
* Start using styles and themes
* Introduce `Dagger Hilt` for `AssistedInject` and `ViewModelInject`
* More unit-tests(testing the repository) and UI tests
* Bitrise integration for continuous integration and automatic deploy into the Play store
* Firebase integration for crash and user analytics
* Navigation component for handling transitions between screens/activities
* Better animations for the CoordinatorLayout
* More screens for more details
* Expandable items with more details on each transaction

## Screenshots

Initial State | Scrolled State
--------- | -------------
![Feed page image](https://github.com/The-Shader/crypto_wallet_info/blob/develop/initial.png) | ![Detailed page image](https://github.com/The-Shader/crypto_wallet_info/blob/develop/scrolled.png)
