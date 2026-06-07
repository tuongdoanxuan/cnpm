package Model;

import java.io.*;
import java.util.*;
//Tường: hiển thị top xếp hạng
public class Leaderboard {

    private static final String FILE_NAME = "leaderboard.txt";

    public static void saveScore(int score) {

        try {
            List<Integer> scores = loadScores();
            scores.add(score);
            scores.sort(Collections.reverseOrder());
            while(scores.size() > 10) {
                scores.remove(scores.size() - 1);
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for(Integer s : scores) {
                bw.write(String.valueOf(s));
                bw.newLine();
            }

            bw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> loadScores() {
        List<Integer> scores =new ArrayList<>();
        try {
            File file = new File(FILE_NAME);

            if(!file.exists())
                return scores;

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while((line = br.readLine()) != null) {
                scores.add(Integer.parseInt(line));
            }

            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return scores;
    }
}