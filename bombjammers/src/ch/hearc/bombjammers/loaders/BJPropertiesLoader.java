package ch.hearc.bombjammers.loaders;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class BJPropertiesLoader {
	
	/*
	 * CHEMIN DU FICHIER	
	 */
	private static final String GAME_PROPERTIES_FILEPATH = "config.ini";
	
	/*
	 * COMMENTAIRES DU FICHIER
	 */
	private static final String GAME_PROPERTIES_COMMENTS = "gameProperties";
	
	/*
	 * VALEURS PAR DEFAUT
	 */
	private static final String DEFAULT_VOLUME_MUSIC = "100";
	private static final String DEFAULT_VOLUME_EFFECTS = "100";
	private static final String DEFAULT_VOLUME_AMBIENCE = "100";
	private static final String DEFAULT_FULLSCREEN = "0";
	private static final String DEFAULT_RESOLUTION_WIDTH = "800";
	private static final String DEFAULT_RESOLUTION_HEIGHT = "600";
	
	private static final String DEFAULT_PLAYER1_UP = "17";
	private static final String DEFAULT_PLAYER1_DOWN = "31";
	private static final String DEFAULT_PLAYER1_RIGHT = "32";
	private static final String DEFAULT_PLAYER1_LEFT = "30";
	private static final String DEFAULT_PLAYER1_A = "58";
	private static final String DEFAULT_PLAYER1_B = "47";
	private static final String DEFAULT_PLAYER1_START = "28";
	
	private static final String DEFAULT_PLAYER2_UP = "200";
	private static final String DEFAULT_PLAYER2_DOWN = "208";
	private static final String DEFAULT_PLAYER2_RIGHT = "205";
	private static final String DEFAULT_PLAYER2_LEFT = "203";
	private static final String DEFAULT_PLAYER2_A = "24";
	private static final String DEFAULT_PLAYER2_B = "25";
	private static final String DEFAULT_PLAYER2_START = "28";
	
	/*
	 * NOM DES PROPRIETES
	 */
	public static final String VOLUME_MUSIC = "volumeMusic";
	public static final String VOLUME_EFFECTS = "volumeEffects";
	public static final String VOLUME_AMBIENCE = "volumeAmbience";
	public static final String FULLSCREEN = "fullscreen";
	public static final String RESOLUTION_WIDTH = "resolutionWidth";
	public static final String RESOLUTION_HEIGHT = "resolutionHeight";
	
	public static final String PLAYER1_UP = "player1Up";
	public static final String PLAYER1_DOWN = "player1Down";
	public static final String PLAYER1_RIGHT = "player1Right";
	public static final String PLAYER1_LEFT = "player1Left";
	public static final String PLAYER1_A = "player1A";
	public static final String PLAYER1_B = "player1B";
	public static final String PLAYER1_START = "player1Start";
	
	public static final String PLAYER2_UP = "player2Up";
	public static final String PLAYER2_DOWN = "player2Down";
	public static final String PLAYER2_RIGHT = "player2Right";
	public static final String PLAYER2_LEFT = "player2Left";
	public static final String PLAYER2_A = "player2A";
	public static final String PLAYER2_B = "player2B";
	public static final String PLAYER2_START = "player2Start";
	
	/*
	 * INSTANCE
	 */
	private static BJPropertiesLoader instance;
	
	/*
	 * PROPRIETES DU JEU	
	 */
	private Properties gameProperties;
	
	private BJPropertiesLoader() {
		loadProperties();
	}
	
	public static BJPropertiesLoader getInstance() {
		if (instance == null) {
			instance = new BJPropertiesLoader();
		}
		return instance;
	}
	
	private void loadProperties() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		
		gameProperties = new Properties();
		
		try {
			fis = new FileInputStream(GAME_PROPERTIES_FILEPATH);
			bis = new BufferedInputStream(fis);
			
			gameProperties.load(bis);
		} catch (FileNotFoundException e) {
			/* Fichier introuvable -> Chargement des propriétés par défaut */
		} catch (IOException e) {
			/* Fichier corrompu -> Chargement des propriétés par défaut */
		}
		
		//TODO Buggé le fait à chaque fois qu'on re-ouvre le jeu
//		validateProperties();
		
		try {
			bis.close();
			fis.close();
		} catch (IOException e) {
			/* Erreur lors de la fermeture du fichier */
		}
	}
	
	private void validateProperties() {
		if (!gameProperties.containsKey(VOLUME_MUSIC))  {
			gameProperties.put(VOLUME_MUSIC, DEFAULT_VOLUME_MUSIC);
		}
		
		if (!gameProperties.containsKey(VOLUME_EFFECTS))  {
			gameProperties.put(VOLUME_EFFECTS, DEFAULT_VOLUME_EFFECTS);
		}
		
		if (!gameProperties.containsKey(VOLUME_AMBIENCE))  {
			gameProperties.put(VOLUME_AMBIENCE, DEFAULT_VOLUME_AMBIENCE);
		}
		
		if (!gameProperties.containsKey(FULLSCREEN))  {
			gameProperties.put(FULLSCREEN, DEFAULT_FULLSCREEN);
		}
		
		if (!gameProperties.containsKey(RESOLUTION_WIDTH))  {
			gameProperties.put(RESOLUTION_WIDTH, DEFAULT_RESOLUTION_WIDTH);
		}
		
		if (!gameProperties.containsKey(RESOLUTION_HEIGHT))  {
			gameProperties.put(RESOLUTION_HEIGHT, DEFAULT_RESOLUTION_HEIGHT);
		}
		
		if (!gameProperties.containsKey(PLAYER1_UP))  {
			gameProperties.put(PLAYER1_UP, DEFAULT_PLAYER1_UP);
		}
		
		if (!gameProperties.containsKey(PLAYER1_DOWN))  {
			gameProperties.put(PLAYER1_DOWN, DEFAULT_PLAYER1_DOWN);
		}
		
		if (!gameProperties.containsKey(PLAYER1_RIGHT))  {
			gameProperties.put(PLAYER1_RIGHT, DEFAULT_PLAYER1_RIGHT);
		}
		
		if (!gameProperties.containsKey(PLAYER1_LEFT))  {
			gameProperties.put(PLAYER1_LEFT, DEFAULT_PLAYER1_LEFT);
		}
		
		if (!gameProperties.containsKey(PLAYER1_A))  {
			gameProperties.put(PLAYER1_A, DEFAULT_PLAYER1_A);
		}
		
		if (!gameProperties.containsKey(PLAYER1_B))  {
			gameProperties.put(PLAYER1_B, DEFAULT_PLAYER1_B);
		}
		
		if (!gameProperties.containsKey(PLAYER1_START))  {
			gameProperties.put(PLAYER1_START, DEFAULT_PLAYER1_START);
		}
		
		if (!gameProperties.containsKey(PLAYER2_UP))  {
			gameProperties.put(PLAYER2_UP, DEFAULT_PLAYER2_UP);
		}
		
		if (!gameProperties.containsKey(PLAYER2_DOWN))  {
			gameProperties.put(PLAYER2_DOWN, DEFAULT_PLAYER2_DOWN);
		}
		
		if (!gameProperties.containsKey(PLAYER2_RIGHT))  {
			gameProperties.put(PLAYER2_RIGHT, DEFAULT_PLAYER2_RIGHT);
		}
		
		if (!gameProperties.containsKey(PLAYER2_LEFT))  {
			gameProperties.put(PLAYER2_LEFT, DEFAULT_PLAYER2_LEFT);
		}
		
		if (!gameProperties.containsKey(PLAYER2_A))  {
			gameProperties.put(PLAYER2_A, DEFAULT_PLAYER2_A);
		}
		
		if (!gameProperties.containsKey(PLAYER2_B))  {
			gameProperties.put(PLAYER2_B, DEFAULT_PLAYER2_B);
		}
		
		if (!gameProperties.containsKey(PLAYER2_START))  {
			gameProperties.put(PLAYER2_START, DEFAULT_PLAYER2_START);
		}
	}
	
	public void saveProperties() {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			fos = new FileOutputStream(GAME_PROPERTIES_FILEPATH);
			bos = new BufferedOutputStream(fos);
			
			gameProperties.store(bos, GAME_PROPERTIES_COMMENTS);
		} catch (FileNotFoundException e) {
			/* Fichier introuvable -> Chargement des propriétés par défaut */
		} catch (IOException e) {
			/* Fichier corrompu -> Chargement des propriétés par défaut */
		}
		
		try {
			bos.close();
			fos.close();
		} catch (IOException e) {
			/* Erreur lors de la fermeture du fichier */
		}
	}
	
	public String getProperty(String key) {
		return gameProperties.getProperty(key);
	}
	
	public void setProperty(String key, String value) {
		gameProperties.setProperty(key, value);
	}
}
