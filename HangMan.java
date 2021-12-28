
import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//                      ************* class HangMan  ****************
class hangMan{
    void draw(int mistake, int k) {
        mistake /= (k / 7);
        System.out.println(ConsoleColors.YELLOW);
        switch (mistake) {
            case 0:
                System.out.println("-----\n|    \n|    \n|   \n|");
                break;
            case 1:
                System.out.println("-----\n|    |\n|    \n|   \n|");
                break;
            case 2:
                System.out.println("-----\n|    |\n|    O\n|   \n|");
                break;
            case 3:
                System.out.println("-----\n|    |\n|    O\n|   /\n|");
                break;
            case 4:
                System.out.println("-----\n|    |\n|    O\n|   /|\n|");
                break;
            case 5:
                System.out.println("-----\n|    |\n|    O\n|   /|\\\n|");
                break;
            case 6:
                System.out.println("-----\n|    |\n|    O\n|   /|\\\n|   /");
                break;
            case 7:
                System.out.println("-----\n|    |\n|    O\n|   /|\\\n|   / \\");
                break;
        }
        System.out.println(ConsoleColors.RESET);
    }
}
//-----------------------------------class user------------------------------------
class User{
    private String userName;
    private String password;
    private int score;
    User(){
        score = 0;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    void increaseScore(){
        score+=5;
    }
    void shopLetter(){
        score-=10;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }
}

//                ***************  class Game *****************

class Game {
    private int mistakeCounter;
    private static String hide_word;
    private static String mistake_letter;
    private String correctWord;
    private int allowedMistake;
    private String table;
    String[] words = {"tehran", "pizza", "banana", "new york", "advanced programming", "michael jordan",
            "lionel messi", "apple", "macaroni", "university", "intel", "kitten", "python", "java",
            "data structures", "algorithm", "assembly", "basketball", "hockey", "leader", "javascript",
            "toronto", "united states of america", "psychology", "chemistry", "breaking bad", "physics",
            "abstract classes", "linux kernel", "january", "march", "time travel", "twitter", "instagram",
            "dog breeds", "strawberry", "snow", "game of thrones", "batman", "ronaldo", "soccer",
            "hamburger", "italy", "greece", "albert einstein", "hangman", "clubhouse", "call of duty",
            "science", "theory of languages and automata"};

    Game(){
        mistakeCounter = 0;
        correctWord = words[(int)(Math.random()*words.length)];
        mistake_letter = "";
        allowedMistake = (correctWord.length() > 9)? 14 : 7;
        if (allowedMistake == 7){
            table = ConsoleColors.GREEN+"|V|V|V|V|V|V|V|"+ConsoleColors.RESET;
        }
        else{
            table = ConsoleColors.GREEN+"|V|V|V|V|V|V|V|V|V|V|V|V|V|V|"+ConsoleColors.RESET;
        }
    }
    boolean winChecker(User user){
        if (correctWord.equals(hide_word)){
            user.increaseScore();
            return true;
        }
        return false;
    }
    void Hide(){
        hide_word = correctWord.replaceAll("[a-z]", "-");
    }
    void displayWord(){
        System.out.println("This is the word : ");
        System.out.println(hide_word);
    }
    boolean letter_checker(String ch){
        if (mistake_letter.contains(ch)){
            System.out.println(ConsoleColors.RED_BOLD+"This letter has already been selected. please select a new Letter"+ConsoleColors.RESET);
            wait.sleep(2000);
            return false;
        }
        mistake_letter += ch;
        if (correctWord.contains(ch)){
            hide_word = replace(correctWord, ch);
            System.out.println(ConsoleColors.GREEN+"correct letter! :) "+ConsoleColors.RESET);
            wait.sleep(1000);
        }
        else{
            mistakeCounter++;
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"wrong letter :( "+ConsoleColors.RESET);
            wait.sleep(2000);
            int x = table.indexOf("V");
            table = table.substring(0,x)+ConsoleColors.RED+"X"+ConsoleColors.RESET+ConsoleColors.GREEN+table.substring(x+1)+ConsoleColors.RESET;
        }
        return true;
    }
    String replace(String str, String ch){
        String help = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch.charAt(0)){
                help+=ch;
            }
            else if(str.charAt(i) == " ".charAt(0)){
                help+=" ";
            }
            else{
                help+=hide_word.charAt(i);
            }
        }
        return help;
    }
    void display_Table(){
        System.out.println(table);
    }
    void displayWrongLetters(){
        System.out.print(ConsoleColors.CYAN_BOLD+"You Can't Select These Letters : "+ConsoleColors.RESET);
        for (int i = 0; i < mistake_letter.length(); i++) {
            System.out.print(ConsoleColors.RED+mistake_letter.charAt(i)+" "+ConsoleColors.RESET);
        }
        System.out.println();
    }
    void openALetter(){
        while (true){
            int x = (int) (Math.random()*26) + 97;
            char ch = (char) x;
            if (!mistake_letter.contains(ch+"") && correctWord.contains(ch+"")){
                hide_word = replace(correctWord, ch+"");
                mistake_letter+=ch;
                break;
            }
        }
    }

    public int getMistakeCounter() {
        return mistakeCounter;
    }

    public int getAllowedMistake() {
        return allowedMistake;
    }

    public String getCorrectWord() {
        return correctWord;
    }
}
//----------------------------------------class wait ----------------------------------
class wait{
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}


