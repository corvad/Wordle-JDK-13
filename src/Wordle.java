import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordle {
    private ArrayList<String> wordList;
    private String word;

    public Wordle(){
        //Make the Wordle object
        System.out.println(loadList());
        pickWord();
    }

    private String loadList(){
        try {
            wordList = (ArrayList<String>) Files.readAllLines(Paths.get("src/words.txt"));
        } catch (IOException e){
            e.printStackTrace();
            return "Error Loading Words";
        }
        return "Words Loaded";
    }

    public void start(){
        int attempt = 1;
        while(attempt <= 6){
            String guess = input("\nEnter your guess: ");
            if(guess.equals(word)){
                win(attempt);
                return;
            }

            for(int x = 0;x<guess.length();x++){
                boolean green = false;
                boolean yellow = false;
                for(int y = 0;y<guess.length();y++){
                    if(guess.substring(x,x+1).equals(word.substring(y,y+1)) && y==x){
                        green = true;
                    }
                    if(guess.substring(x,x+1).equals(word.substring(y,y+1))){
                        yellow = true;
                    }
                }
                if(green){
                    colorOutput(guess.substring(x,x+1),"green");
                }
                else if(yellow){
                    colorOutput(guess.substring(x,x+1),"yellow");
                }
                else{
                    colorOutput(guess.substring(x,x+1), "gray");
                }
            }

            attempt++;
        }
        loss();
    }

    private void win(int tries){
        System.out.println("You guessed today's Wordle in " + tries + " tries");
    }

    private void loss(){
        System.out.println("Sadly, you didn't get today's Wordle.\nThe Correct word was " + word + " .");
    }

    private void pickWord(){
        word = wordList.get((int)(Math.random()*wordList.size()+1));
        System.out.println("Word Picked");
    }

    private String input(String output){
        Scanner input  = new Scanner(System.in);
        System.out.print(output);
        String in = input.next().toLowerCase();
        while(!check(in)){
            System.out.println("That Word is not in the list. Try again.");
            System.out.print(output);
            in = input.next();
        }
        return in;
    }

    private boolean check(String input){
        for (String e : wordList) {
            if (input.equals(e)) {
                return true;
            }
        }
        return false;
    }

    private void colorOutput(String output, String color){
        if(color.equals("green")){
            System.out.print("\u001B[30m" + "\033[42m" + output + "\u001B[0m");
        }
        else if(color.equals("yellow")){
            System.out.print("\u001B[30m" + "\033[43m" + output + "\u001B[0m");
        }
        else{
            System.out.print("\u001B[30m" + "\033[47m" + output + "\u001B[0m");
        }
    }
}

