package kr.ac.jbnu.se.tetris;

import java.io.*;

public class ScoreManager {

    private static final String BEST_SCORE_FILE = "tetris/src/data/best_score.txt";

/*    public static void saveBestScore(int bestScore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_SCORE_FILE))) {
            writer.write(String.valueOf(bestScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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

    // 새로운 메소드: 파일에서 처음 6 줄의 점수를 불러온다
    public static int[] loadScores() {
        int[] scores = new int[6];
        File file = new File(BEST_SCORE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                for (int i = 0; i < scores.length; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        scores[i] = Integer.parseInt(line);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
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
            for (int i = 0; i < scores.length; i++) {
                writer.write(String.valueOf(scores[i]));
                writer.newLine();
            }
            // 나머지 줄을 그대로 복사한다
            BufferedReader reader = new BufferedReader(new FileReader(BEST_SCORE_FILE));
            reader.lines().skip(6).forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
