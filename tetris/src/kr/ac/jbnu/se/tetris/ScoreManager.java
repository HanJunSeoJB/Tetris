package kr.ac.jbnu.se.tetris;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreManager {

    private static final Logger logger = Logger.getLogger(ScoreManager.class.getName());
    private static final String BEST_SCORE_FILE = "tetris/src/data/best_score.txt";

    private ScoreManager() {
        throw new IllegalStateException("Utility class");
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
                logger.log(Level.WARNING, "Failed to load best score from file", e);
            }
        }
        return 0;  // 파일이 없거나 읽기에 실패하면 0 반환
    }

    // 파일에서 처음 6 줄의 점수를 불러온다
    public static int[] loadScores() {
        int[] scores = new int[6];
        File file = new File(BEST_SCORE_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < scores.length; i++) {
                String line = reader.readLine();
                if (line != null) {
                    scores[i] = Integer.parseInt(line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to load scores from file", e);
        }

        return scores;
    }

    // 새로운 메소드: 새로운 점수를 받아서 기존 점수를 업데이트하고 파일에 다시 저장한다
    public static void updateAndSaveScores(int newScore) {
        int[] scores = loadScores();
        for (int i = 0; i < scores.length; i++) {
            if (newScore > scores[i]) {
                System.arraycopy(scores, i, scores, i + 1, scores.length - i - 1);
                scores[i] = newScore;
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_SCORE_FILE))) {
            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to update and save scores", e);
        }
    }
}
