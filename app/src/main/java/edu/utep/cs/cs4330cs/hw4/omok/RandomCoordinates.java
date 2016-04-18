package edu.utep.cs.cs4330cs.hw4.omok;
import java.util.Random;
public class RandomCoordinates {
    private Coordinates coor;

        public Coordinates placeToken(Board board){
            coor = new Coordinates();
            Random rand = new Random();
            coor.setX(rand.nextInt(10));
            coor.setY(rand.nextInt(10));

            while(!board.setStone(coor.getX(), coor.getY(), board.getPlayerName())){
                coor.setX(rand.nextInt(10));
                coor.setY(rand.nextInt(10));
            }
            return coor;
        }

}

