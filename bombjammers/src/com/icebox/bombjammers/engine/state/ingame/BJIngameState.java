package com.icebox.bombjammers.engine.state.ingame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import javax.swing.Timer;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import com.icebox.bombjammers.elements.bomb.BJBomb;
import com.icebox.bombjammers.elements.character.BJCharacter;
import com.icebox.bombjammers.elements.character.BombExplodeListener;
import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.loaders.BJAnimationLoader;
import com.icebox.bombjammers.loaders.BJCharacterLoader;
import com.icebox.bombjammers.loaders.BJPropertiesLoader;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTAnimatedImage;
import com.ragebox.bombframework.bwt.BTButton;
import com.ragebox.bombframework.bwt.BTImage;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTStatePanelManager;
import com.ragebox.bombframework.bwt.BTSwitchPanel;
import com.ragebox.bombframework.bwt.event.BTAnimationAdapter;
import com.ragebox.bombframework.bwt.event.BTHitboxAdapter;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;
import com.ragebox.bombframework.bwt.event.BTKeyboardListener;

public class BJIngameState extends BTStatePanel implements BJActionable {

	private int scoreLeft;
	private int scoreRight;

	private BJBomb bomb;

	private BJCharacter characterLeft;
	private BJCharacter characterRight;
	
	private BTAnimatedImage explosionAnimation;
	
	private String folderP1;
	private String folderP2;

	private BJEngine engine;

	private BTButton buttonScoreLeft;
	private BTButton buttonScoreRight;

	private BTImage groundImage;

	private Rectangle2D area;
	private String stateToChange;
	
	private Music song;
	private Sound explosionSound;
	private double volEffects;
	
	public BJIngameState(String nameId, BTSwitchPanel parent, 
						 BJEngine engine) {
		super(nameId, parent);

		this.engine = engine;

		area = new Rectangle2D.Float(0, 0, 800, 600);
	}

	@Override
	public void doAction() {
		switch (stateToChange) {
		case BJEngine.MAIN_MENU_ID:
			characterLeft.setFocus(BJEngine.MAIN_FOCUS, false);
			characterRight.setFocus(BJEngine.FOCUS_1, false);
			BTStatePanelManager.getStatePanel(BJEngine.MAIN_MENU_ID).activate();
			break;
		default:
			break;
		}
	}

	@Override
	public void initComponent() {
		createComponents();
		createKeyboardListeners();
		createAnimationListeners();
		createHitboxListeners();
	}

	@Override
	public void activateComponent() {
		characterLeft.setFocus(BJEngine.MAIN_FOCUS, true);
		characterRight.setFocus(BJEngine.FOCUS_1, true);

		scoreLeft = 0;
		scoreRight = 0;
		
		buttonScoreLeft.setText(String.valueOf(scoreLeft));
		buttonScoreRight.setText(String.valueOf(scoreRight));
		
		resetPlayersPos();
		
		characterLeft.reset();
		characterRight.reset();

		engine.allowInput();
		
		volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;
	}

