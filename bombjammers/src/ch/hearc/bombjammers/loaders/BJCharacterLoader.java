package ch.hearc.bombjammers.loaders;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ch.hearc.bombjammers.elements.character.BJCharacter;
import ch.hearc.bombtoolkit.bwt.BTImage;

public class BJCharacterLoader {
	
	private static final String TN_NAME = "name";
	//private static final String TN_POWER_TYPE = "power_type";
	private static final String TN_SPEED = "speed";
	private static final String TN_STRENGTH = "strength";
	private static final String TN_WEIGHT = "weight";
	
	private static BJCharacterLoader instance;
	
	private Element root;
	
	private BJCharacterLoader() {
		root = null;
	}
	
	public static BJCharacterLoader getInstance() {
		if (instance == null) {
			instance = new BJCharacterLoader();
		}
		return instance;
	}
	
	public void loadXMLFile(String xmlFilePath) {		
		File xmlFile = new File(xmlFilePath);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document document = null;
		
		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(xmlFile);
		} catch (Exception e) {
			System.err.println("XML parser : "
					+ "Impossible de lire le fichier passé en paramètre !");
		}
		document.normalize();
		
		root = document.getDocumentElement();
	}
	
	public BJCharacter getCharacter(String fileFolder, boolean reverse) {		
		int weight;
		int strength;
		int speed;
		
		String name;
		
		/* Poids */
		weight = Integer.parseInt(root.getElementsByTagName(
				TN_WEIGHT).item(0).getTextContent());
		
		/* Force */
		strength = Integer.parseInt(root.getElementsByTagName(
				TN_STRENGTH).item(0).getTextContent());
		
		/* Vitesse */
		speed = Integer.parseInt(root.getElementsByTagName(
				TN_SPEED).item(0).getTextContent());
		
		/* Nom */
		name = root.getElementsByTagName(TN_NAME).item(0).getTextContent();
		
		/* Petit portrait */
		BTImage portraitBig = new BTImage(0, 0, 256, 256,
				fileFolder+"big_portrait.png");
		
		/* Grand portrait */
		BTImage portraitSmall = new BTImage(0, 0, 128, 128,
				fileFolder+"small_portrait.png");
		
		return new BJCharacter(0, 0, 64, 64, speed, strength, weight, name,
				portraitBig, portraitSmall, reverse, fileFolder);
	}
}
