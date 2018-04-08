/**
 * Holds the question of the current user
 * And the person who replied to the question
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Question {
	Player questioner;
	Player answerer;
	String token;
	String weapon;
	String room;
	
	public Question(Player questioner,String token, String weapon, String room) {
		this.questioner = questioner;
		this.token = token;
		this.weapon = weapon;
		this.room = room;
		this.answerer = null;
	}
	
	//If a person has the card, they would answer the question
	public void setAnswerer(Player answerer) {
		this.answerer = answerer;
	}
	
	//Accessors
	public Player getQuestioner() {
		return questioner;
	}
	
	public Player getAnswerer() {
		return answerer;
	}
	
	public String getTokenName() {
		return token;
	}
	
	public String getWeaponName() {
		return weapon;
	}
	
	public String getRoomName() {
		return room;
	}
		
	/*
	 *returns what the player suggested 
	 */
	public String toString() {
		String question;
		question = "====="+questioner.getName() + "[" +questioner.getToken().getTokenName()+ "]" + " SUGGESTED=====";
		
		question += "\nToken:        " + token.toUpperCase() + "\nWeapon:     " + weapon.toUpperCase() + "\nRoom:         " + room.toUpperCase();
		
		if(answerer != null) {
			question += "\nAnswered by: " + answerer.getName() +"[" + answerer.getToken().getTokenName() + "]";
		}else {
			question += "\nAnswered by: Null";
		}
		return question;
	}
	
}
