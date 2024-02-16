import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

//Michelle Zhang
//Gr 11 Review Project
//Version 12

public class ReviewProject{
    //variables that can be used through whole class
    private static boolean blnRepeat = true;
    private static int intHealth = 50;

    public static void main(String[] args){
        Console con = new Console("RPG Game",800,800);
        //variables
        String strChoice;
        strChoice = Menu(con);
        Clear(con);

        if(strChoice.equals("map")){
            Map(con);
            Clear(con);
            //con.println(strMapChoice);
        }else if(strChoice.equals("play")){
            Play(con);
        }
    }

    //game menu
    public static String Menu(Console con){
        //variables for buttons
		int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;

        while(true){
			//repeatedly gets x, y, and click of mouse
			intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();
			
			if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 140) && (intMouseY <= 140+80))){
				//play button
				con.setDrawColor(Color.RED);
				con.drawString("PLAY",370,165);
                
                if(intMouseClick == 1){
                    //Play(con);
                    return("play");
                }
				con.repaint();
			}else if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 360) && (intMouseY <= 360+80))){
				//map button
				con.setDrawColor(Color.RED);
				con.drawString("MAP",380,385);
                
                if(intMouseClick == 1){
                    return("map");
                }
                con.repaint();
			}else if(((intMouseX >= 250) && (intMouseX <= 550)) && ((intMouseY >= 580) && (intMouseY <= 580+80))){
				//quit button
				con.setDrawColor(Color.RED);
				con.drawString("QUIT",370,605);
				
				if(intMouseClick == 1){
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
    
    //play option
    public static void Play(Console con){
        Clear(con);
        //variables
        String strLine;
        String strSplit[];
        String strNextBlock = "";
        int intCountRow = 0;
        int intCountCol = 0;
        int intX = 0;
        int intY = 0;
        int intDmg = 10;
        int intDefense = 10;
        int intPlayerRX;
        int intPlayerRY;
        char chrKey;
        boolean blnHUD = false;

        //array
        String strMap[][];
        strMap = new String[20][20];
        strSplit = new String[20];

        //csv file
        TextInputFile fileMap = new TextInputFile("map2.csv");
        
        //loading map
        for(intCountRow = 0; intCountRow < 20; intCountRow++){
            strLine = fileMap.readLine();
            strSplit = strLine.split(",");

            for(intCountCol = 0; intCountCol < 20; intCountCol++){
                strMap[intCountRow][intCountCol] = strSplit[intCountCol];
            }
        }
        //drawing map
        drawMap(con, intCountCol, intCountRow, strMap);

        //adding hero
        BufferedImage imgHero = con.loadImage("hero.png");
        con.drawImage(imgHero,intX,intY);

        while(blnRepeat){
            chrKey = con.currentChar();
            
            if(chrKey == 'h'){
                blnHUD = HUD(con, intHealth, intDmg, intDefense, blnHUD, intCountCol, intCountRow, imgHero, intX, intY, strMap);
                con.sleep(200);
            }
            if(blnHUD == false){
                drawMap(con, intCountCol, intCountRow, strMap);
                con.drawImage(imgHero,intX,intY);
                if(chrKey == 'w' || chrKey == 'a' || chrKey == 's' || chrKey == 'd'){
                    if(chrKey == 'w'){
                        intPlayerRY = intY-40;

                        if(intPlayerRY >= 0){
                            strNextBlock = strMap[intPlayerRY/40][intX/40];
                            intY = playerMovement(con, strNextBlock, chrKey, intY);
                        }
                    }else if(chrKey == 'a'){
                        intPlayerRX = intX-40;

                        if(intPlayerRX >= 0){
                            strNextBlock = strMap[intY/40][intPlayerRX/40];
                            intX = playerMovement(con, strNextBlock, chrKey, intX);
                        }
                        
                    }else if(chrKey == 's'){
                        intPlayerRY= intY+40;
                        
                        if(intPlayerRY < 800){
                            strNextBlock = strMap[intPlayerRY/40][intX/40];
                            intY = playerMovement(con, strNextBlock, chrKey, intY);
                        }
                    }else if(chrKey == 'd'){
                        intPlayerRX = intX+40;

                        if(intPlayerRX < 800){
                            strNextBlock = strMap[intY/40][intPlayerRX/40];
                            intX = playerMovement(con, strNextBlock, chrKey, intX);
                        }
                    }
                    con.sleep(200);
                }
            }

            if(intHealth <= 0){
                Death(con);
            }
        }

    }
    
    public static int playerMovement(Console con, String strNextBlock, char chrKey, int intPlayerMovement){
        if(strNextBlock.equals("w")){
            //Water code
            intHealth = 0;
            Death(con);
            
        }else if(strNextBlock.equals("b")){
            //health code
            intHealth += 10;
        }else if(strNextBlock.equals("e1")){
            //enemy 1 code
            intHealth -= 10;
        }else if(strNextBlock.equals("e2")){
            //enemny 2 code
            intHealth -= 20;
        }else if(strNextBlock.equals("e3")){
            //enemy 3 code
            intHealth -= 30;
        }

        if(!strNextBlock.equals("t")){
            if(chrKey == 'w' || chrKey == 'a'){
                intPlayerMovement -= 40;
            }else if(chrKey == 's' || chrKey == 'd'){
                intPlayerMovement += 40;
            }
        }

        return intPlayerMovement;
    }

    //dying screen
    public static void Death(Console con){
        blnRepeat = false;

        BufferedImage imgDead = con.loadImage("dead.png");
        con.drawImage(imgDead, 0, 0);

        con.repaint();
    }

    //hud
    public static boolean HUD(Console con, int intH, int intDmg, int intD,boolean blnHUD, int intCountCol, int intCountRow, BufferedImage imgHero, int intX, int intY, String strMap[][]){
        if(blnHUD == false){
            con.setDrawColor(Color.BLACK);
            con.fillRect(0, 0, 800, 100);
            con.println("Health:  "+intH);
            con.println("Damage:  "+intDmg);
            con.println("Defense: "+intD);
            con.repaint();
            return true;
        }else{
            Clear(con);
            drawMap(con, intCountCol, intCountRow, strMap);
            con.drawImage(imgHero,intX,intY);
            return false;
        }
        
    }

    //map draw
    public static void drawMap(Console con, int intCountColumns, int intCountRows, String strMap[][]){
        //variables
        String strSquare;
        //square is null why sob
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
                intBlockX += 40;
            }
            intBlockX = 0;
            intBlockY += 40;
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