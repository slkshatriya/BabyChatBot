package mycode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;

public class BabyChatBot {
    private static HashMap<String, String> map = new HashMap<>();
    private static final String DATA_FILE = "chatbot_data.txt";
    private static final String API_ENDPOINT = "https://api.wolframalpha.com/v2/result";
    private static final String API_KEY = "4Y4JEU-PHGXUA9APE";

    public static void main(String[] args) {

        loadDataFromFile();
        Scanner sc = new Scanner(System.in);

        int choice = 0;
        while (choice != 4) {
            System.out.println("Hey, good people what you want me to do?");
            System.out.println();
            System.out.println("1. Train Me");
            System.out.println("2. Chat with me");
            System.out.println("3. Ask basic questions of any subject(i.e. maths, science, cs,...)");
            System.out.println("4. exit");
            System.out.println();
            System.out.println("enter your choice");

            choice = sc.nextInt();

            switch (choice) {
                case 1:

                    String loop="anything";

                    while (!loop.equals("exit")){

                        loop = trainBot();
                        saveDataToFile();
                        System.out.println();

                    }
                    break;
                case 2:

                    String loop1 ="anything";

                    while (!loop1.equals("exit")){

                        loop1 = chatBot();
                        System.out.println();

                    }

                    break;
                case 3:

                    String loop3 = "anything";

                    while (!loop3.equals("exit")){

                        try {
                            String question = askMathematicsQuestion();
                            String answer = solveMathematicsQuestion(question);
                            System.out.println("The answer is: " + answer);
                        } catch (Exception e) {
                            System.out.println("An error occurred : " + e.getMessage());
                        }

                        System.out.println();
                        System.out.println("If you want to ask more question type anything else type exit");
                        loop3 = sc.next();
                    }

                    break;
                case 4:
                    break;
                default:
                    System.out.println("invalid choice");
            }
        }


    }

    public static String trainBot() {
        Scanner sc = new Scanner(System.in);

        System.out.println("enter your input");
        String input = sc.nextLine();
        System.out.println("enter your response");
        String response = sc.nextLine();

        map.put(input, response);

        System.out.println("i have learned a new thing :)");
        System.out.println();
        System.out.println("If you want me to train more type anything else type exit");
        String res = sc.next();
        return res;
    }

    public static String chatBot() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hi, there let's chat :)");
        String res = sc.nextLine();

        if (map.containsKey(res)) {
            String response = map.get(res);
            System.out.println(response);
        } else {
            System.out.println("sorry you didn't trained me for this input :(");
        }
        System.out.println();
        System.out.println("If you want to continue your chat type anything else type exit");
        String res1 = sc.next();
        return res1;
    }

    public static void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String input = parts[0];
                String response = parts[1];
                map.put(input, response);
            }
        } catch (IOException e) {

            createNewFile();
        }
    }

    public static void createNewFile() {
        File file = new File(DATA_FILE);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (String input : map.keySet()) {
                String response = map.get(input);
                writer.write(input + "," + response);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String askMathematicsQuestion() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your question:");
        return sc.nextLine();
    }

    public static String solveMathematicsQuestion(String question) throws Exception {
        String encodedQuestion = URLEncoder.encode(question, "UTF-8");
        String urlString = API_ENDPOINT + "?appid=" + API_KEY + "&i=" + encodedQuestion;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

}