	private void createComponents() {
		
		TinySound.shutdown();
		TinySound.init();
		
		groundImage = new BTImage(0, 0, 800, 600,
				"res/maps/kaamelott/kaamelott.png");

		buttonScoreLeft = new BTButton(320, 0, 60, 60, "0");
		buttonScoreRight = new BTButton(420, 0, 60, 60, "0");

		/* Chargement des personnages */
		BJCharacterLoader loader = BJCharacterLoader.getInstance();

		loader.loadXMLFile(folderP1+"properties.xml");

		characterLeft = loader.getCharacter(
				folderP1, false);
						
		BJAnimationLoader annimationLoader = BJAnimationLoader.getInstance();
		annimationLoader.loadXMLFile("./res/element/explosion/info.xml");
		explosionAnimation = annimationLoader.getAnimation("explosion",
						"./res/element/explosion/explosion.png");
		explosionAnimation.setVisible(false);
		
		loader.loadXMLFile(folderP2 + "properties.xml");
		
		characterRight = loader.getCharacter(
				folderP2, true);

		bomb = new BJBomb(100, 100, 32, 32, this);

		characterLeft.setBomb(bomb);
		characterRight.setBomb(bomb);

		characterLeft.changeMask(BJCharacter.HAS_BOMB);
		
		characterLeft.addBombExplodeListener(new BombExplodeListener() {

			@Override
			public void bombExplosed() {
				explosionAnimation.setX(characterLeft.getX());
				explosionAnimation.setY(characterLeft.getY());
				explosionAnimation.setVisible(true);
				
				startNewRound(true, true);
				
				characterLeft.blinkOverTime(50, 600);
				
				explosionSound.play(volEffects);
				
				Timer timer = new Timer(0, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						explosionAnimation.setVisible(false);
					}
				});
				
				timer.setRepeats(false);
				timer.setInitialDelay(100*6);
				timer.start();
			}
			
		});
		
		characterRight.addBombExplodeListener(new BombExplodeListener() {

			@Override
			public void bombExplosed() {				
				explosionAnimation.setX(characterRight.getX());
				explosionAnimation.setY(characterRight.getY());
				explosionAnimation.setVisible(true);
				
				startNewRound(false, true);
				
				characterRight.blinkOverTime(50, 600);
				
				explosionSound.play(volEffects);
				
				Timer timer = new Timer(0, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						explosionAnimation.setVisible(false);
					}
				});
				
				timer.setRepeats(false);
				timer.setInitialDelay(100*6);
				timer.start();
			}
			
		});

		addComponent(groundImage);
		addComponent(characterLeft);
		addComponent(characterRight);
		addComponent(bomb);
		addComponent(buttonScoreLeft);
		addComponent(buttonScoreRight);		
		addComponent(explosionAnimation);
		
		song = TinySound.loadMusic(new File("./res/music/kaamelott.ogg"));
		song.play(true,Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_MUSIC))/100);
		
		explosionSound = TinySound.loadSound(new File("./res/sound/explosion.wav"));
	}

	private void createKeyboardListeners() {
		
		characterLeft.addKeyboardListener(new BTKeyboardListener() {

			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode()) {
				case Keyboard.KEY_W:
					characterLeft.changeMask(BJCharacter.MOVE_UP);
					break;
				case Keyboard.KEY_A:
					characterLeft.changeMask(BJCharacter.MOVE_LEFT);
					break;
				case Keyboard.KEY_S:
					characterLeft.changeMask(BJCharacter.MOVE_DOWN);
					break;
				case Keyboard.KEY_D:
					characterLeft.changeMask(BJCharacter.MOVE_RIGHT);
					break;
				case Keyboard.KEY_E:
					characterLeft.changeMask(BJCharacter.BUTTON_A);
					break;
				case Keyboard.KEY_R:
					characterLeft.changeMask(BJCharacter.BUTTON_B);
					break;
				default:
					break;
				}
			}

			@Override
			public void keyReleased(BTKeyboardEvent event) {
				switch (event.getKeyCode()) {
				case Keyboard.KEY_W:
					characterLeft.changeMask(BJCharacter.MOVE_UP);
					break;
				case Keyboard.KEY_A:
					characterLeft.changeMask(BJCharacter.MOVE_LEFT);
					break;
				case Keyboard.KEY_S:
					characterLeft.changeMask(BJCharacter.MOVE_DOWN);
					break;
				case Keyboard.KEY_D:
					characterLeft.changeMask(BJCharacter.MOVE_RIGHT);
					break;
				case Keyboard.KEY_E:
					characterLeft.changeMask(BJCharacter.BUTTON_A);
					break;
				case Keyboard.KEY_R:
					characterLeft.changeMask(BJCharacter.BUTTON_B);
					break;
				default:
					break;
				}
			}
		});

		characterRight.addKeyboardListener(new BTKeyboardListener() {

			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode()) {
				case Keyboard.KEY_I:
					characterRight.changeMask(BJCharacter.MOVE_UP);
					break;
				case Keyboard.KEY_J:
					characterRight.changeMask(BJCharacter.MOVE_LEFT);
					break;
				case Keyboard.KEY_K:
					characterRight.changeMask(BJCharacter.MOVE_DOWN);
					break;
				case Keyboard.KEY_L:
					characterRight.changeMask(BJCharacter.MOVE_RIGHT);
					break;
				case Keyboard.KEY_U:
					characterRight.changeMask(BJCharacter.BUTTON_A);
					break;
				case Keyboard.KEY_Z:
					characterRight.changeMask(BJCharacter.BUTTON_B);
					break;
				default:
					break;
				}
			}

			@Override
			public void keyReleased(BTKeyboardEvent event) {
				switch (event.getKeyCode()) {
				case Keyboard.KEY_I:
					characterRight.changeMask(BJCharacter.MOVE_UP);
					break;
				case Keyboard.KEY_J:
					characterRight.changeMask(BJCharacter.MOVE_LEFT);
					break;
				case Keyboard.KEY_K:
					characterRight.changeMask(BJCharacter.MOVE_DOWN);
					break;
				case Keyboard.KEY_L:
					characterRight.changeMask(BJCharacter.MOVE_RIGHT);
					break;
				case Keyboard.KEY_U:
					characterRight.changeMask(BJCharacter.BUTTON_A);
					break;
				case Keyboard.KEY_Z:
					characterRight.changeMask(BJCharacter.BUTTON_B);
					break;
				default:
					break;
				}
			}
		});
	}

	private void createAnimationListeners() {
		characterLeft.addAnimationListener(new BTAnimationAdapter() {

			@Override
			public void translationFinished(int id) {
				switch (id) {
				case 1:
					characterLeft.unblock();
				}
			}
		});

		characterRight.addAnimationListener(new BTAnimationAdapter() {

			@Override
			public void translationFinished(int id) {
				switch (id) {
				case 1:
					characterRight.unblock();
				}
			}
		});
	}

	private void createHitboxListeners() {
		Rectangle2D northBarrier = new Rectangle2D.Float(0, 0, 800, 50);
		Rectangle2D eastBarrier = new Rectangle2D.Float(750, 0, 50, 600);
		Rectangle2D southBarrier = new Rectangle2D.Float(0, 550, 800, 50);
		Rectangle2D westBarrier = new Rectangle2D.Float(0, 0, 50, 600);
		Rectangle2D centerBarrier = new Rectangle2D.Float(401, 0, 6, 600);
		
		Rectangle2D borderLeft = new Rectangle2D.Float(0, 0, 1, 600);
		Rectangle2D borderRight = new Rectangle2D.Float(799, 0, 1, 600);

		// Empêcher le personnage de gauche de sortir du terrain
		characterLeft.addHitboxListener(northBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterLeft.setY(50);
			}
		});

		characterLeft.addHitboxListener(westBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterLeft.setX(50);
			}
		});

		characterLeft.addHitboxListener(southBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterLeft.setY(486);
			}
		});

		characterLeft.addHitboxListener(centerBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterLeft.block();
				characterLeft.setX(337);
				characterLeft.translateOverTime(-50, 0, 200, 1);
				characterLeft.blinkOverTime(50, 200);
			}
		});

		characterLeft.addHitboxListener(bomb.getHitbox(),
				new BTHitboxAdapter() {

					@Override
					public void hitboxIntersected() {
						if ((bomb.getMask() & BJBomb.MOVING_WEST) != 0) {
							characterLeft.changeMask(BJCharacter.HAS_BOMB);
							bomb.changeMask(BJBomb.MOVING_WEST);
						}
					}
				});

		// Empêcher le personnage de droite de sortir du terrain
		characterRight.addHitboxListener(northBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterRight.setY(50);
			}
		});

		characterRight.addHitboxListener(eastBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterRight.setX(686);
			}
		});

		characterRight.addHitboxListener(southBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterRight.setY(486);
			}
		});

		characterRight.addHitboxListener(centerBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				characterRight.block();
				characterRight.setX(407);
				characterRight.translateOverTime(50, 0, 200, 1);
				characterRight.blinkOverTime(50, 200);
			}
		});

		characterRight.addHitboxListener(bomb.getHitbox(),
				new BTHitboxAdapter() {

					@Override
					public void hitboxIntersected() {
						if ((bomb.getMask() & BJBomb.MOVING_EAST) != 0) {
							characterRight.changeMask(BJCharacter.HAS_BOMB);
							bomb.changeMask(BJBomb.MOVING_EAST);
						}
					}
				});

		bomb.addHitboxListener(northBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				bomb.setY(50);
				bomb.inverseSpeedY();
			}
		});

		bomb.addHitboxListener(southBarrier, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				bomb.setY(518);
				bomb.inverseSpeedY();
			}
		});

		bomb.addHitboxListener(borderLeft, new BTHitboxAdapter() {

			@Override
			public void hitboxIntersected() {
				bomb.changeMask(BJBomb.MOVING_WEST);
				bomb.setX(100);
				bomb.setY(100);
				startNewRound(true, false);
			}
		});
		
		bomb.addHitboxListener(borderRight, new BTHitboxAdapter() {
			
			@Override
			public void hitboxIntersected() {
				bomb.changeMask(BJBomb.MOVING_EAST);
				bomb.setX(100);
				bomb.setY(100);
				startNewRound(false, false);
			}
		});
	}

	public void startNewRound(boolean isLeftCharacterStarting, boolean bombExplosed) {
		if (isLeftCharacterStarting) {
			if (!bombExplosed) {
				characterLeft.changeMask(BJCharacter.HAS_BOMB);
			}
			++scoreRight;
			
			buttonScoreRight.setText(String.valueOf(scoreRight));
		} else {
			if (!bombExplosed) {
				characterRight.changeMask(BJCharacter.HAS_BOMB);
			}
			++scoreLeft;
			
			buttonScoreLeft.setText(String.valueOf(scoreLeft));
		}

		if (scoreRight >= 10 || scoreLeft >= 10) {
			stateToChange = BJEngine.MAIN_MENU_ID;
			engine.blockInput();
			engine.closeGate(this);
		}
		
		resetPlayersPos();
	}

	public Rectangle2D getArea() {
		return area;
	}
	
	
	/**
	 * Ajout les répertoir du joueur 1 et du joueur 2
	 * @param folderP1
	 * @param folderP2
	 */
	public void setFolders(String folderP1, String folderP2){
		this.folderP1 = folderP1;
		this.folderP2 = folderP2;
	}
	
	private void resetPlayersPos() {
		characterLeft.setX(200);
		characterLeft.setY(250);

		characterRight.setX(586);
		characterRight.setY(250);	
	}
}
