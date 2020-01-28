
Project Import Instructions:

     Start Android Studio and close any open Android Studio projects.
1. From the Android Studio menu click File > New > Import Project.
2. Select the destination folder and click Next.

N.B : recommended latest android studio version.


About


MVVM(Model View ViewModel) is the architecture pattern used in this project.

It provides maintainability, testability and extensibility, in general, a more clean and maintainable codebase.

Advantages of MVVM

1. Separation of concerns – Typically, there is a connection between the user interface and application logic, resulting in change-resistant, brittle code. MVVM cleanly separates the user interface from the application logic. Divorcing one from the other improves application maintenance. It also makes application evolution easier, thereby reducing the risk of technological obsolescence.

2. Eliminates the need for application redesign – User interfaces become outdated. However, upgrading a user interface often means a complete application redesign. Since MVVM separates the user interface from application logic, upgrading involves minimal effort. You can select a template of your choosing, and then plug it into your application.

3. Streamlines the data sending process – When you click the “Save” button, an application resubmits all of the data on the screen. MVVM streamlines this process by sending only the necessary data, thereby boosting performance.

So MVVM treats both Activity classes and XML files as views, and ViewModel classes are where we write your business logic. It completely separates an app's UI from its logic.


Library Used:

Retrofit,gson,Room,Recyclerview etc

3rd party Libraries not Used.














