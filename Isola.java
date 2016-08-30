import java.util.Scanner;

public class Isola
{
  private char[][] board = new char[7][7];
  private final String[] moveDefinitions = new String[8];
  private int player1X, player1Y, player2X, player2Y;

  // set up the board
  private void initBoard()
  {
    for (int i=0; i < board.length; i++)
      for (int j=0; j < board[i].length; j++)
        board[i][j] = ' ';

    // player starting positions
    player1X = 6;
    player1Y = 3;
    player2X = 0;
    player2Y = 3;
    board[player1X][player1Y] = '1';
    board[player2X][player2Y] = '2';

    // text descriptions of possible moves
    moveDefinitions[0] = "\'up\'";
    moveDefinitions[1] = "\'upright\'";
    moveDefinitions[2] = "\'right\'";
    moveDefinitions[3] = "\'downright\'";
    moveDefinitions[4] = "\'down\'";
    moveDefinitions[5] = "\'downleft\'";
    moveDefinitions[6] = "\'left\'";
    moveDefinitions[7] = "\'upleft\'";
  }

  // helper method to printBoard
  private void printBorder()
  {
    System.out.println(" --------------------------- ");
  }

  // print an ASCII representation of the board to the console
  private void printBoard()
  {
    for (int i=0; i < board.length; i++) {
      this.printBorder();
      System.out.print("|");

      for (int j=0; j < board[i].length; j++) {
        System.out.print(" " + board[i][j] + " |");
      }

      System.out.print("\n");
    }

    this.printBorder();
  }

  // update user coordinate values
  private void updatePlayerPosition(int player, int x, int y)
  {
    if (player == 1) {
      player1X = x;
      player1Y = y;
    } else {
      player2X = x;
      player2Y = y;
    }
  }

  // ensure desired square is on the board, unvisited, and unoccupied.
  private boolean isLegalMove(int x, int y)
  {
    if (x < 0 || x > 6 || y < 0 || y > 6)
      return false;

    if (board[x][y] == '1' || board[x][y] == '2' || board [x][y] == '@')
      return false;

    return true;
  }

  // display the command list to the user
  private void help()
  {
    System.out.println("Command List: ");
    for (int i=0; i < moveDefinitions.length; i++)
      System.out.println(moveDefinitions[i]);
  }

  // carry out the user's command
  private boolean processInput(String input, int x, int y, int player)
  {
    input = input.replaceAll("\\s", "");
    input = input.toLowerCase();
    if (input.equals("help")) {
      this.help();
      return false;
    }

    // check for legality, then execute moves
    String errMssg = "Illegal move. Please try again.";
    switch (input) {
      case "up":
        if (this.isLegalMove(x-1, y)) {
          this.updatePlayerPosition(player, x-1, y);
          board[x-1][y] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "upright":
        if (this.isLegalMove(x-1, y+1)) {
          this.updatePlayerPosition(player, x-1, y+1);
          board[x-1][y+1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "right":
        if (this.isLegalMove(x, y+1)) {
          this.updatePlayerPosition(player, x, y+1);
          board[x][y+1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "downright":
        if (this.isLegalMove(x+1, y+1)) {
          this.updatePlayerPosition(player, x+1, y+1);
          board[x+1][y+1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "down":
        if (this.isLegalMove(x+1, y)){
          this.updatePlayerPosition(player, x+1, y);
          board[x+1][y] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "downleft":
        if (this.isLegalMove(x+1, y-1)) {
          this.updatePlayerPosition(player, x+1, y-1);
          board[x+1][y-1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "left":
        if (this.isLegalMove(x, y-1)) {
          this.updatePlayerPosition(player, x, y-1);
          board[x][y-1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      case "upleft":
        if (this.isLegalMove(x-1, y-1)) {
          this.updatePlayerPosition(player, x-1, y-1);
          board[x-1][y-1] = board[x][y];
          board[x][y] = '@';
        } else {
          System.out.println(errMssg);
          return false;
        }
        break;
      default:
        System.out.println("That is not a valid command.");
        return false;
    }

    System.out.println("Input is: " + input); // debugging statement
    return true;
  }

  // check if the game has reached a terminal state
  private boolean checkGameOver(int player)
  {
    int x, y;
    if (player == 1) {
      x = player1X;
      y = player1Y;
    } else {
      x = player2X;
      y = player2Y;
    }

    // if the current player has no legal moves, the game is over
    if (isLegalMove(x-1, y))
      return false;
    else if (isLegalMove(x-1, y+1))
      return false;
    else if (isLegalMove(x, y+1))
      return false;
    else if (isLegalMove(x+1, y+1))
      return false;
    else if (isLegalMove(x+1, y))
      return false;
    else if (isLegalMove(x+1, y-1))
      return false;
    else if (isLegalMove(x, y-1))
      return false;
    else if (isLegalMove(x-1, y-1))
      return false;
    else
      return true;
  }

  // get the board ready for gameplay
  private void primeBoard()
  {
    this.initBoard();
    this.printBoard();
  }

  // configure the board, game loop, and play!
  public static void main(String[] args)
  {
    Isola isola = new Isola();
    isola.primeBoard();

    Scanner catScan = new Scanner(System.in);

    boolean gameOver = false;
    boolean player1Turn = true;
    boolean turnFinished;
    String prompt = "";

   // game loop
    while (!gameOver) {
      turnFinished = false;
      prompt = "Type \'help\' for the command list.\n";
      if (player1Turn)
        prompt += "Player 1 to move: ";
      else
        prompt += "Player 2 to move: ";

      while (!turnFinished) {
        System.out.print(prompt);
        String input = catScan.nextLine();
        if (player1Turn)
          turnFinished = isola.processInput(input, isola.player1X, isola.player1Y, 1);
        else
          turnFinished = isola.processInput(input, isola.player2X, isola.player2Y, 2);
      }

      isola.printBoard();
      player1Turn = !player1Turn;
      if (player1Turn)
        gameOver = isola.checkGameOver(1);
      else
        gameOver = isola.checkGameOver(2);
    }

    if (player1Turn)
     System.out.println("Player 2 Wins!");
    else
      System.out.println("player 1 Wins!");
  }
}
