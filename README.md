# Demo Android App with Room, ViewModel, LiveData & Kotlin Coroutines

## Objective
Create a reusable CurrencyListFragment. Also create a DemoActivity to showcase the CurrencyListFragment

## Requirements
- CurrencyListFragment receives an array list of CurrencyInfo to create the UI.
- DemoActivity provides 1 dataset, Currency List A of CurrencyInfo to CurrencyListFragment and the dataset should be queried from local DB.
- DemoActivity provides at least 2 buttons to do the demo:
  - a button to load the data and display
  - a button for sorting currency list
- CurrencyListFragment provides a hook of item click listener to the parent
- All the IO operations are done in UI Thread.
- Use multi-threading operation, and deal with concurrency issues when do sorting.

## Components

### Room
Simplifies database work and serves as an access point to the underlying SQLite database (hides SQLiteOpenHelper). The Room database uses the DAO to issue queries to the SQLite database.

#### Entity
Annotated class that describes a database table when working with Room

#### DAO
Data access object. A mapping of SQL queries to functions. DAO calls the methods, and Room takes care of the rest.

#### Repository
A class that is primarily used to manage multiple data sources.

### LiveData
A data holder class that can be observed. Always holds/caches the latest version of data, and notifies its observers when data has changed. LiveData is lifecycle aware. UI components just observe relevant data and don't stop or resume observation. LiveData automatically manages all of this since it's aware of the relevant lifecycle status changes while observing.

### ViewModel
Acts as a communication center between the Repository (data) and the UI. The UI no longer needs to worry about the origin of the data. ViewModel instances survive Activity/Fragment recreation.

## Outputs

### DB Module
Contains all database implementation, including Room dependencies and instrumented tests.

Entities: `CurrencyInfo`

DAOs: `CurrencyInfoDao`

Repositories: `CurrencyInfoRepository`

Databases: `AppDatabase`

Tests: `DbInstrumentedTest`

### App UI

#### DemoActivity

2 main buttons:
- LOAD/SORT button: Used to load initial default data to the CurrencyListFragment. Once data is loaded, the button becomes a SORT button which shows data as alphabetically ascending or descending.
- CLEAR: Resets the list, i.e. clears all items.

1 fragment container for CurrencyListFragment.

Activity passes to fragment:
- DB data to the fragment
- Item click listener

#### CurrencyListFragment
- Receives an array list of CurrencyInfo to create the UI using `update()`.
- Enables the activity to pass an item click listener.

#### Multithreading & Concurrencies
- By default, to avoid poor UI performance, Room doesn't allow issuing queries on the main thread. When Room queries return Flow, the queries are automatically run asynchronously on a background thread.
- A coroutine is a concurrency design pattern used on Android to simplify code that executes asynchronously.
- In Kotlin, all coroutines run inside a CoroutineScope. A scope controls the lifetime of coroutines through its job. When cancelling the job of a scope, it cancels all coroutines started in that scope.
- The AndroidX lifecycle-viewmodel-ktx library adds a viewModelScope as an extension function of the ViewModel class, enabling working with scopes.
- UI is updated as soon as the view model live data are updated asynchronously.

#### UI Testing
Test behaviour of main activity, including button clicks, rendering of list, etc.

```
$ ./gradlew test connectedAndroidTest
```

### References:
- [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Kotlin coroutines on Android](https://developer.android.com/kotlin/coroutines)
- [Save data in a local database using Room](https://developer.android.com/training/data-storage/room/)
- [Android Room with a View - Kotlin](https://developer.android.com/codelabs/android-room-with-a-view-kotlin)
- [Data Binding Library](https://developer.android.com/topic/libraries/data-binding)
- [Automate user interface tests](https://developer.android.com/training/testing/ui-testing/)
- [Build effective unit tests](https://developer.android.com/training/testing/unit-testing)
