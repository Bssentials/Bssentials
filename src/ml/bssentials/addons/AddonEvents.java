package ml.bssentials.addons;

public interface AddonEvents {
	/*
	 *	The Addon Manager is 0% done!!, this is just what I WOULD like to add NOT stuff thats allready added
	 * */
	
	
	/**
	 * Creates an addon
	 * 
	 * @author Bssentials Team
	 * */
	void createAddon();
	
	/**
	 * Runs in your addon when someone joins the game
	 * 
	 * @author Bssentials Team
	 * */
	void onLogin();
	
	/**
	 * Runs in your addon when someone left the game :(
	 * 
	 * @author Bssentials Team
	 * */
	void onLogout();
	
	/**
	 * Runs in your addon when someone chats
	 * 
	 * @author Bssentials Team
	 * */
	void onChat();
	
	/**
	 * Broadcasts an message to everywon on the serve
	 * 
	 * @param msg Message to broadcast
	 * @author Bssentials Team
	 * */
	void broadcastMessage(String msg);
	
	/**
     * Execute a command as Console
     *
     * @param cmd Command to Execute
     */
    void executeCommand(String cmd);
}
