# ZomatoSearchMvvm
This app uses zomato api for searching restaurants and displaying restaurant list categorised by cuisine.

Technology Stack : 
Kotlin
MVVM architecture
Live Data
View Model
Repository
Zomato Search Api
RxJava (for making network api calls)
Picasso
Google Location services(For detecting users latitude and longitude)
How to use:
-Install apk from above link.
-After opening it will ask for location permissions. Kindly allow it.
-It will take 5-10s to fetch your current location.
-Then you will be able to view a list of restaurants listed on zomato in your locality categorized by cuisine.
-You can also search for particular cuisines, dish or restaurant names and it will show results as per your query categorized by cuisine type.
 
If you want to run source code on your system. Then you need to clone it from the above github link. Then replace API_KEY value present in app level build.gradle file with your zomato api key (which is available for free after registering on this website : https://developers.zomato.com/api ). Then rebuild the project. Select your device and run.

Screenshots:
![Screenshot1](https://i.imgur.com/SMmYzd6.jpeg)
![Screenshot2](https://i.imgur.com/3nYNP0P.jpg)
