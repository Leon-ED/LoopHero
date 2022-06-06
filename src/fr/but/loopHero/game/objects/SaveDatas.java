package fr.but.loopHero.game.objects;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import fr.but.loopHero.game.LoopHeroGameData;
import fr.but.loopHero.game.TimeData;
import fr.but.loopHero.player.Player;

public class SaveDatas {
	
	private final Player hero;
	private final LoopHeroGameData datas;
	private final String save_type;
	
	public SaveDatas(Player hero, LoopHeroGameData datas) {
		this.hero = Objects.requireNonNull(hero);
		this.datas = Objects.requireNonNull(datas);
		this.save_type = "recap";
		saveFiles();
	}

	
	public SaveDatas(Player hero, LoopHeroGameData datas,TimeData timeData,Board Plateau) {
		this.hero = Objects.requireNonNull(hero);
		this.datas = Objects.requireNonNull(datas);
		this.save_type = "save";
		saveFiles();
	}
	
	
	private final void saveFiles() {
		if(save_type.equalsIgnoreCase("recap"))
			save_recap(LoopHeroGameData.GAME_RECAP_PATH);
		else
			save_game(LoopHeroGameData.GAME_SAVE_PATH);

	}


	private void save_game(Path gameSavePath) {
		Player p1 = new Player();
		Player p2 = null;
		try (OutputStream back = Files.newOutputStream(gameSavePath);
				ObjectOutputStream out = new ObjectOutputStream(back)){
				out.writeObject(p1); // sauvegarde
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
		try( InputStream back = Files.newInputStream(gameSavePath);
				ObjectInputStream in = new ObjectInputStream(back)){
				p2 = (Player) in.readObject(); // recuperation
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		System.out.println(p1);
		System.out.println(p2);

		
	}


	private void save_recap(Path gameRecapPath) {
		SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		try(BufferedWriter writer = Files.newBufferedWriter(gameRecapPath)) {
			writer.write("=============== Recapitulatif de votre partie Loop Hero :\n");
			writer.write("Jouée le : "+ formatter.format(date)+"\n");
			writer.write("Vous avez joué pendant : "+(System.currentTimeMillis()-LoopHeroGameData.startedTick)/1000 + " secondes \n");
			writer.write("Vous avez tué : "+LoopHeroGameData.KILLED_ENEMIES+ " ennemis en ayant fait : "+LoopHeroGameData.MADE_DAMAGES+" de degats en"+ LoopHeroGameData.ATTACKS+" attaques \n");
			writer.write("En revanche vous avez perdu : "+LoopHeroGameData.TAKEN_DAMAGES+" HP en combat \n");
			writer.write("Vous avez utilisé : "+LoopHeroGameData.USED_CARDS+" cartes et "+LoopHeroGameData.USED_ITEMS +" équipements \n");
			writer.write("\n Merci d'avoir joué à LoopHero ! rejoignez notre discord : https://discord.gg/vByZn36sjd pour en savoir plus sur les mises à jours à venir ! comme le multijoueur :)");
			
			
			
			
			
			
			
			
				
				}catch (IOException e) {
					System.out.println("Erreur :" + e);
				}
		
		
	}
}
