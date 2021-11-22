package background;

import background.grids.HorizontalGrid;
import background.grids.VerticalGrid;
import control.Animator;
import objects.Drawable;
import objects.actors.Cat;
import objects.actors.ghosts.Ash;
import objects.actors.ghosts.Casper;
import objects.actors.ghosts.Dolley;
import objects.actors.ghosts.Ghost;
import objects.edibles.Fruit;
import objects.edibles.Poison;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static background.Cell.cellColumnNum;
import static background.Cell.cellRowNum;


/*
* Board class is the center class of this assignment.Connects different classes together and starts the game
* */

public class Board extends JFrame {
    // Static variables for application width and height
    public static final int BOARD_WIDTH = 750;
    public static final int BOARD_HEIGHT = 750;

    //Actors of the game. Making cat static makes sense since it is created only once.
    public static Cat cat = new Cat();
    private Ghost[] ghosts;

    //Other variables
    private Random rgen = new Random();
    private static JLabel scoreLabel;
    private ArrayList<Drawable> drawables;

    public Board() {
        //Initializes board by setting size of the content pane, color, title etc.
        drawables = new ArrayList<>();
        getContentPane().setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        getContentPane().setBackground(Color.CYAN);
        pack();
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("KoçCat");

    }

    public static void setScoreLabel(int score) {
        //Setting score label with score.
        scoreLabel.setText("Score: " + score);
        scoreLabel.setSize(scoreLabel.getPreferredSize());
        scoreLabel.setLocation(BOARD_WIDTH - scoreLabel.getWidth() - 20, 10);
        scoreLabel.repaint();
    }

    public Cat getCat() {
        return cat;
    }

    public Ghost[] getGhosts() {
        return ghosts;
    }

    public void initGame(int fruit, int poison, int ghost) {
        /*This method starts the game with setting and adding score label
        * adding cat,ghost,fruit,poison to screen
        * drawing grid on screen
        * and starting the animator class which handles listeners and animations.
        * */
        addScoreLabel();
        addCat(cat);
        addGhost(ghost);
        addFruit(fruit);
        addPoison(poison);
        drawGrid();
        repaint();
        Animator animator = new Animator(drawables, this);
        animator.startAnimation();
    }

    private void addScoreLabel(){
        scoreLabel = new JLabel("Score: " + cat.getScore());
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        scoreLabel.setSize(scoreLabel.getPreferredSize());
        scoreLabel.setLocation(BOARD_WIDTH - scoreLabel.getWidth() - 20, 10);
        add(scoreLabel);
    }

    private void addCat(Cat cat) {
        add(cat);
        drawables.add(cat);
    }

    private void addFruit(int fruitNum) {
        //Adds fruit to empty cells
        for (int i = 0; i < fruitNum; i++) {
            int row = rgen.nextInt(cellRowNum);
            int column = rgen.nextInt(cellColumnNum);
            while (Cell.getCellValue(row, column) != null) {
                row = rgen.nextInt(cellRowNum);
                column = rgen.nextInt(cellColumnNum);
            }
            //Creates fruit with row and column
            Fruit fruit = new Fruit(row, column);
            add(fruit);
            drawables.add(fruit);
        }
    }

    private void addPoison(int poisonNum) {
        for (int i = 0; i < poisonNum; i++) {
            int row = rgen.nextInt(cellRowNum);
            int column = rgen.nextInt(cellColumnNum);
            while (Cell.getCellValue(row, column) != null) {
                row = rgen.nextInt(cellRowNum);
                column = rgen.nextInt(cellColumnNum);
            }
            Poison poison = new Poison(row, column);
            add(poison);
            drawables.add(poison);
        }
    }

    private void addGhost(int ghostNum) {
        ghosts = new Ghost[ghostNum];
        for (int i = 0; i < ghostNum; i++) {
            if (i < ghostNum / 3) {
                ghosts[i] = new Ash();
            } else if (i < 2 * ghostNum / 3) {
                ghosts[i] = new Dolley();
            } else {
                ghosts[i] = new Casper();
            }
        }
        for (Ghost ghost : ghosts) {
            int row = rgen.nextInt(cellRowNum);
            int column = rgen.nextInt(cellColumnNum);
            while (Cell.getCellValue(row, column) != null) {
                row = rgen.nextInt(cellRowNum);
                column = rgen.nextInt(cellColumnNum);
            }
            int posX = column * Cell.CELL_WIDTH;
            int posY = row * Cell.CELL_HEIGHT;

            ghost.setLocation(posX, posY);
            add(ghost);
            drawables.add(ghost);
        }
    }
    //Adds vertical and horizontal grids according to row and column numbers.
    private void drawGrid() {
        for (int i = 0; i < Cell.cellColumnNum; i++) {
            VerticalGrid vGrid = new VerticalGrid(i);
            add(vGrid);
        }
        for (int i = 0; i < Cell.cellRowNum; i++) {
            HorizontalGrid hGrid = new HorizontalGrid(i);
            add(hGrid);

        }
    }


}
