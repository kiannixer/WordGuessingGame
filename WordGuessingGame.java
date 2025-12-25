import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class WordGuessingGame {


    static class Player {
        private String name;
        private int score;

        public Player(String name) {
            this.name = name;
            this.score = 0;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void incrementScore() {
            score++;
        }

        public void resetScore() {
            score = 0;
        }
    }


    static class Game {
        private Player player;
        private List<String> words;
        private String currentWord;
        private char[] guessedLetters;
        private int attemptsLeft;
        private int maxAttempts;
        private boolean gameWon;

        public Game(Player player) {
            this.player = player;
            this.words = new ArrayList<>();
            initializeWords();
            this.maxAttempts = 6;
        }


        private void initializeWords() {
            words.add("جاوا");
            words.add("برنامه");
            words.add("کامپیوتر");
            words.add("الگوریتم");
            words.add("شی‌گرایی");
            words.add("اینترفیس");
            words.add("متد");
            words.add("کانستراکتور");
            words.add("آبجکت");
            words.add("کلاس");
        }


        public void newGame() {
            Random random = new Random();
            currentWord = words.get(random.nextInt(words.size()));
            guessedLetters = new char[currentWord.length()];


            for (int i = 0; i < guessedLetters.length; i++) {
                guessedLetters[i] = '_';
            }

            attemptsLeft = maxAttempts;
            gameWon = false;

            System.out.println("\n=== بازی جدید شروع شد! ===");
            System.out.println("کلمه شامل " + currentWord.length() + " حرف است.");
            displayWord();
        }


        public void displayWord() {
            System.out.print("کلمه: ");
            for (char c : guessedLetters) {
                System.out.print(c + " ");
            }
            System.out.println("\nتعداد فرصت‌های باقی‌مانده: " + attemptsLeft);
        }


        public boolean guessLetter(char letter) {
            boolean correctGuess = false;


            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == letter && guessedLetters[i] != letter) {
                    guessedLetters[i] = letter;
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                attemptsLeft--;
                System.out.println("حرف '" + letter + "' در کلمه وجود ندارد.");
            } else {
                System.out.println("آفرین! حرف '" + letter + "' در کلمه وجود دارد.");
            }


            if (isWordComplete()) {
                gameWon = true;
                player.incrementScore();
            }

            return correctGuess;
        }


        public boolean guessWord(String word) {
            if (word.equalsIgnoreCase(currentWord)) {
                gameWon = true;
                guessedLetters = currentWord.toCharArray();
                player.incrementScore();
                return true;
            } else {
                attemptsLeft--;
                System.out.println("کلمه حدس زده شده اشتباه است.");
                return false;
            }
        }


        private boolean isWordComplete() {
            for (char c : guessedLetters) {
                if (c == '_') {
                    return false;
                }
            }
            return true;
        }


        public boolean isGameOver() {
            return attemptsLeft <= 0 || gameWon;
        }


        public void displayResult() {
            System.out.println("\n=== نتیجه بازی ===");

            if (gameWon) {
                System.out.println("تبریک! شما برنده شدید!");
                System.out.println("کلمه صحیح: " + currentWord);
                System.out.println(player.getName() + " عزیز، امتیاز شما: " + player.getScore());
            } else {
                System.out.println("متاسفانه شما باختید!");
                System.out.println("کلمه صحیح: " + currentWord);
            }

            displayWord();
        }


        public void displayGameStatus() {
            System.out.println("\n--- وضعیت بازی ---");
            System.out.println("بازیکن: " + player.getName());
            System.out.println("امتیاز: " + player.getScore());
            displayWord();
        }


        public String getCurrentWord() {
            return currentWord;
        }


        public boolean isGameWon() {
            return gameWon;
        }


        public int getAttemptsLeft() {
            return attemptsLeft;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== بازی حدس کلمه =====");
        System.out.print("لطفا نام خود را وارد کنید: ");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);
        Game game = new Game(player);

        boolean playAgain = true;

        while (playAgain) {
            game.newGame();

            while (!game.isGameOver()) {
                game.displayGameStatus();

                System.out.print("\nحدس شما (یک حرف یا کل کلمه): ");
                String guess = scanner.nextLine();

                if (guess.length() == 1) {

                    char letter = guess.charAt(0);
                    game.guessLetter(letter);
                } else {

                    game.guessWord(guess);
                }
            }

            game.displayResult();

            System.out.print("\nآیا می‌خواهید دوباره بازی کنید؟ (بله/خیر): ");
            String response = scanner.nextLine();

            if (!response.equalsIgnoreCase("بله")) {
                playAgain = false;
                System.out.println("\nبا تشکر از بازی کردن! امتیاز نهایی شما: " + player.getScore());
            }
        }

        scanner.close();
    }
}