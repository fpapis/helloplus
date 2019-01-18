package helloPlus;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class App {

	private static final Logger Log = Logger.getLogger(App.class.getName());
	
	//attributs
	private String filename;
	//constructeurs
	public App(String filename) {
		setFilename(filename);
	}
	//setters & getters
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public static void main(String[] args) {
		//paramètres
		String filename = null;
		//options
		Options options = new Options();
		//type d'option
		Option input = new Option("i", "input", true, "nom du fichier .csv contenant la liste des données");
		//option necessaire ou non ?
		input.setRequired(true);
		
		//ajout des option dans options
		options.addOption(input);
		
		//parser la ligne de commande
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(options, args);
			if (line.hasOption("i")) {
				filename = line.getOptionValue("i");
			}
		}
		catch( ParseException exp){
			Log.severe("Erreur dans la ligne de commande");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("App", options);
			System.exit(1);
		}
		
		App app = new App(filename);
		try {
			CSVParser p = app.buildCSVParser();
			for(CSVRecord r : p) {
				String nom = r.get(0);
				String prenom = r.get(1);
				System.out.println("Hello " +nom+" "+prenom+" !");
			}
		} 
		catch (IOException e) {
			Log.severe("Erreur de lecture dans le ficier CSV");
		}

	}
	
	public CSVParser buildCSVParser() throws IOException{
		CSVParser res = null;
		Reader in;
		in = new FileReader(filename);
		CSVFormat csvf = CSVFormat.DEFAULT.withCommentMarker('#').withDelimiter(';');
		res = new CSVParser(in, csvf);
		return res;
	}

}
