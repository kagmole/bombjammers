package com.icebox.bombjammers.loaders;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ragebox.bombframework.bwt.BTAnimatedImage;
import com.ragebox.bombframework.bwt.BTImage;

public class BJAnimationLoader {
	
	private static final String TN_X = "x";
	private static final String TN_Y = "y";
	private static final String TN_WIDTH = "width";
	private static final String TN_HEIGHT = "height";
	private static final String TN_NUMBER = "number";
	private static final String TN_SPEED = "speed";

	private static BJAnimationLoader instance;
	
	private Element root;
	
	private BJAnimationLoader() {
		root = null;
	}
	
	public static BJAnimationLoader getInstance() {
		if (instance == null) {
			instance = new BJAnimationLoader();
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
	
	public BTAnimatedImage getAnimation(String tagName, String imageFilePath) {
		NodeList animationList = root.getElementsByTagName(tagName);
		
		Element animationElement = (Element) animationList.item(0);
		
		int x;
		int y;
		int width;
		int height;
		int number;
		int speed;
		
		/* Coordonnées (x, y) */
		x = Integer.parseInt(animationElement.getElementsByTagName(
				TN_X).item(0).getTextContent());
		
		y = Integer.parseInt(animationElement.getElementsByTagName(
				TN_Y).item(0).getTextContent());
		
		/* Dimensions (w, h) */
		width = Integer.parseInt(animationElement.getElementsByTagName(
				TN_WIDTH).item(0).getTextContent());
		
		height = Integer.parseInt(animationElement.getElementsByTagName(
				TN_HEIGHT).item(0).getTextContent());
		
		/* Nombre d'images */
		number = Integer.parseInt(animationElement.getElementsByTagName(
				TN_NUMBER).item(0).getTextContent());
		
		/* Animation delay */
		speed = Integer.parseInt(animationElement.getElementsByTagName(
				TN_SPEED).item(0).getTextContent());
		
		return extractAnimatedImage(x, y, width, height, number, speed,
				imageFilePath);
	}

	private BTAnimatedImage extractAnimatedImage(int x, int y, int width,
			int height, int number, int speed, String imageFilePath) {
		BufferedImage contentImage = null;
		
		try {			
			contentImage = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			System.err.println("Image IO (read) : "
					+ "Impossible de lire le fichier passé en paramètre !");
		}
		LinkedList<BTImage> imageList = new LinkedList<BTImage>();
		
		for (int i = 0; i < number; ++i) {
			BufferedImage animationImage = contentImage.getSubimage(
					x + width * i, y, width, height);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				ImageIO.write(animationImage, "png", baos);
			} catch (IOException e) {
				System.err.println("Image IO (write) : "
						+ "Impossible de créer un buffer pour l'image !");
			}
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			imageList.add(new BTImage(0, 0, width, height, is));
			
			try {
				baos.close();
			} catch (IOException e) {
				System.err.println("baos.close() : "
						+ "Impossible de refermer le flux !");
			}
		}
		
		return new BTAnimatedImage(0, 0, 0, 0, speed, imageList);
	}
}
