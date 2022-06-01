package RankingViews;

/**
 * Class to manage the TableView
 */
public class PlayerOnly {
	private int place;
	private int score;
	private String username;
	private String gamename;

	/**
	 * Constructor of the table view
	 * @param place Place in the rank of the user
	 * @param score Score of the user
	 * @param username Username of the user
	 * @param gamename game name of the User
	 */
	public PlayerOnly(int place, int score, String username, String gamename) {
		this.place = place;
		this.score = score;
		this.username = username;
		this.gamename = gamename;
	}

	/**
	 * Getter of User NAme
	 * @return returns the user name
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sette of the username
	 * @param username Sets the user name of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Getter of Game Name
	 * @return returns the Game Name
	 */
	public String getGamename() {
		return gamename;
	}

	/**
	 * Setter of game name
	 * @param gamename Sets the Game Name of the user
	 */
	public void setConfiguration(String gamename) {
		this.gamename = gamename;
	}

	/**
	 * Getter of Score
	 * @return returns the Score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Setter of Score
	 * @param score Score of the user
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Getter of Place
	 * @return returns the Place
	 */
	public int getPlace() {
		return place;
	}
	
	/**
	 * Setter of Place
	 * @param place place of the user
	 */
	public void setPlace(int place) {
		this.place = place;
	}

}
