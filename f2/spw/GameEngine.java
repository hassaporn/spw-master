package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	private boolean playerLive = true, playerLive2 = true;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> enemies2 = new ArrayList<Enemy>();
	private ArrayList<Extrapoint> extra = new ArrayList<Extrapoint>();	
	private SpaceShip v, v2;	
	
	private Timer timer;
	
	private long score = 0, score2 = 0, totalScore = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip v2) {
		this.gp = gp;
		this.v = v;
		this.v2 = v2;	
		
		gp.sprites.add(v);
		gp.sprites.add(v2);
		
			timer = new Timer(50 , new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
				}
			});
				
			timer.setRepeats(true);
	}
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30, 7);
		Enemy e2 = new Enemy((int)(Math.random()*390), 30, 5);
		Extrapoint e3 = new Extrapoint((int)(Math.random()*390), 1);
		gp.sprites.add(e);
		gp.sprites.add(e2);
		gp.sprites.add(e3);
		enemies.add(e);
		enemies2.add(e2);
		extra.add(e3);
	}
	
	private void process(){
		if(Math.random() < difficulty()){
			generateEnemy();
		}
		
		if (playerLive == false && playerLive2 == false){
			die();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				if (playerLive == true){
					score += 1;
				}
				else{
					score += 0;
				}
			}
		}

		Iterator<Enemy> e2_iter = enemies2.iterator();
		while(e2_iter.hasNext()){
			Enemy e2 = e2_iter.next();
			e2.proceed();
			
			if(!e2.isAlive()){
				e2_iter.remove();
				gp.sprites.remove(e2);
				if (playerLive2 == true){
					score2 += 1;
				}
				else{
					score2 += 0;
				}
			}
		}
		
		Iterator<Extrapoint> e3_iter = extra.iterator();
		while(e3_iter.hasNext()){
			Extrapoint e3 = e3_iter.next();
			e3.proceed();
			
			if(!e3.isAlive()){
				e3_iter.remove();
				gp.sprites.remove(e3);
				score += 0;
				score2 += 0;
			}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double er2;
		Rectangle2D.Double er3;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				playerLive = false;
				return;
			}
		}
		for(Enemy e2 : enemies2){
			er2 = e2.getRectangle();
			if(er2.intersects(vr2)){
				playerLive2 = false;
				return;
			}
		}
		for(Extrapoint e3 : extra){
			er3 = e3.getRectangle();
			if(er3.intersects(vr)){
				if (playerLive == true){
					score += 5;
				}
				else{
					score += 0;
				}
				return;
			}
			else if(er3.intersects(vr2)){
				if (playerLive2 == true){
					score2 += 5;
				}
				else{
					score2 += 0;
				}
				return;
			} 
		}
	}
	
	public void die(){
		timer.stop();
		totalScore = score + score2;
	}
	
	void controlVehicle(KeyEvent e) {
		if (playerLive == true){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				v.move(-1);
				break;
			case KeyEvent.VK_RIGHT:
				v.move(1);
				break;
			}
		}
		if (playerLive2 == true){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				v2.move(-1);
				break;
			case KeyEvent.VK_S:
				v2.move(1);
				break;
			}
		}
	}
	
	public double difficulty(){
		if (score < 50){
			difficulty += 0.001;
		}
		else if (score < 100){
			difficulty += 0.002;
		}
		else if (score < 150){
			difficulty += 0.003;
		}
		else if (score < 200){
			difficulty += 0.004;
		}
		return difficulty;
	}

	public long getScore(){
		return score;
	}
	public long getScore2(){
		return score2;
	}
	public long getTotalScore(){
		return totalScore;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
