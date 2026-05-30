package model;

import java.util.ArrayList;
import java.util.List;

public class GameStatus implements GameStatus.Subject {

    public interface Observer {
        void onScoreChanged(double newScore);
        void onGameOver();
    }

    public interface Subject {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyScoreChanged(double newScore);
        void notifyGameOver();
    }

    private double score;
    private double highScore;
    private boolean gameOver;
    private GameState currentState = GameState.START_MENU;
    private final List<Observer> observers = new ArrayList<>();

    public GameStatus() {
        this.score = 0;
        this.highScore = 0;
        this.gameOver = false;
    }

    public enum GameState {
        START_MENU,
        WAITING_TO_START,
        PLAYING,
        GAME_OVER
    }

    public GameState getState() {
        return currentState;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public void incrementScore(double amount) {
        this.score += amount;
        notifyScoreChanged(this.score);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if (gameOver) {
            if (this.score > this.highScore) {
                this.highScore = this.score;
            }
            notifyGameOver();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyScoreChanged(double newScore) {
        for (Observer o : new ArrayList<>(observers)) {
            o.onScoreChanged(newScore);
        }
    }

    @Override
    public void notifyGameOver() {
        for (Observer o : new ArrayList<>(observers)) {
            o.onGameOver();
        }
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        notifyScoreChanged(this.score);
    }

    public double getHighScore() {
        return highScore;
    }

    public void setHighScore(double highScore) {
        this.highScore = highScore;
        notifyScoreChanged(this.highScore);
    }

    public void reset() {
        this.score = 0;
        this.gameOver = false;
        notifyScoreChanged(this.score);
    }

    public void resetHighScore() {
        this.highScore = 0;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getScoreText() {
        return String.valueOf((int) score);
    }
}

