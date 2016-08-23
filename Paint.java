import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Paint extends JFrame implements ActionListener{

	Grafico gr;
	Font big = new Font("Digiface", Font.BOLD, 30);
	Timer time;
	final int jx = 400;
	final int jy = 800;
	final int carX = 20;
	final int carY = 40;
	int score = 0;
	int posX= 200, posY = 700, dirX, dirY, fim;
	static float linePOS = 0;
	int[] obsX = new int[6];
	int[] obsY = new int[6];
	Color[] cor = new Color[6];
	int[] velY = new int[6];


	class Grafico extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			if(fim==1){
				setBackground(Color.red);
				drawResultboard(score, g);
			}else{
				setBackground(Color.darkGray);
				drawSidewalk(posX, posY, carX, carY, g);
				drawLines(g);
				drawParticles(g);
				drawCar(posX, posY, carX, carY, g);
				drawDynamicShadowBOT(g);
				generateObstacles();
				drawObstacles(g);
				drawDynamicShadowTOP(g);
				drawShadow(posX, posY, carX, carY, g);
				drawScoreboard(score, g);
			}
		}
		public void drawResultboard(int score,Graphics g){
			Graphics2D resultBoard = (Graphics2D) g;
			resultBoard.setColor(Color.black);
			resultBoard.drawString("GAME OVER --- SCORE:"+score, 100, 400);
		}

		public void generateObstacles(){
			for(int i=0; i<6; i++){
				if(obsY[i]>900 || obsX[i]<20){
					obsY[i]=randX()*(-2);
					obsX[i]=randX();
					cor[i]= randColor();
				}else{
					obsY[i]=obsY[i]+3;
				}
			}
		}
		public void drawDynamicShadowBOT(Graphics g){
			for(int i=0; i<6; i++){
				Graphics2D shadow = (Graphics2D) g;
				Color shadowColor = new Color(0, 0, 0.1f, 0.5f);

				shadow.setColor(shadowColor);
				shadow.fill(new Rectangle2D.Double(obsX[i], obsY[i], carX, 10));
				if(obsX[i]>posX){
					int xpoints[] = {obsX[i]+carX, obsX[i], obsX[i]+3*(obsX[i]-posX)-carX, obsX[i]+3*(obsX[i]-posX)};
					int ypoints[] = {obsY[i]+carY, obsY[i], 3*(obsY[i]-posY-carY), 3*(obsY[i]-posY)};
					int npoints = 4;
					shadow.fillPolygon(xpoints, ypoints, npoints);

				}else{
					int xpoints[] = {obsX[i], obsX[i]+carX, -obsX[i]+3*(obsX[i]-posX)-carX, -obsX[i]+3*(obsX[i]-posX)};
					int ypoints[] = {obsY[i]+carY, obsY[i], 3*(obsY[i]-posY-carY), 3*(obsY[i]-posY)};
					int npoints = 4;
					shadow.fillPolygon(xpoints, ypoints, npoints);

				}
			}
		}

		public void drawDynamicShadowTOP(Graphics g){
			for(int i=0; i<6; i++){
				Graphics2D shadow = (Graphics2D) g;
				Color shadowColor = new Color(0, 0, 0.1f, 0.5f);

				shadow.setColor(shadowColor);
				shadow.fill(new Rectangle2D.Double(obsX[i], obsY[i], carX, 10));
				if(obsX[i]<posX-80){
					shadow.fill(new Rectangle2D.Double(obsX[i], obsY[i], 5, carY));
				}
				if(obsX[i]>posX+80){
					shadow.fill(new Rectangle2D.Double(obsX[i]+15, obsY[i], 5, carY));
				}

			}
		}

		public void drawObstacles(Graphics g){
			Graphics2D obs = (Graphics2D) g;
			for(int i=0; i<6; i++){


				obs.setColor(cor[i]);
				obs.fill(new Rectangle2D.Double(obsX[i], obsY[i], carX, carY));
				if(new Rectangle2D.Double(obsX[i], obsY[i], carX, carY).intersects(posX, posY, carX, carY)){
					fim = 1;
				}

				obs.setColor(Color.black);
				obs.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i]+10, 16, 8));
				obs.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i]+28, 16, 5));

				obs.setColor(Color.yellow);
				obs.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i], 4, 1));
				obs.fill(new Rectangle2D.Double(obsX[i]+14, obsY[i], 4, 1));
			}
		}

		public int randX(){
			Random rand = new Random();
			return rand.nextInt(390 - 10 + 1) + 1;
		}

		public Color randColor(){
			Random rand = new Random();
			int aux = rand.nextInt(5 - 1 + 1) + 1;
			if(aux==1){
				return Color.white;
			}else{
				if(aux==2){
					return Color.cyan;
				}else{
					if(aux==3){
						return Color.red;
					}else{
						if(aux==4){
							return Color.magenta;
						}else{
							return Color.yellow;
						}
					}
				}
			}
		}

		public void drawLines(Graphics g){
			Graphics2D line = (Graphics2D) g;

			line.setColor(Color.yellow);
			if(linePOS == 30){
				linePOS = 0;
			}else{
				linePOS=(float) (linePOS+5);
			}
			for(int i=0; i<800; i=i+30){
				line.fill(new Rectangle2D.Double(198, linePOS+i, 4, 10));
			}
		}

		public void drawScoreboard(int score, Graphics g){
			Graphics2D scoreBoard = (Graphics2D) g;
			scoreBoard.setColor(Color.black);
			scoreBoard.fill(new Rectangle2D.Double(0, jy, jx, jy+40));
			scoreBoard.setColor(Color.white);
			scoreBoard.drawString("--DrunkDriver--                       SCORE:"+score, 100, jy+20);
		}

		public void drawObstacle(Graphics g, int i){
			Graphics2D obsS = (Graphics2D) g;

			obsS.setColor(cor[i]);
			obsS.fill(new Rectangle2D.Double(obsX[i], obsY[i], carX, carY));

			obsS.setColor(Color.black);
			obsS.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i]+10, 16, 8));
			obsS.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i]+28, 16, 5));

			obsS.setColor(Color.yellow);
			obsS.fill(new Rectangle2D.Double(obsX[i]+2, obsY[i], 4, 1));
			obsS.fill(new Rectangle2D.Double(obsX[i]+14, obsY[i], 4, 1));

		}
		public void drawParticles(Graphics g){
			Graphics2D part = (Graphics2D) g;
			part.setPaint(Color.lightGray);
			for(int i=0; i<=100; i++){
				part.fill(new Rectangle2D.Double(randX() , randX()*2.1, 1, 5));
			}
		}

		public void drawShadow(int posX,int posY,int carX,int carY, Graphics g){
			Graphics2D shadow = (Graphics2D) g;
			Color shadowColor = new Color(0, 0, 0.1f, 0.7f);
			Color airColor = new Color(0, 0, 0.1f, 0.0f);

			GradientPaint gradientshadow = new GradientPaint(0, posY-400, shadowColor, 0, posY-100, airColor);
			shadow.setPaint(gradientshadow);
			shadow.fill(new Rectangle2D.Double(0 , posY-400, jx, posY-100));



			shadow.setColor(shadowColor);
			shadow.fill(new Rectangle2D.Double(0, posY+10, jx, jy));
			shadow.fill(new Rectangle2D.Double(0 , 0, jx, posY-400));

			int xpoints[] = {posX-400, posX-10, posX-400};
			int ypoints[] = {posY-900, posY+10, posY+10};
			int npoints = 3;
			shadow.fillPolygon(xpoints, ypoints, npoints);
			int x2points[] = {posX+400, posX+30, posX+400};
			int y2points[] = {posY-900, posY+10, posY+10};
			shadow.fillPolygon(x2points, y2points, npoints);
		}

		public void drawSidewalk(int posX,int posY,int carX,int carY, Graphics g){
			Graphics2D sidewalk = (Graphics2D) g;

			sidewalk.setColor(Color.gray);
			sidewalk.fill(new Rectangle2D.Double(0, 0, 30, jy));
			sidewalk.fill(new Rectangle2D.Double(jx-30, 0, 30, jy));

			sidewalk.setColor(Color.green);
			sidewalk.fill(new Rectangle2D.Double(0, 0, 20, jy));
			sidewalk.fill(new Rectangle2D.Double(jx-20, 0, 20, jy));
		}

		public void drawCar(int posX,int posY,int carX,int carY, Graphics g){
			Graphics2D car = (Graphics2D) g;

			car.setColor(Color.red);
			car.fill(new Rectangle2D.Double(posX, posY, carX, carY));

			car.setColor(Color.black);
			car.fill(new Rectangle2D.Double(posX+2, posY+10, 16, 8));
			car.fill(new Rectangle2D.Double(posX+2, posY+28, 16, 5));

			car.setColor(Color.yellow);
			car.fill(new Rectangle2D.Double(posX+2, posY, 4, 1));
			car.fill(new Rectangle2D.Double(posX+14, posY, 4, 1));


		}
	}

	public void Janela(){
		setTitle("--DrunkDriver--");
		setSize(jx, jy+30);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		gr = new Grafico();
		add(gr);
		time = new Timer( 2, this);
		time.start();


	}

	public static void main(String[] args){
		Paint paint = new Paint();
		paint.Janela();
		paint.control();


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jogar();

	}
	public void jogar(){
		gr.repaint();
		if(fim!=1){
			score = score+1;
		}
		if(posX<30){
			dirX = 2;
		}
		if(posX>jx-30-carX){
			dirX = 1;
		}
		if(dirX == 1 /*&& posX>30*/){
			posX -= 2;
		}
		if(dirX == 2 /*&& posX<jx-30-carX*/){
			posX += 2;
		}

		if(dirY == 1 && posY>0){
			posY -= 1;
		}
		if(dirY == 2 && posY<jy-carY){
			posY += 1;
		}

	}
	public void control(){

		addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 37 && posX>30){
					dirX = 1;
				}
				if(e.getKeyCode() == 39 && posX<jx-30-carX){
					dirX = 2;
				}
				if(e.getKeyCode() == 38 && posY>0){
					dirY = 1;
				}
				if(e.getKeyCode() == 40 && posY<jy-carY){
					dirY = 2;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == 37){
					//dirX = 0;
				}
				if(e.getKeyCode() == 39 ){
					//dirX = 0;
				}
				if(e.getKeyCode() == 38 ){
					dirY = 0;
				}
				if(e.getKeyCode() == 40 ){
					dirY = 0;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {


			}


		});
	}

}
