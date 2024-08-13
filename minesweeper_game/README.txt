=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: huolivia
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: My board is represented as a 2D Array. With this, there are two 2D
  Arrays within my game: one within the model and one within the board. The first
  2D Array has a type Tile which stores within any specific tile is revealed, a mine
  is flagged, and the number of mines adjacent to the tile. The 2D Array makes it
  easy to iterate through the tiles and the type works nicely to translate the model
  into the actual game. The second 2D Array is the type TileObj which stores the
  graphics of the tiles in relation to their state. This 2D Array also makes it easy
  to iterate and draw the tiles. The TileObj type is used to make the image drawing
  process easier.

  2. File I/O: My game incorporates the File I/O concept as the game state is stored
  in a file. The file stores the board three times, each time displaying a different
  feature of the board. The first board displays the number of mines adjacent to each
  tile (with the mines displaying a -1). The second board displays which tiles are
  flagged (flagged tiles are written as 1). The third board displays which tiles have
  been revealed (the revealed tiles are written as 1). The rows of the board are stored
  as rows within the file and the spaces between numbers display the columns. Through
  the file, the game can be saved, and the user can load the last saved game. The game
  also catches and handles IOExceptions.

  3. JUnit Testable Component: For this concept, I tested my game model separately from
  the graphics components. That is, I utilized JUnit tests to test aspects of my game
  model. These were appropriate uses of the concept as the tests weren't related to the
  graphics connected to the game and purely tested the model. My tests were also of the
  correct style and were unique in function. Lastly, I included edge cases such as
  counting adjacent mines for cells that are on the edge of the board.

  4. Recursion: I used recursion in the case where the player reveals a tile that is
  not adjacent to any mines. When the player does this, the game reveals adjacent tiles
  until it reaches tiles that are adjacent to a mine. This is recursively done in all
  directions, checking if there is a mine adjacent to squares, with the base case being
  when the square is adjacent to a mine. This aspect of the game is best implemented
  using recursion, as the mines are placed randomly so there is no set amount of
  iteration. Additionally, the recursion does not cause an infinite loop because it
  takes the edge of the board into account.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

My code has 7 classes: GameBoard, Instructions, Minesweeper, RunInstructions, RunMinesweeper,
Tile, and TileObj.

GameBoard instantiates a Minesweeper object (the model). As the user clicks the game board,
the model is updated. Whenever the model is updated, the game board repaints itself and
updates its status JLabel to reflect the current state of the model. This class represents
the controller and view portion of the Model-View-Controller framework.

Instructions is a JPanel that has the instructions of the game written on it.

Minesweeper contains the model of the game. It is completely independent of the view and
controller. When the file is run, a game of minesweeper is printed out without creating
a Java Swing object.

RunInstructions sets up the top-level frame and widgets for the Instructions portion of the
game.

RunMinesweeper sets up the top-level frame and widgets for the GUI. In a Model-View-Controller
framework, it initializes the view, implements a bit of controller functionality through the
buttons, and then instantiates a GameBoard.

Tile is a type that has several private variables that define a tile in the game model.
The variables are mine, flagged, revealed, and numOfMines. The 2D Array in the Minesweeper
class is of type Tile.

TileObj is a type that represents a tile in the game. Within the GameBoard, a TileObj has a
position, size, and an image based on the values of their private variables. The 2D Array in
the GameBoard is of type TileObj.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

There were a couple stumbling blocks while implementing my design. The first major obstacle
was figuring out how to translate the model to the view. At first, I was trying not to create
another 2D Array within GameBoard but then realized that I had to to make every tile unique.
With this, initially, my game moved extremely slow because I was recreating each TileObj every
time that the board was repainted. Eventually, I changed this by creating all the TileObjs once
for a game and then updating specific tiles in the MouseListener. With this, the repaint function
drew all the tiles.

Another stumbling block that I encountered was when implementing the File I/O section of my game.
I was struggling with the creating a save and load save button. Whenever I clicked the save button,
it loaded the saved game (and it doesn't write anything to the file), but when I clicked the load
button, it didn't do anything. I was stuck on this issue for a couple hours before I realized it
was a simple mistake with adding the ActionListener to the wrong button. Oops!

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
There is a pretty good separation of functionality. However, I wonder if there was a way to make
the functions less redundant and have only one 2D Array. Overall, I feel as though my design is
done fairly well. It's pretty disorganized and some parts of the code are inconsistent; if given
more time, I would definitely clean up the code and add more comments to add readability.
Furthermore, my private state is encapsulated relatively well; one must use methods to adjust the
variables. However, I'm unsure if I should've made the Tiles adjustable. It might not be
sufficiently encapsulated but does make testing easier.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
    N/A