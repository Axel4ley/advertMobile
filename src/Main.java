import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
        public static final String USER_SIGN = "X";
        public static final String USER_SIGN_SECOND = "O";
        public static final String NOT_SIGN = "*";
        public static final int DIMENSION = 3;
        public static String[][] field = new String[DIMENSION][DIMENSION];
        public static String userName1;
        public static String userName2;
        private static String dataset = "dataset.txt";
        private static String resultsLog = "";


        public static void main(String[] args) throws IOException {
            history();
            usersNames();
            mainMenu();
        }

        public static void modeTwoPlayers() throws IOException {
            int count = 0;
            initField(); // initialize play field
            while (true) {
                printField();
                userShot(USER_SIGN, 1);
                count++;
                if (checkWin(USER_SIGN)) {
                    System.out.println("USER 1 WIN!!!");
                    printField();
                    write(dataset, createWinLog(userName1));
                    playAgain();

                    break;
                }
                userShot(USER_SIGN_SECOND, 2);
                count++;
                if (checkWin(USER_SIGN_SECOND)) {
                    System.out.println("USER 2 WIN!!!");
                    printField();
                    write(dataset, createWinLog(userName2));
                    playAgain();
                    break;
                }
                if (count == Math.pow(DIMENSION, 2)) {
                    printField();
                    break;
                }
            }
        }

        public static void usersNames() {
            System.out.println("Игрок 1 введите имя: ");
            Scanner sc = new Scanner(System.in);
            userName1 = sc.nextLine();
            System.out.println("Игрок 2 введите имя: ");
            userName2 = sc.nextLine();
        }

        public static void mainMenu() throws IOException {
            System.out.println("Выберите режим игры: ");
            System.out.println("1. Играть");
            System.out.println("2. История");
            System.out.println("3. Выход.");
            int i = 0;
            Scanner sc = new Scanner(System.in);
            i = sc.nextInt();
            switch (i) {
                case 1: {
                    modeTwoPlayers();
                    break;
                }
                case 2: {
                    System.out.println(resultsLog);
                    mainMenu();
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Было введено неверное значение!");
                }
            }
        }

        public static void history() {

            try (FileReader reader = new FileReader(dataset)) {
                int c;
                while ((c = reader.read()) != -1) {

                    resultsLog += ((char) c);
                }
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }


        public static void initField() {
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    field[i][j] = NOT_SIGN;
                }
            }
        }

        public static void printField() {
            for (int i = 0; i <= DIMENSION; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i < DIMENSION; i++) {
                System.out.print((i + 1) + " ");
                for (int j = 0; j < DIMENSION; j++) {
                    System.out.print(field[i][j] + " ");
                }
                System.out.println();
            }
        }

        public static void userShot(String sign, int i) {
            int x = -1;
            int y = -1;
            do {
                System.out.println("Игрок " + i + ". Введите координаты x y (1 - " + DIMENSION + "): ");
                Scanner sc = new Scanner(System.in);
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
            }
            while (isCellBusy(x, y));
            field[x][y] = sign;
        }

        public static boolean isCellBusy(int x, int y) {
            if (x < 0 || y < 0 || x > DIMENSION - 1 || y > DIMENSION - 1) {
                return false;
            }
            return field[x][y] != NOT_SIGN;
        }

        public static boolean checkWin(String sign) {
            // проверка по строкам
            {
                for (int i = 0; i < DIMENSION; i++) {
                    if (field[i][0] == sign && field[i][1] == sign && field[i][2] == sign) {
                        return true;
                    }
                }
            }
            // проверка по столбцам
            {
                for (int j = 0; j < DIMENSION; j++) {
                    if (field[0][j] == sign && field[1][j] == sign && field[2][j] == sign) {
                        return true;
                    }
                }
            }
            // проверка диагоналей
            {
                if (field[0][0] == sign && field[1][1] == sign && field[2][2] == sign) {
                    return true;
                }
                if (field[0][2] == sign && field[1][1] == sign && field[2][0] == sign) {
                    return true;
                }
            }
            return false;
        }

        public static void playAgain() throws IOException {
            int answer = 0;
            System.out.println("Хотите сыграть еще раз?");
            System.out.println("1. Да");
            System.out.println("2. Нет, спасибо");

            Scanner sc = new Scanner(System.in);
            answer = sc.nextInt();
            switch (answer) {
                case 1: {
                    modeTwoPlayers();
                    break;
                }
                case 2: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("Было введено неверное значение!");
                }
            }
        }

        public static void write(String fileName, String text) {

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.append(text);
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static String createWinLog(String winner) {
            String str = "\n" + userName1 + " " + userName2 + "\n" + "Winner is " + winner + "\n";
            for (int i = 0; i <= DIMENSION; i++) {
                str += (i + " ");
            }
            str += "\n";
            for (int i = 0; i < DIMENSION; i++) {
                str += ((i + 1) + " ");
                for (int j = 0; j < DIMENSION; j++) {
                    str += (field[i][j] + " ");
                }
                str += ("\n");
            }
            return str;
        }
}

