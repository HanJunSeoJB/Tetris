package kr.ac.jbnu.se.tetris;

import java.io.*;

public class ScoreManager {

    private static final String BEST_SCORE_FILE = "tetris/src/data/best_score.txt";

    public static void saveBestScore(int bestScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_SCORE_FILE))) {
            writer.write(String.valueOf(bestScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadBestScore() {
        File file = new File(BEST_SCORE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    return Integer.parseInt(line);
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;  // 파일이 없거나 읽기에 실패하면 0 반환
    }
}
