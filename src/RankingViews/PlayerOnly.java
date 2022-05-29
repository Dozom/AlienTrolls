package RankingViews;

public class PlayerOnly {
	private int place;
	private int score;
	private String username;
	private String gamename;

	public PlayerOnly(int place, int score, String username, String gamename) {
		this.place = place;
		this.score = score;
		this.username = username;
		this.gamename = gamename;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGamename() {
		return gamename;
	}
	public void setConfiguration(String gamename) {
		this.gamename = gamename;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}

}