//                             ********** class Tester ********

class Tester {
    //-----------------------------------main------------------------------------------
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int j = 0;
        //User[] users = readFile();
        User[] users = new User[0];
        boolean flag = false;
        while (true) {
            welcome();
            boolean winning = false;
            while (!flag) {
                int n = Menu();
                switch (n) {
                    case 1:
                        users = signUp(users);
                        break;
                    case 2:
                        if( Menu2(users) == 1){
                            j = login(users);
                            flag = j>=0? true: false;
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println(ConsoleColors.RED_BOLD+"Invalid number!\nplease choose correct number"+ConsoleColors.RESET);
                        wait.sleep(2500);
                }
                //fileWriter(users);
            }
            boolean shopping = true;
            Game g1 = new Game();
            g1.Hide();
            game(g1);
            while (g1.getMistakeCounter() < g1.getAllowedMistake()) {
                if (shopping){
                    System.out.println("Can I Help You? (I Can Open One Letter) Write \"shop\"");
                }
                System.out.print("please enter a Letter : ");
                String Letter = input.next();
                if (Letter.equals("shop")) {
                    shopping = shopLetter(shopping, users[j], g1);
                }
                else if (g1.letter_checker(Letter) ) {
                }
                if (g1.winChecker(users[j]) == true) {
                    winning = true;
                    break;
                }
                game(g1);
            }
            //fileWriter(users);
            leaderBoard(users);
            winningSituation(winning, g1, users[j]);
            flag = input.nextInt() == 2 ? false : true;
        }
    }
    //----------------------------------Methods-------------------------------------------------------
    public static void winningSituation(boolean winning, Game game, User user){
        clrscr();
        if (winning == false){
            System.out.println(ConsoleColors.RED_BOLD+"you loose! :("+ConsoleColors.RESET);
        }
        else {
            System.out.println(ConsoleColors.GREEN+"Woooooow! you win!"+ConsoleColors.RESET);
        }
        wait.sleep(2000);
        System.out.println("correct word is : "+ConsoleColors.YELLOW_BOLD_BRIGHT+game.getCorrectWord()+ConsoleColors.RESET);
        System.out.print("Your Score : "+ConsoleColors.GREEN_BOLD_BRIGHT+user.getScore()+ConsoleColors.RESET+"\nDo you want to play again?\n1) YES \n2) NO\n"+ConsoleColors.YELLOW_BOLD_BRIGHT+"Which one?! "+ConsoleColors.RESET);

    }
    public static void game(Game game){
        hangMan hm = new hangMan();
        clrscr();
        game.displayWord();
        hm.draw(game.getMistakeCounter(), game.getAllowedMistake());
        game.displayWrongLetters();
        game.display_Table();
    }
    public static boolean shopLetter(boolean shopping, User user, Game game){
        if (shopping == true) {
            clrscr();
            if (user.getScore() >= 10) {
                game.openALetter();
                user.shopLetter();
                return false;
            } else {
                System.out.println(ConsoleColors.RED_BOLD+"You Don't have enough score\nwin more"+ConsoleColors.RESET);
                wait.sleep(2000);
                clrscr();
                return true;
            }
        }
        else {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"you only could use it once a game"+ConsoleColors.RESET);
            wait.sleep(2000);
            clrscr();
            return false;
        }
    }

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }


    /*static User[] readFile(){
        User[] users = new User[0];
        try {
            File file = new File("C:\\Users\\SabaNet\\Desktop\\HangMan\\file\\users.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str;
            while (true){
                User newUser = new User();
                str = bufferedReader.readLine();
                if (str == null){
                    break;
                }
                String name = str.substring(0, str.indexOf(" "));
                String password = str.substring(str.indexOf(" ")+1, str.lastIndexOf(" "));
                int score = Integer.valueOf(str.substring(str.lastIndexOf(" ")+1));
                newUser.setUserName(name);
                newUser.setPassword(password);
                newUser.setScore(score);
                User[] helpuser = new User[users.length + 1];
                System.arraycopy(users, 0, helpuser, 0, users.length);
                helpuser[users.length] = newUser;
                users = helpuser;
            }
            fileReader.close();
            bufferedReader.close();
        }
        catch (Exception ex){
            System.out.println("Error");

        }
        return users;
    }*/

   /* static void fileWriter(User[] users){
        clrscr();
        try {
            File file = new File("C:\\Users\\SabaNet\\Desktop\\HangMan\\file\\users.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < users.length; i++) {
                bufferedWriter.write(users[i].getUserName() + " " + users[i].getPassword()+" "+users[i].getScore());
                bufferedWriter.newLine();
            }
            //fileWriter.close();
            bufferedWriter.close();
        }
        catch (Exception ex){
            System.out.println(ex);
        }

    }*/
    static void leaderBoard(User[] users){
        try {
            File file = new File("C:\\Users\\SabaNet\\Desktop\\HangMan\\LeaderBoard.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            sort(users);
            bufferedWriter.write("user" + "-" +"score");
            bufferedWriter.newLine();
            for (int i = 0; i < users.length; i++) {
                bufferedWriter.write(users[i].getUserName() + "-" +users[i].getScore());
                bufferedWriter.newLine();
            }
            //fileWriter.close();
            bufferedWriter.close();
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    static int Menu() {
        Scanner input = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT+"Hello! Welcome to this program :)"+ConsoleColors.RESET+ConsoleColors.PURPLE_BOLD_BRIGHT+"\n1) sign up\n2) login\n0) close the program"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD + "Which one?! " + ConsoleColors.RESET);
        int n = input.nextInt();
        wait.sleep(200);
        clrscr();
        return n;
    }
    static int Menu2(User[] users) {
        Scanner input = new Scanner(System.in);
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"1.Start Game\n2.Show Leader Board"+ConsoleColors.RESET);
        System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT+"Which one?! "+ConsoleColors.RESET);
        switch (input.nextInt()) {
            case 1:
                return 1;
            case 2:
                showLeaderBoard(users);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT+"\npress any key to back "+ConsoleColors.RESET);
                input.next();
                return 0;
        }
        return 0;
    }
    static void showLeaderBoard(User[] users){
        clrscr();
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"Leader Board : \n"+ConsoleColors.RESET);
        sort(users);
        System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT+"            Name................score\n"+ConsoleColors.RESET);
        for (int i = 0; i < users.length; i++) {
            if (i == 0){
                System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);
            }
            System.out.printf("            "+users[i].getUserName());
            for (int j = 1; j < 22 - users[i].getUserName().length(); j++) {
                System.out.print(".");
            }
            System.out.println(users[i].getScore());
            System.out.print(ConsoleColors.RESET);

        }
    }
    static void sort(User arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            User key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].getScore() < key.getScore()) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    static int isUserAvailable(String userName, User[] users) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    static User[] signUp(User[] users) {
        User newUser = new User();
        String userName = setName(users);
        newUser.setUserName(userName);
        String password = setPassword();
        newUser.setPassword(password);
        User[] helpuser = new User[users.length + 1];
        System.arraycopy(users, 0, helpuser, 0, users.length);
        helpuser[users.length] = newUser;
        users = helpuser;
        System.out.println(ConsoleColors.GREEN+ "you seccessfully signed up! :) " + ConsoleColors.RESET);
        wait.sleep(3000);
        return users;
    }
    static String setName(User[] users){
        Scanner input = new Scanner(System.in);
        System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT+"Please Enter a User Name\n"+ConsoleColors.RESET+ConsoleColors.RED_BOLD_BRIGHT+"user name : "+ConsoleColors.RESET);
        String userName = input.nextLine();
        while (isUserAvailable(userName, users) >= 0) {
            System.out.println(ConsoleColors.RED_BOLD+"it is available. try again"+ConsoleColors.RESET);
            wait.sleep(1500);
            clrscr();
            System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT+"Please Enter a User Name\n"+ConsoleColors.RESET+ConsoleColors.RED_BOLD_BRIGHT+"user name : "+ConsoleColors.RESET);
            userName = input.nextLine();
        }
        clrscr();
        return userName;
    }
    static String setPassword(){
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"please enter your password"+ConsoleColors.RESET);
        String password = setPass();
        while (password.length() < 6 || Pattern.compile("[a-zA-Z0-9]*[@#$%^&*)(*/!~><][a-zA-Z0-9]*").matcher(password).matches() == false){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"your password is weak! try again (use special character!)"+ConsoleColors.RESET);
            wait.sleep(1500);
            clrscr();
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT+"please enter your password"+ConsoleColors.RESET);
            password = setPass();
        }
        return password;
    }
    static int login(User[] users) {
        Scanner input = new Scanner(System.in);
        clrscr();
        System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT+"please enter your user name\nuser name : "+ConsoleColors.RESET);
        String user = input.nextLine();
        if (isUserAvailable(user, users) >= 0) {
            wait.sleep(1000);
            clrscr();
            int j = isUserAvailable(user, users);
            System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT+"please enter your password\n"+ ConsoleColors.RESET);
            String password = setPass();
            while (!users[j].getPassword().equals(password)) {
                System.out.print("password is wrong try again");
                wait.sleep(1500);
                clrscr();
                System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT+"please enter your password\n"+ ConsoleColors.RESET);
                password = setPass();
            }
            System.out.println(ConsoleColors.GREEN_BOLD+"you successfully login"+ConsoleColors.RESET);
            wait.sleep(3000);
            return j;
        } else {
            System.out.println(ConsoleColors.RED_BOLD+"first sign up"+ConsoleColors.RESET);
            wait.sleep(2000);
            return -1;
        }
    }
    static String setPass(){
        Scanner input = new Scanner(System.in);
        char[] passwordArray;
        try {
            //Console console = System.console();
            //passwordArray = console.readPassword(ConsoleColors.CYAN_BOLD_BRIGHT+"password: "+ConsoleColors.RESET);
            //String str = String.valueOf(passwordArray);
            String str = input.nextLine();
            return str;
        }
        catch (Exception ex){
            System.out.println(ex);
            return "Error";
        }
    }
    static void welcome() {
        clrscr();
        for (int i = 0; i < 7; i++) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "  _    _          _   _  _____   __  __          _   _ \n " +
                    "| |  | |   /\\   | \\ | |/ ____| |  \\/  |   /\\   | \\ | |\n | |__| | " +
                    " /  \\  |  \\| | |  __  | \\  / |  /  \\  |  \\| |\n |  __" +
                    "  | / /\\ \\ | . ` | | |_ | | |\\/| | / /\\ \\ | . ` |\n | |  | |/ ____ \\| |\\  " +
                    "| |__| | | |  | |/ ____ \\| |\\  |\n |_|  |_/_/    \\_\\_| \\_|\\_____| |_|  |_/_/   " +
                    " \\_\\_| \\_|\n\n" + ConsoleColors.RESET);
            wait.sleep(300);
            clrscr();
            wait.sleep(100);
        }

    }
}
class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
}

