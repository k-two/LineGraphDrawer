LineGraphDrawer
===============

Write an android app which has a custom view which will draw a simple line graph.
The points for the line will be available in a file.

Ex: 

3 2
4 6
3 10
5 5

Conditions :
1. The points in the file can be large and so the file should not be read in UI thread.

2. No help of third-party libraries. Use only whatever is available in android SDK ( No external jars )

Bonus : 

1) If you are able to use adapter to separate file reading into a different class, so that the view can be re-used.

2) Handle screen rotation and scale the graph so that the graph fits properly. (You need to find max & min and scale it to the view width/height)

3) If you can avoid reading the file again during screen rotation.
