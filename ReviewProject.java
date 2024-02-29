import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

//Michelle Zhang
//Gr 11 Review Project

public class ReviewProject{
    //variables that can be used through whole class
    private static boolean blnRepeat = true;
    private static boolean blnSword = false;
    private static boolean blnShield = false;
    private static int intHealth = 50;
    private static int intMapChoice;
    private static int intX = 0;
    private static int intY = 0;
    private static int intDmg = 20;
    private static int intDefense = 20;

    public static void main(String[] args){
        Console con = new Console("RPG Game",800,800);
        //variables
        String strChoice;
        boolean blnPlay = false;

        while(blnPlay == false){
            strChoice = Menu(con);
            if(strChoice.equals("help")){
                Help(con);
            }else{
                blnPlay = true;
            }
        }

        con.setDrawColor(Color.BLACK);
		con.fillRect(0,0,800,800);
		con.clear();
        intMapChoice = Map(con);
        Play(con, intMapChoice);
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
				con.drawString("HELP",370,385);
                
                if(intMouseClick == 1){
                    return("help");
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
				con.drawString("HELP",370,385);
				con.drawString("QUIT",370,605);
								
				con.repaint();
            }
        }
    }
    
    //help option
    public static void Help(Console con){
        boolean blnHelpMenuActive = true;
        int intMouseClick;
        BufferedImage imgHelp = con.loadImage("help.png");

        while(blnHelpMenuActive == true){
            intMouseClick = con.currentMouseButton();
            con.drawImage(imgHelp, 0, 0);
            con.repaint();
            if(intMouseClick == 1){
                con.sleep(100);
                blnHelpMenuActive = false;
            }
        }
    }

    //play option
    public static void Play(Console con, int intMapChoice){
        //variables
        String strLine;
        String strSplit[];
        String strNextBlock = "";
        int intCountRow = 0;
        int intCountCol = 0;
        int intPlayerRX;
        int intPlayerRY;
        char chrKey;
        boolean blnHUD = false;
        //array
        String strMap[][];
        strMap = new String[20][20];
        strSplit = new String[20];
        //csv file
        TextInputFile fileMap = new TextInputFile("map.csv");
        if(intMapChoice == 2){
            fileMap = new TextInputFile("map2.csv");
        }
        //loading map
        for(intCountRow = 0; intCountRow < 20; intCountRow++){
            strLine = fileMap.readLine();
            strSplit = strLine.split(",");

            for(intCountCol = 0; intCountCol < 20; intCountCol++){
                strMap[intCountRow][intCountCol] = strSplit[intCountCol];
            }
        }
        //close csv file
        fileMap.close();
        //drawing map
        drawMap(con, intCountCol, intCountRow, strMap);
        //adding hero
        BufferedImage imgHero = con.loadImage("hero.png");
        con.drawImage(imgHero,intX,intY);

        while(blnRepeat){
            chrKey = con.currentChar();
            
            if(chrKey == 'h'){
                blnHUD = HUD(con, intHealth, intDmg, intDefense, blnHUD, intCountCol, intCountRow, imgHero, strMap, intMapChoice);
                con.sleep(100);
            }
            if(blnHUD == false){
                drawMap(con, intCountCol, intCountRow, strMap);
                con.drawImage(imgHero,intX,intY);
                if(chrKey == 'w' || chrKey == 'a' || chrKey == 's' || chrKey == 'd'){
                    if(chrKey == 'w'){
                        intPlayerRY = intY-40;

                        if(intPlayerRY >= 0){
                            strNextBlock = strMap[intPlayerRY/40][intX/40];
                            intY = playerMovement(con, strNextBlock, chrKey, intY, intMapChoice);
                        }
                    }else if(chrKey == 'a'){
                        intPlayerRX = intX-40;

                        if(intPlayerRX >= 0){
                            strNextBlock = strMap[intY/40][intPlayerRX/40];
                            intX = playerMovement(con, strNextBlock, chrKey, intX, intMapChoice);
                        }
                        
                    }else if(chrKey == 's'){
                        intPlayerRY= intY+40;
                        
                        if(intPlayerRY < 800){
                            strNextBlock = strMap[intPlayerRY/40][intX/40];
                            intY = playerMovement(con, strNextBlock, chrKey, intY, intMapChoice);
                        }
                    }else if(chrKey == 'd'){
                        intPlayerRX = intX+40;

                        if(intPlayerRX < 800){
                            strNextBlock = strMap[intY/40][intPlayerRX/40];
                            intX = playerMovement(con, strNextBlock, chrKey, intX, intMapChoice);
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
    
    //battle animations
    public static void Enemy(Console con, String strE, int intMapChoice){
        blnRepeat = false;

        //variables
        int intEHealth = 50;
        int intRun;
        int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;
        boolean blnFight = false;

        BattleBkg(con, strE, intEHealth);

        //battle will run until player or enemy dies
        while((intHealth > 0) && (intEHealth > 0)){
            intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();

            if(((intMouseX >= 50) && (intMouseX <= 375)) && ((intMouseY >= 650) && (intMouseY <= 750))){
				con.setDrawColor(Color.WHITE);
				con.drawString("FIGHT",180,690);

				if(intMouseClick == 1){
                    //if button is clicked
                    blnFight = true;
                    con.sleep(200);
                    Fight(con, strE, intEHealth, blnFight, intMapChoice);
				}
                con.repaint();
			}else if(((intMouseX >= 435) && (intMouseX <= 760)) && ((intMouseY >= 650) && (intMouseY <= 750))){
                con.setDrawColor(Color.WHITE);
				con.drawString("FLIGHT",565,690);

				if(intMouseClick == 1){
                    //if button is clicked
                    intRun = (int)(Math.random()*100+1);

                    if((intRun%2) == 0){
                        //leave fight successfully
                        blnRepeat = true;
                        Play(con, intMapChoice);
                    }else{
                        intHealth -= 10;
                        BattleBkg(con, strE, intEHealth);
                        con.drawString("Unable to run away", 10, 610);
                    }
				}
				con.repaint();
            }else{
				//redraws buttons, words, and outline if user is not hovering button
				con.setDrawColor(Color.RED);
				con.fillRect(50, 650, 325, 100);
				con.fillRect(435, 650, 325, 100);
				con.setDrawColor(Color.WHITE);
				con.drawString("FIGHT",180,690);
				con.drawString("FLIGHT",565,690);
								
				con.repaint();
            }
        }
    }
    public static void Fight(Console con, String strE, int intEHealth, boolean blnFight, int intMapChoice){
        BattleBkg(con, strE, intEHealth);
        //variables
        int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;
        int intRabbit;
        //fighting part of the battle
        while((blnFight = true) && (intEHealth > 0) && (intHealth > 0)){
            intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();

            if(((intMouseX >= 50) && (intMouseX <= 375)) && ((intMouseY >= 650) && (intMouseY <= 750))){
                con.setDrawColor(Color.WHITE);
				con.drawString("POKE",185,690);

                if(intMouseClick == 1){
                    //animate hand moving in and out to poke
                    Poke(con, strE, intEHealth);
                    intEHealth -= EDmg(strE);
                    intHealth -= HDmg(strE);
                    BattleBkg(con, strE, intEHealth);
                }
                con.repaint();
            }else if(((intMouseX >= 435) && (intMouseX <= 760)) && ((intMouseY >= 650) && (intMouseY <= 750))){
                con.setDrawColor(Color.WHITE);
				con.drawString("MAGIC",570,690);

                if(intMouseClick == 1){
                    //25% chance of turning enemy into rabbit
                    intRabbit = (int)(Math.random()*4+1);
                    if(intRabbit == 1){
                        //leave fight successfully
                        strE = "Rabbit";
                        Magic(con, strE, intEHealth);
                        intEHealth -= EDmg(strE);
                        intHealth -= HDmg(strE);
                        BattleBkg(con, strE, intEHealth);
                    }else{
                        intHealth -= HDmg(strE);
                        BattleBkg(con, strE, intEHealth);
                        con.drawString("Magic failed", 10, 610);
                    }
                }
                con.repaint();
            }else{
                con.setDrawColor(Color.RED);
				con.fillRect(50, 650, 325, 100);
				con.fillRect(435, 650, 325, 100);
				con.setDrawColor(Color.WHITE);
				con.drawString("POKE",185,690);
				con.drawString("MAGIC",570,690);
								
				con.repaint();
            }
        }
        if(intHealth > 0){
            blnRepeat = true;
            Play(con, intMapChoice);
        }
    }
    public static void Poke(Console con, String strE, int intEHealth){
        int intPX = 800;
        int intFX = 580;
        int intFY = 260;
        BufferedImage imgPoke = con.loadImage("poke.png"); //220x220
        BufferedImage imgFinger = con.loadImage("finger.png"); //90x90
        BufferedImage imgHand = con.loadImage("hand.png"); // 170x160

        for(intPX = 800; intPX > 580; intPX -= 10){
            BattleBkg(con, strE, intEHealth);

            con.drawImage(imgPoke, intPX, 260);
            con.sleep(16);

            con.repaint();
        }
        while((intFX != 550) && (intFY != 110)){
            BattleBkg(con, strE, intEHealth);

            intFX -= 2;
            intFY -= 10;
            con.drawImage(imgFinger, intFX, intFY);
            con.drawImage(imgHand, 630, 320);

            con.repaint();
        }
        con.sleep(300);
        while((intFX != 580) && (intFY != 260)){
            BattleBkg(con, strE, intEHealth);

            intFX += 2;
            intFY += 10;
            con.drawImage(imgFinger, intFX, intFY);
            con.drawImage(imgHand, 630, 320);

            con.repaint();
        }
        for(intPX = 580; intPX < 800; intPX += 10){
            BattleBkg(con, strE, intEHealth);

            con.drawImage(imgPoke, intPX, 260);
            con.sleep(16);

            con.repaint();
        }
    }
    public static void Magic(Console con, String strE, int intEHealth){
        int intMY;
        BufferedImage imgMagic = con.loadImage("magic.png");

        for(intMY = -300; intMY < 800; intMY += 10){
            BattleBkg(con, strE, intEHealth);

            con.drawImage(imgMagic, 425, intMY);
            con.sleep(33);

            con.repaint();

        }
    }

    //dmg calculators
    public static int EDmg(String strE){
        //enemy defense lowers player atk by 1 every 10 def points
        if(strE.equals("Ghost")){
            return (intDmg-1);
        }else if(strE.equals("Alien")){
            return (intDmg-2);
        }else if(strE.equals("Bear")){
            return (intDmg-3);
        }else if(strE.equals("Rabbit")){
            return (intDmg);
        }
        return(intDmg);
    }
    public static int HDmg(String strE){
        int intEAtk = intDefense/10;
        int intHurt;
        //enemy dmg determined by sum of atk and every 10 defense points
        if(strE.equals("Ghost")){
            intHurt = 5+intEAtk+1;
        }else if(strE.equals("Alien")){
            intHurt = 10+intEAtk+2;
        }else if(strE.equals("Bear")){
            intHurt = 15+intEAtk+3;
        }else{
            intHurt = intEAtk;
        }
        return(intHurt);
    }

    //battle bkg
    public static void BattleBkg(Console con, String strE, int intEHealth){
        con.setDrawColor(Color.BLACK);
        BufferedImage imgB = con.loadImage("battle.png");
        con.drawImage(imgB,0,0);
        BufferedImage imgH = con.loadImage("bhero.png");
        con.drawImage(imgH,0,250);
        con.drawString("Hero:", 10, 220);
        con.drawString("HP:"+intHealth, 10, 240);

        con.drawString(strE+":", 415, 0);
        con.drawString("HP:"+intEHealth, 415, 20);

        if(strE.equals("Ghost")){
            BufferedImage imgE = con.loadImage("benemy1.png");
            con.drawImage(imgE,450,20);
        }else if(strE.equals("Alien")){
            BufferedImage imgE = con.loadImage("benemy2.png");
            con.drawImage(imgE,450,20);

        }else if(strE.equals("Bear")){
            BufferedImage imgE = con.loadImage("benemy3.png");
            con.drawImage(imgE,450,20);
        }else if(strE.equals("Rabbit")){
            BufferedImage imgE = con.loadImage("rabbit.png");
            con.drawImage(imgE,450,20);
        }
    }

    //block logics (tree, water, etc)
    public static int playerMovement(Console con, String strNextBlock, char chrKey, int intPlayerMovement, int intMapChoice){
        if(strNextBlock.equals("w")){
            //Water code
            intHealth = 0;
            Death(con);
        }else if(strNextBlock.equals("b")){
            //health code
            intHealth += 10;
        }else if(strNextBlock.equals("e1")){
            //enemy 1 code
            Enemy(con, "Ghost", intMapChoice);
        }else if(strNextBlock.equals("e2")){
            //enemny 2 code
            Enemy(con, "Alien", intMapChoice);
        }else if(strNextBlock.equals("e3")){
            //enemy 3 code
            Enemy(con, "Bear", intMapChoice);
        }

        if(!strNextBlock.equals("t")){
            if(chrKey == 'w' || chrKey == 'a'){
                intPlayerMovement -= 40;
            }else if(chrKey == 's' || chrKey == 'd'){
                intPlayerMovement += 40;
            }
        }

        //sword and shield
        if((intX == 320) && (intY == 520)){
            intDmg += 10;
            blnSword = true;
        }
        if((intX == 280) && (intY == 160)){
            intDefense += 10;
            blnShield = true;
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
    public static boolean HUD(Console con, int intH, int intDmg, int intD,boolean blnHUD, int intCountCol, int intCountRow, BufferedImage imgHero, String strMap[][], int intMapChoice){
        if(blnHUD == false){
            con.setDrawColor(Color.BLACK);
            con.fillRect(0, 0, 800, 100);
            con.setDrawColor(Color.WHITE);
            con.drawString("Hero", 0, 0);
            con.drawString("Health:  "+intH, 0, 20);
            con.drawString("Damage:  "+intDmg, 0, 40);
            con.drawString("Defense: "+intD, 0, 60);

            con.drawString("Ghost", 200, 0);
            con.drawString("Health:  50", 200, 20);
            con.drawString("Damage:  5", 200, 40);
            con.drawString("Defense: 10", 200, 60);

            con.drawString("Alien", 400, 0);
            con.drawString("Health:  50", 400, 20);
            con.drawString("Damage:  10", 400, 40);
            con.drawString("Defense: 20", 400, 60);

            if(intMapChoice == 2){
                con.drawString("Bear", 600, 0);
                con.drawString("Health:  50", 600, 20);
                con.drawString("Damage:  15", 600, 40);
                con.drawString("Defense: 30", 600, 60);
            }

            con.repaint();
            return true;
        }else{
            drawMap(con, intCountCol, intCountRow, strMap);
            con.drawImage(imgHero,intX,intY);
            return false;
        }
        
    }

    //map draw
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
                intBlockX += 40;
            }
            intBlockX = 0;
            intBlockY += 40;
        }
        //adding sword and shield
        if(blnSword == false){
            BufferedImage imgSword = con.loadImage("sword.png");
            con.drawImage(imgSword,320,520);
        }
        if(blnShield == false){
            BufferedImage imgShield = con.loadImage("shield.png");
            con.drawImage(imgShield,280,160);
        }

        con.repaint();
    }

    //map selection
    public static int Map(Console con){
        //variables for buttons
		int intMouseX = 0;
		int intMouseY = 0;
		int intMouseClick = 0;
		String strRepeat;
		strRepeat = "yes";

        //map icons
        BufferedImage imgMap1 = con.loadImage("map 1.png");
        BufferedImage imgMap2 = con.loadImage("map 2.png");

        con.setDrawColor(Color.WHITE);
        con.drawString("Pick Your Difficulty",275,140);

        while(strRepeat.equals("yes")){
			//repeatedly gets x, y, and click of mouse
			intMouseX = con.currentMouseX();
			intMouseY = con.currentMouseY();
			intMouseClick = con.currentMouseButton();
			
			if(((intMouseX >= 100) && (intMouseX <= 350)) && ((intMouseY >= 300) && (intMouseY <= 600))){
				//lvl 1 map button
                con.drawImage(imgMap1, 125, 325);
				con.setDrawColor(Color.RED);
                con.drawString("LVL 1",190,570);
                
                if(intMouseClick == 1){
                    strRepeat = "no";
                    return 1;
                }
				con.repaint();
			}else if(((intMouseX >= 450) && (intMouseX <= 700)) && ((intMouseY >= 300) && (intMouseY <= 600))){
				//lvl 2 map button
                con.drawImage(imgMap2, 475, 325);
				con.setDrawColor(Color.RED);
                con.drawString("LVL 2",540,570);
                
                if(intMouseClick == 1){                    
                    strRepeat = "no";
                    return 2;
                }
                con.repaint();
			}else{
                //redraws buttons and words if user is not hovering button
				con.setDrawColor(Color.WHITE);
                con.fillRect(100, 300, 250, 300);
                con.fillRect(450, 300, 250, 300);
                con.setDrawColor(Color.RED);
                con.drawString("LVL 1",190,570);
                con.drawString("LVL 2",540,570);

                con.drawImage(imgMap1, 125, 325);
				con.drawImage(imgMap2, 475, 325);
								
				con.repaint();
            }
        }
        return 1;
    }
}