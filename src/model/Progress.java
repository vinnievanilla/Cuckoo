package model;
import model.SQLite;



public class Progress {

	
	double progress;
	
	public Progress(String user) {
		setProgress(user);
	}
	
	public void setProgress(String usr) {
		
		SQLite dbAccess = new SQLite();
		String user = usr;
		
		int totalCorrect = dbAccess.getTotalCorrect(user);
		int totalAttempt = dbAccess.getTotalAttempt(user);
		
		progress = (double) totalCorrect/ (double) totalAttempt; 
						
	}
	
	public double getProgress() {
		
		
		return progress;
	}
	
	
}
