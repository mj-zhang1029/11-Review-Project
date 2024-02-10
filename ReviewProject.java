import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
//import java.awt.Font;

public class ReviewProject{
    public static void main(String[] args){
        Console con = new Console("RPG Game",800,800);
        //font
		//Font fntVera = con.loadFont("VeraMono.ttf",20);
		//con.setDrawFont(fntVera);
		//con.setTextFont(fntVera);
        //variables
        String strMapChoice = "map.csv";
        String strChoice;
        strChoice = Menu(con);
        Clear(con);
        if(strChoice.equals("map")){
            strMapChoice = Map(con);
            Clear(con);
            //Menu(con);
            //con.println(strMapChoice);
        }else if((strChoice.equals("play")) && (strMapChoice.equals("map.csv"))){
            Play1(con);
        }else if((strChoice.equals("play")) && (strMapChoice.equals("map2.csv"))){
            //Play2(con);
        }
    }
    //game menu
    public static String Menu(Console con){
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
                
                if(intMouseClick == 1){
                    strRepeat = "no";

                    return("play");
                }

				con.repaint();
			}else if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 360) && (intMouseY <= 360+80))){
				//map button
				con.setDrawColor(Color.RED);
				con.drawString("MAP",380,385);
                
                if(intMouseClick == 1){
                    strRepeat = "no";
                    
                    return("map");
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

        return ("hi");
    }
    
    //play option
    public static void Play1(Console con){
        Clear(con);
        //variables
        String strLine;
        int intCountRow = 0;
        int intCountCol = 0;
        String strSplit[];
        //array
        String strMap[][];
        strMap = new String[20][20];
        //csv file
        TextInputFile txtMap = new TextInputFile("map.csv");
        
        for(intCountRow = 0; intCountRow < 20; intCountRow++){
            strLine = txtMap.readLine();
            strSplit = strLine.split(",");

            for(intCountCol = 0; intCountCol < 20; intCountCol++){
                strMap[intCountRow][intCountCol] = strSplit[intCountCol];
                drawMap(con,intCountCol,intCountRow,strMap);
            }
        }
        con.println("woohoo game");
    }

    //map loading
    public static void drawMap(Console con, int intCountColumns, int intCountRows, String strMap[][]){
        //variables
        String strSquare;
        int intBlockX = 0;
        int intBlockY = 0;

        //loading pics
        BufferedImage imgWater = con.loadImage("water.png");
        BufferedImage imgGrass = con.loadImage("grass.png");
        BufferedImage imgTree = con.loadImage("tree.png");
        BufferedImage imgBuilding = con.loadImage("building.png");
        BufferedImage imgEnemy1 = con.loadImage("enemy1.png");
        BufferedImage imgEnemy2 = con.loadImage("enemy2.png");
        BufferedImage imgEnemy3 = con.loadImage("enemy3.png");

        //drawing board
        for(intCountRows = 0; intCountRows < 20; intCountRows++){
            for(intCountColumns = 0; intCountColumns < 20; intCountColumns++){
                strSquare = strMap[intCountRows][intCountColumns];
                if(strSquare.equals("g")){
                    con.drawImage(imgGrass, intBlockX, intBlockY);
                }else if(strSquare.equals("w")){
                    con.drawImage(imgWater, intBlockX, intBlockY);
                }else if(strSquare.equals("t")){
                    con.drawImage(imgTree, intBlockX, intBlockY);
                }else if(strSquare.equals("b")){
                    con.drawImage(imgBuilding, intBlockX, intBlockY);
                }else if(strSquare.equals("e1")){
                    con.drawImage(imgEnemy1, intBlockX, intBlockY);
                }else if(strSquare.equals("e2")){
                    con.drawImage(imgEnemy2, intBlockX, intBlockY);
                }else if(strSquare.equals("e3")){
                    con.drawImage(imgEnemy3, intBlockX, intBlockY);
                }
                intBlockX += 30;
            }
            intBlockX = 0;
            intBlockY += 30;
        }
        con.repaint();
    }

    //map selection
    public static String Map(Console con){
        //variables for buttons
		int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;
		String strRepeat;
		strRepeat = "yes";
        
        Clear(con);
        con.setDrawColor(Color.WHITE);
        con.drawString("Pick Your Difficulty",275,140);

        while(strRepeat.equals("yes")){
			//repeatedly gets x, y, and click of mouse
			intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();
			
			if(((intMouseX >= 100) && (intMouseX <= 350)) && ((intMouseY >= 300) && (intMouseY <= 600))){
				//play button
				con.setDrawColor(Color.RED);
                con.drawString("LVL 1",190,570);
                
                if(intMouseClick == 1){
                    strRepeat = "no";

                    return("map.csv");
                }

				con.repaint();
			}else if(((intMouseX >= 450) && (intMouseX <= 700)) && ((intMouseY >= 300) && (intMouseY <= 600))){
				//map button
				con.setDrawColor(Color.RED);
                con.drawString("LVL 2",540,570);
                
                if(intMouseClick == 1){                    
                    strRepeat = "no";

                    return("map2.csv");
                }

                con.repaint();
			}else{
				//redraws buttons, words, and outline if user is not hovering button
				con.setDrawColor(Color.WHITE);
                con.fillRect(100, 300, 250, 300);
                con.fillRect(450, 300, 250, 300);
                con.setDrawColor(Color.RED);
                con.drawString("LVL 1",190,570);
                con.drawString("LVL 2",540,570);
								
				con.repaint();
            }
        }
        return("map.csv");
    }

    //clears screen
	public static void Clear(Console con){
		con.sleep(500);
		con.setDrawColor(Color.BLACK);
		con.fillRect(0,0,1280,720);
		con.clear();
	}

}