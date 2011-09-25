Basic Wars
==========

About
-------
This game is being developed for the http://www.icsscsummerofcode.com/ competition and is loosely based on Advance Wars.

I don't have much more to say right now because I really don't know what the final product will look/feel like.

Movement/Attacking
----------------
This table should help you understand which unit types can move on which cells.

<table border=1>
<tr><th>&nbsp;</th><th>Earth (Brown)</th><th>Water (Blue)</th><th>Swamp (Green)</th><th>Health</th><th>Attack</th><th>Move Radius</th><th>Attack Radius</th></tr>
<tr><th>Soldier</th><td>Yes</td><td>Yes, soldiers swim</td><td>No, too dangerous</td><td>100</td><td>50</td><td>2 Cells</td><td>2 Cells</td></tr>
<tr><th>Tank</th><td>Yes</td><td>No, they sink</td><td>Yes, built for bad terrain</td><td>200</td><td>100</td><td>1 Cell</td><td>2 Cells</td></tr>
<tr><th>Plane</th><td>Yes</td><td>Can't land, no skis</td><td>Can't land, too dangerous</td><td>150</td><td>75</td><td>3 Cells</td><td>2 Cells</td></tr>
</table>

This table should help you understand attacks.



Todo List
---------
* Need popup after each successful attack (damage, isCritical, etc)
* Force Tank and Plane to only attack long range, not short range
* Attack damage should be randomized (like a dice roll for board game)
* Embed images/sounds in jar archive to make distribution easier (one file)
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
* Remove the Lava cell type and changed Tree to Swamp 
* Limit movement on cells (tanks cant go in water, etc)
* Added resume feature so players can continue game
* Add a view with the instructions on how to play
* Added sprite images (soldier, tank, plane)


Beta Testing
------------
If you would like to try out Basic Wars you can download the latest release on the [download page](https://github.com/styfle/Basic-Wars/downloads) and look for the biggest revision number (or newest date).

For the bleeding edge code, you must first install the JDK so you can compile the source code. To compile, first navigate to the source folder and run this command: 

    javac *.java

Then execute the code with the command:

    java BasicWars 

Custom Maps
------------
If you are innovative and decide to make a custom map, all you have to do is open your favorite text editor and start typing. The format looks like this (Oasis map):

```
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEEEEEEEEEEEEEEEE
EEEEEEEEEEETEEEEEEEEEEEEE
EEEEEEEEESSWWWEEEEEEEEEEE
EEEEEEEEEWWWWWWSEEEEEEEEE
EEEEEEEESWWWWWWSEEEEEEEEE
EEEEEEEESWWWWWWSEEEEEEEEE
EEEEEEEEESWWWWWSEEEEEEEEE
EEEEEEEEEESSWSSEEEEEEEEEE
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

The only valid cell types are

    E: Earth (Brown)
	W: Water (Blue)
	S: Swamp (Green)

Rules for custom maps:
* Must have exactly 20 rows
* Must have exactly 25 columns
* Must have at least 10 Earth cells on the left and 10 earth cells on the right
* That means Earth cells in middle-most column don't count toward this

That should be enough to get you started.

