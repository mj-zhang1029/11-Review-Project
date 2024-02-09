import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

public class ReviewProject{
    public static void main(String[] args){
        Console con = new Console("RPG Game",800,800);
        //font
		Font fntVera = con.loadFont("VeraMono.ttf",20);
		con.setDrawFont(fntVera);
		con.setTextFont(fntVera);
        
        /*loading pictures
        BufferedImage imgGrass;
		imgGrass = con.loadImage("grass.png");
        BufferedImage imgTrees;
		imgTrees = con.loadImage("tree.png");
        BufferedImage imgWater;
		imgWater = con.loadImage("water.png");
        BufferedImage imgBuild;
		imgBuild = con.loadImage("building.png");
        BufferedImage imgE1;
		imgE1 = con.loadImage("enemy1.png");
        */
        BufferedImage imgE2;
		imgE2 = con.loadImage("enemy2.png");
        BufferedImage imgE3;
		imgE3 = con.loadImage("enemy3.png");
        //BufferedImage imgH;
		//imgH = con.loadImage("hero.png");
        

        //variables for buttons
		int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;
		String strRepeat;
		strRepeat = "yes";

        while(strRepeat.equals("yes")){
			//repeatedly gets x, y, and click of mouse
			intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();
			
			if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 140) && (intMouseY <= 140+80))){
				//play button
				con.setDrawColor(Color.RED);
				con.drawString("PLAY",370,165);
                
				con.repaint();
			}else if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 360) && (intMouseY <= 360+80))){
				//map button
				con.setDrawColor(Color.RED);
				con.drawString("MAP",380,385);
                
                if(intMouseClick == 1){
                    strRepeat = "no";
                    Clear(con);
                    con.setDrawColor(Color.WHITE);
                    con.drawString("Pick Your Difficulty",275,140);

                    //buttons for map selection
                    con.fillRect(100, 300, 250, 300);
                    con.fillRect(450, 300, 250, 300);
                    con.setDrawColor(Color.RED);
                    con.drawString("LVL 1",190,570);
                    con.drawString("LVL 2",540,570);
                    con.drawImage(imgE2,100,300);
                    con.drawImage(imgE3,450,300);

                    if(((intMouseX >= 100) && (intMouseX <=350)) && ((intMouseY >= 300) && (intMouseY <= 140+300))){
                        //lvl 1 button
                        con.setDrawColor(Color.RED);
				        con.drawString("LVL 1",190,570);

                        con.repaint();
                    }else if(((intMouseX >= 450) && (intMouseX <=700)) && ((intMouseY >= 300) && (intMouseY <= 140+300))){
                        //lvl 2 button
                        con.setDrawColor(Color.RED);
				        con.drawString("LVL 2",540,570);

                        con.repaint();
                    }else{
                        //redraws buttons, words, and outline if user is not hovering button
                        con.setDrawColor(Color.WHITE);
                        con.fillRect(100, 300, 250, 300);
                        con.fillRect(450, 300, 250, 300);
                        
                        con.setDrawColor(Color.RED);
                        con.drawString("LVL 1",190,570);
                        con.drawString("LVL 2",540,570);
                        con.drawImage(imgE2,100,300);
                        con.drawImage(imgE3,450,300);
                        
                        con.repaint();
                    }
                }

                con.repaint();
			}else if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 580) && (intMouseY <= 580+80))){
				//quit button
				con.setDrawColor(Color.RED);
				con.drawString("QUIT",370,605);
				
				if(intMouseClick == 1){
					strRepeat = "no";
					//if button is clicked
					con.closeConsole();
				}
				con.repaint();
			}else{
				//redraws buttons, words, and outline if user is not hovering button
				con.setDrawColor(Color.WHITE);
				con.fillRect(250, 140, 300, 80);
				con.fillRect(250, 360, 300, 80);
				con.fillRect(250, 580, 300, 80);
				
				con.setDrawColor(Color.RED);
				con.drawString("PLAY",370,165);
				con.drawString("MAP",380,385);
				con.drawString("QUIT",370,605);
								
				con.repaint();
            }
        }
    }
    //clears screen
	public static void Clear(Console con){
		con.sleep(500);
		con.setDrawColor(Color.BLACK);
		con.fillRect(0,0,1280,720);
		con.clear();
	}
}