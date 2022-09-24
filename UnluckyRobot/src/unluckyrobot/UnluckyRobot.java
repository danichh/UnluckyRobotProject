/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Danich Hang
 */
public class UnluckyRobot {
 /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in); 
        
        int x = 0; // is the x coordinate of the robot position
        int y = 0; // is the y coordinate of the robot position
        int itrCount = 0; 
        int reward;
        int totalScore = 300 ;
        
        do{    
            displayInfo(x, y, itrCount, totalScore);
            itrCount++;  // count how many time we do the iteration 
            char direction = inputDirection();
            x =movementX(x,direction); 
            y =movementY(y,direction);
            totalScore += ExeedTheBoundaryDamage(x, y, direction);
            // if the x and y > 4  or < 0 is would -2000 to the totalScore
            reward = reward(); // get random number 1 to 6 different reward accordingly
            if (direction == 'u'){ // when the player move up it would take 10 point off 
                int punish = punishOrMercy(direction, reward); 
                // if we should apply the punish or not tail = no , head = yes 
                totalScore = totalScore + punish - 10; // 10 is the cost of moving up
            }else  // when the player everywhere beside up it would take 50 point off
                 totalScore = totalScore + reward - 50; //  50 is the cost of moving
            System.out.println("");
        } while (! (isGameOver(x, y, totalScore, itrCount))); 
        /* redo the loop when the score plus that 2000 or lower that -1000 or 
           x = 4, y = 4 or x = 4, y = 0
        */
            evaluation(totalScore);      
    }
    
    /**
     *  to print info about where the robot at , and the total score 
     * @param x is the x coordinate of the robot position (start at 0)
     * @param y is the x coordinate of the robot position (start at 0)
     * @param itrCount is the number of iteration 
     * @param totalScore is the total score of point obtained (start at 300)
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){  
        System.out.printf("For point (X=%d, Y=%d) at iteration: %d total score "
                + "is: %d\n", x, y, itrCount, totalScore);
    }
    
    /**
     * if the robot get out of the boundary or not 
     * @param x is the x coordinate of the robot position (start at 0)
     * @param y is the y coordinate of the robot position (start at 0)
     * @param direction is 'r' right , 'l' left, 'u' up, 'd' down 
     * @return true of x > 4 or y > 4 , else false 
     */
    public static boolean doesExeed(int x, int y,char direction){
        return (x > 4 || y > 4 || x < 0 || y < 0 );  
    }
    
    /**
     * 
     * @param x is the x coordinate of the robot position
     * @param y is the y coordinate of the robot position
     * @param direction is the input 'r' right , 'l' left, 'u' up, 'd' down 
     * to move the robot 
     * @return -2000 if  the Boundary is exceed 
     */
    public static int ExeedTheBoundaryDamage (int x, int y, char direction){
        if (doesExeed(x, y, direction)){
            System.out.println("Exceed boundary, -2000 damage applied");
            return -2000;   // the damage when exceed boundary 
        } else 
            return 0; 
    }
    
    /**
    * to tell if the input is the Acronym that we want 
    * @param acronym is the given acronym
    * @return true ('r' right , 'l' left, 'u' up, 'd' down) else false 
    */
    public static boolean isdirectionAcronym (char acronym){
        String validAcronym = "rdul"; 
        return validAcronym.contains("" + acronym);          
   }
   
    /**
     * asked the user to input direction 
     * re ask if the input is not right 
     * @return the input ( 'r' right , 'l' left, 'u' up, 'd' down)
     */
    public static char inputDirection (){
        Scanner console = new Scanner (System.in); 
        char inputDirection; 
        
        // keep asked if the input is not r, l, left, 'u' up, 'd' down)
        do {
              System.out.print("Please input a valid direction(r = right, l = left, u = up, d = down): ");
              inputDirection = console.next().charAt(0);
        } while (! (isdirectionAcronym(inputDirection)));
     return inputDirection;
    }
    
    /** 
     * 
     * @param inputDirection the input ( 'r' right , 'l' left, 'u' up, 'd' down)
     *  @param x is the position of the robot
     * @return +1 when turn right -1 when turn left 
     */
    public static int movementX(int x, char inputDirection){
        if (inputDirection == 'r')
            x++;    // x + 1 when turn right 
        else if (inputDirection == 'l')
            x--;    // x - 1 when turn left
      return x ; 
    }
    
    /** 
     * 
     * @param inputDirection
     * @param y is the position of the robot
     * @return +1 when go up -1 when go down 
     */
    public static int movementY(int y , char inputDirection){
        if (inputDirection == 'u')  // y + 1 when go up
            y++;
        else if (inputDirection== 'd')  // y - 1 when turn right
            y--; 
      return y; 
    }
    
    /**
     * the reward and the punishment is base on a random number ( 1 to 6) 
     * each represent a reward or a punishment
     * @return a the reward or the punishment for each iteration 
     */
    public static int reward(){
        Random rand = new Random(); 
        int dice = rand.nextInt(6) + 1; 
        int reward = 0;
        
        switch(dice){
            case 1:
                System.out.println("Dice: 1, reward: -100");
                return -100; // 100 points off the total score 
            case 2:
                System.out.println("Dice: 2, reward: -200");
                return -200; // 200 points off the total score 
            case 3:
                System.out.println("Dice: 3, reward: -300");
                return -300; // 300 points off the total score 
            case 4:
                System.out.println("Dice: 4, reward: 300");
                return 300; // 300 point add to thr totall score 
            case 5:
                System.out.println("Dice: 5, reward: 400");
                return 400; // 400 point add to thr totall score
            default:
                System.out.println("Dice: 6, reward: 600");  
                return 600; // 600 point add to thr totall score
        }
    }
    
    /**
     * put the string to title case 
     * @param str is the input name 
     * @return the name in title case "Xxxx Xxxx" 
     */
    public static String toTitleCase(String str){
        // extract the frist word and put it to title case "Xxx"
        char fristLetterOfFristName = Character.toTitleCase(str.charAt(0));
        String theRestOfFirstName = str.substring(1, str.indexOf(" ")).toLowerCase(); 
        
        //extract the second word and put it to title case "Xxx"
        char fristLetterOfLastName = Character.toTitleCase(str.charAt(
                str.indexOf(" ") + 1));
        String theRestOfLastName = str.substring(str.indexOf(" ") + 2).toLowerCase();
        
        return fristLetterOfFristName + theRestOfFirstName + " " 
                + fristLetterOfLastName + theRestOfLastName;
    }
    
    /**
     * Print the victory or failed text 
     * @param totalScore is the score at end of the game 
     * name is the player name (input)
     */
    public static void evaluation (int totalScore){
        Scanner console = new Scanner (System.in); 
        //ask for the player name 
        System.out.print("Please your name (only two words): ");
        String name = toTitleCase(console.nextLine()); 
        
        // more that 2000 points == winner 
        if (totalScore >= 2000)
            System.out.printf("Victory, %s, your score is %d\n", name, totalScore);
        else 
            System.out.printf("Mission failed, %s, your score is %d\n", name, totalScore);
            
    }
    
    /**
     * 
     * @param direction
     * @param reward
     * @return 
     */
    public static int punishOrMercy (char direction, int reward){
        Random rand = new Random(); 
        
        int coin = rand.nextInt(2);  
        if(reward < 0 && direction == 'u')  
            if ( coin == 0)  // 0 = tail
                System.out.println("Coin: Tail | Mercy, the negative reward is removed");
            else            // 1 = head
                System.out.println("Coin: head | the negative rewarded is applied");
        /*
        if coin = tail, il would not applied the negative reward, but 
        if the coin = head , il would applied the negative rewared
        */
        return (coin == 0)? 0 : reward;
    }
    
    /**
     * if the game is over or not
     * @param x is the x coordinate of the robot position (start at 0)
     * @param y is the y coordinate of the robot position (start at 0)
     * @param totalScore is the number of iteration
     * @param itrCount is the total score of point obtained (start at 300)
     * @return ture if the iteration is plus that 20 , total score is more that 
     * 2000 or smaller that -1000 , x = 4 , y = 4 or x = 4, y = 0
     */
    public static boolean isGameOver (int x, int y, int totalScore, int itrCount){
        if (itrCount >= 20)
            return true; 
        else if (totalScore <= -1000 || totalScore >= 2000)
            return true; 
        else if ( x == 4 && y == 4 || x == 4 && y == 0 )
            return true;  
            return false;        
    }
    
    
}
