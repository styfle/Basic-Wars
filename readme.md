Basic Wars
==========

About
-----
This game is being developed for the http://www.icsscsummerofcode.com/ competition and is loosely based on Advance Wars.

I don't have much more to say right now because I really don't know what the final product will look/feel like.

Todo List
---------
* Definitely need some royalty-free sprites soon (soldier, tank, planes)
* Add a view with the instructions on how to play
* Resize cells/sprites on the fly when the window/frame changes sizes
* Maybe make the map bigger than the screen and provide "panning" the view
* Lastly, add a cool intro menu and awesome music
* Maybe, redirect System.err to a log file for release code

Completed
----------
* Differentiate cells by the type of land/water it represents
* Provide a way to load arbitrary maps
* Add multiple menus to select players/map/etc.
* Add a buy view so players can buy their units
* Implement simple movement and attack
* Implement game logic such as rotating turns, for move and attack


Beta Testing
------------
If you would like to try out Basic Wars you can download the latest release on the "download page":https://github.com/styfle/Basic-Wars/downloads and look for the biggest revision number (or newest date).

For the bleeding edge code, you must first install the JDK so you can comiple the source code. To compile, first navigate to the source folder and run this command: 

    javac *.java

Then execute the code with the command:

    java BasicWars 

Custom Maps
------------
If you are innovative and decide to make a custom map, all you have to do is open your favorite text editor and start typing. The format looks like this

```
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEETEEEEEEEEEEEEE
EEEEEEEEETTWWWEEEEEEEEEEE
EEEEEEEEEWWWWWWEEEEEEEEEE
EEEEEEEEEWWWWWWEEEEEEEEEE
EEEEEEEEEWWWWWWEEEEEEEEEE
EEEEEEEEEEWWWWWEEEEEEEEEE
EEEEEEEEEEEEWTTEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
```

That is the actual source code for the 'Oasis' map. The cell types are

    E: Earth
	W: Water
	T: Tree
	L: Lava

That should be enough to get you started.
