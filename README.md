## Build tools & versions used
Gradle 7.2

## Steps to run the app
Download the .zip file, and run the app in Android Studio

## What areas of the app did you focus on?
I mostly focused on creating an MVVM application that doesn't utilize too many network calls, and demonstrates
basic understanding of how to utilize observables, UI and background thread operations / error handling, dependency injection, and basic unit testing.

I also added error handling states for empty and malformed JSON.

For the most part, I focused on building the basic building blocks of an end-to-end app that can display information from the internet.

- Architecture - MVVM with Architecture Components (LiveData, Repository, ViewModel)
- Dependency Injection - Dagger Hilt
- Networking - Retrofit & Moshi
- Single Activity Architecture
- Unit Testing - Mockito & JUnit

## What was the reason for your focus? What problems were you trying to solve?
I focused mostly on the MVVM aspect of the application because it's important to do it for enabling easier unit testing
across the board (testing ViewModel instead of logic in Activity / Fragments), helps survive configuration changes, minimizes network calls due
to its lifecycle, and more.

Some layers on top that were required to get it to work elegantly such as dependency injection with Dagger Hilt and adding
a Repository for separation of responsiblity / concern.

## How long did you spend on this project?
About 5.5 hours. It took me some time to re-learn Dagger Hilt and ran into some hiccups in the middle.

## Did you make any trade-offs for this project? What would you have done differently with more time?
I don't think I made any trade-offs for this project in terms of performance and scalability. With more time though, I would definitely look
into utilizing Flow and Coroutines instead. However, I'm much more familiar with Architecture Components and LiveData at the moment, so opted to use that for the time being.

## What do you think is the weakest part of your project?
I think my unit testing is pretty weak for the project. I was focused mostly on building out the moving parts of the project, but I did
get basic unit testing validation for both valid, malformed, and empty JSON cases.

## Did you copy any code or dependencies? Please make sure to attribute them here!
Yes, I copied the Resource wrapper in my code from Google at https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt

## Is there any other information you’d like us to know?
Not at the moment