import arc.*;
//import java.awt.image.BufferedImage;

public class ReviewProject{
    public static void main(String[] args){
        Console con = new Console();
        int intPickMap;

        con.println("Which map would you like to pick?");
        intPickMap = con.readInt();

        while(intPickMap != 1 && intPickMap != 2){
            con.println("There are only two maps");
            intPickMap = con.readInt();
        }
        if(intPickMap == 1){
            con.println("map 1");
        }else if(intPickMap == 2){
            con.println("map 2");
        }

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
        BufferedImage imgE2;
		imgE2 = con.loadImage("enemy2.png");
        BufferedImage imgE3;
		imgE3 = con.loadImage("enemy3.png");
        BufferedImage imgH;
		imgH = con.loadImage("hero.png");
        */
    }
}
