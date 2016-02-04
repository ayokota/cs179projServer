package objects;

import com.google.gson.Gson;

public class Message {
	private int id;
	private String body;
	private User sender;
	private User receiver;
	
	public Message (int id, String body, User sender, User receiver) {
		this.id = id;
		this.body = body;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public static void main(String[] args) {
		//System.out.println(JSON);
		Message newmsg = new Message(1, "hi", null, null);
		System.out.println(new Gson().toJson(newmsg));

	}
}
