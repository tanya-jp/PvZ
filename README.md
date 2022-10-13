# PvZ
This project is an implementation of Plants vs. Zombies written in Java using swing and graphics 2D. Plants vs. Zombies is a tower defense video game, the player takes the role of a homeowner amid a zombie apocalypse. As a horde of zombies approaches along several parallel lanes, the player must defend the home by putting down plants, which fire projectiles at the zombies or otherwise detrimentally affect them. The player collects a currency called sun to buy plants. If a zombie happens to make it to the house on any lane, the player must play through the level again.

## Game Canvas
This game contains 2 maps, one of them is light mode and another one is dark mode which represent day and night.
- Day
<p align="center">
  <img src="https://user-images.githubusercontent.com/72709191/195575678-2e4daddc-ee5b-4ff0-bca4-3b49bb94cc89.JPG" width=70% height=70%>
</p>

- Night
<p align="center">
  <img src="https://user-images.githubusercontent.com/72709191/195575860-99aa623c-fe6e-44a8-8736-a4be162f7f93.JPG" width=70% height=70%>
</p>

## Elements
### Zombies
In this game, there are three different types of zombies:
1. Zombie: <img src="https://user-images.githubusercontent.com/72709191/195578570-7abff256-7c97-4ec8-a79b-9f9a220159d5.png" width=5% height=5%>
2. Cone Head Zombie: <img src="https://user-images.githubusercontent.com/72709191/195578793-5beffb06-264d-4703-b50b-4ae95b8d23d5.png" width=5% height=5%>
3. Bucket Head Zombie: <img src="https://user-images.githubusercontent.com/72709191/195582268-da2431f4-ac0f-499e-a4c4-83baaf8c1980.png" width=10% height=10%>

### Plants
Each plant has its specific card, which shows the needed score for having it.
1. SunFlower  
<img src="https://user-images.githubusercontent.com/72709191/195621384-f2f0e06d-d6e6-4cce-b42e-b1cddaf6fa96.png" width=5% height=5%>
2. PeaShooter   
<img src="https://user-images.githubusercontent.com/72709191/195621372-7782bb2a-3e65-4dd9-a9f0-543a7f26de72.png" width=5% height=5%>
3. SnowPea  
<img src="https://user-images.githubusercontent.com/72709191/195621368-47d941c3-aa98-454b-94dd-7c17a78caed8.png" width=5% height=5%>
4. GiantWall-nut  
<img src="https://user-images.githubusercontent.com/72709191/195622219-53450adb-af16-4352-b67e-7b663d8b9bf5.png" width=5% height=5%>
5. CherryBomb   
<img src="https://user-images.githubusercontent.com/72709191/195622238-4ad29fed-6964-4ea7-a3f9-824433fe8d33.png" width=5% height=5%>
6. Squash   
<img src="https://user-images.githubusercontent.com/72709191/195621378-97b0e73c-c8fe-424b-a612-61479857ac5a.jpg" width=5% height=5%>
7. SunShroom  
<img src="https://user-images.githubusercontent.com/72709191/195621398-379f489b-7601-44d3-b4cd-392df0055371.png" width=5% height=5%>


## Menu
The game includes two types of menus: 
1. Main Menu which gives users the ability to start a new game, load a saved game, view rankings, modify settings or quit. 
<p align="center">
  <img src="https://user-images.githubusercontent.com/72709191/195623493-de3a7e56-9ad9-4247-beed-3eb35d4f31ce.JPG" width=70% height=70%>
</p>
2. Pause Menu is available throughout the game and helps users pause or save their game or exit to the main menu.
<p align="center">
  <img src="https://user-images.githubusercontent.com/72709191/195623553-293e307f-0e90-4175-9a48-666a1f4a142e.JPG" width=35% height=35%>
</p>

## Network
Multiple users (clients) can start a game and play simultaneously by connecting to a **server**. Their game information including scores and mode will be saved to be displayed on a **scoreboard** later on.
<p align="center">
  <img src="https://user-images.githubusercontent.com/72709191/195625152-b7371b28-2e88-4a75-bf69-cc997eb545f4.JPG" width=50% height=50%>
</p>

Packages: [src](https://github.com/tanya-jp/PvZ/tree/master/src)

Design Tool Kit and Musics: [PVS Design Kit](https://github.com/tanya-jp/PvZ/tree/master/PVS%20Design%20Kit)
