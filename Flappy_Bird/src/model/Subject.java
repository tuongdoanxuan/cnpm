package Model;

public interface Subject {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyScoreChanged(double newScore);

    void notifyGameOver();
}
