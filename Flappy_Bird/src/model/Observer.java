package model;

public interface Observer {
	public void onScoreChanged(double newScore);
    public void onGameOver();
}
