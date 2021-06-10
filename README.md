# MovieBag

Features:
1. Paginated Popular Movies
2. Now Playing Movies with Circle Indicator Dots and auto swiping feature
3. Convert incoming date to desirable format
4. Change "en" the language code to language name using Local
5. Parallax Scrolling with CollapseToolbar in movie details screen
6. Fragment with transaction animations
7. Recycler views or list with animation feature while scrolling
8. Validations for N/A when data is not available
9. Show no-data image when reviews or cast are not found
10. Lottie animated preload
11. AndroidX components for designing
12. Multi-threading concept used with Volley library for network API calls
13. Material Dialog to show error custom message and network errors
14. Exception Handling
15. <b>Model View Presenter</b> design architecture used for clean code
16. Attached a Logo (for demo Purpose)


Decisions:
1. Used 10 stars to show rating as TheMovieDB providing rating out of 10, not 5
2. Changed date formate for better UI
3. Used dotted ViewPager Indicator to fit in the UI

Assumptions
1. TheMovieDb returning data in pagination causing recycler view scroll stuck (not smooth at times) - added 
logic to not to call next page till current data is recieved. 