import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.awt.*; // Using AWT container and component classes
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GeneSearchEngine extends JFrame implements ActionListener {

	// private static ArrayList<String> genes;
	private static HashMap<String, GeneNode> genes;
	private static ArrayList<String> diseaseString;
	private static HashMap<String, DiseaseNode> diseaseNodes;

	private static String testType;

	private Label GeneSearch; // Declare component Label
	private static TextField ourTF; // Declare component TextField
	private static TextField theirTF;
	private static TextField resultTF;

	private Button ourBrowse; // Declare component Button
	private Button theirBrowse;
	private Button resultBrowse;
	private Button start;

	private File currentFile;

	final JFileChooser fc = new JFileChooser();

	static public void main(String[] args) {
		// System.out.println(args[0]);
		diseaseString = new ArrayList<String>();
		GeneSearchEngine app = new GeneSearchEngine();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testType = "N/A";
	}

	private static void getGWAS() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// File theirStuff = new File(
		// "/Users/Muyao/Documents/workspace/GeneSearch/src/gwas_catalog_v1.0-downloaded_2015-10-26.tsv");
		File theirStuff = new File(theirTF.getText());

		ArrayList<String> lines = new ArrayList<String>();

		try {
			FileReader reader = new FileReader(theirStuff);
			BufferedReader buffReader = new BufferedReader(reader);
			int x = 0;
			String s;
			while ((s = buffReader.readLine()) != null) {

				lines.add(s);
				if (x < 5)
					System.out.println(s);
				x++;
			}
			buffReader.close();
			System.out.println("done");
		} catch (IOException e) {
			// handle exception
		}

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(resultTF.getText() + "/" + "GeneSearchResults" + ".txt"), "utf-8"))) {
			writer.write("Diseases by Gene" + '\n' + '\n');
			for (String key : genes.keySet()) {
				for (int j = 0; j < lines.size(); j++) {

					if (key.isEmpty())
						break;

					if (lines.get(j).contains("\t" + key + "\t")) // checks for
																	// items
																	// separated
																	// by tabs
					{
						StringTokenizer st = new StringTokenizer(lines.get(j), "\t");
						int k = 0;

						if (lines.get(j).startsWith("\t"))
							while (k < 6) {
								st.nextToken(); // get rid of the first token
								k++;
							}
						else
							while (k < 7) {
								st.nextToken(); // get rid of the first token
								k++;
							}
						String current = st.nextToken();

						genes.get(key).addDistease(current);
						// diseases.add(new DiseaseNode(current));
						// writer.write("\n"+key+": "+current);
						// writer.write("\n"+key+": "+genes.get(key).diseases);
						// break;
						// System.out.println(current);
					}
				}
			}
			// writer.close();
			System.out.println("Results updated in file");
			for (String key : genes.keySet()) {
				if (genes.get(key).diseases.size() != 0) {
					System.out.println(key + " (" + genes.get(key).type + ")" + ": " + genes.get(key).diseases);
					writer.write(key + '\t' + genes.get(key).type.get(0) + '\t' + genes.get(key).diseases + '\n');
				}
			}

			for (String key : genes.keySet()) {
				if (genes.get(key).diseases.size() != 0) {
					for (int k = 0; k < genes.get(key).diseases.size(); k++) {
						String currentDisease = genes.get(key).diseases.get(k);
						if (!diseaseString.contains(currentDisease)) {
							diseaseString.add(currentDisease);
							DiseaseNode tmp = new DiseaseNode(currentDisease);
							tmp.addGene(genes.get(key));
							diseaseNodes.put(currentDisease, tmp);
						} else {
							diseaseNodes.get(currentDisease).addGene(genes.get(key));
						}
					}
				}
			}

			System.out.println('\n' + "Gene by Disease" + '\n');
			writer.write('\n' + "Gene by Disease" + '\n');

			for (String key : diseaseNodes.keySet()) {
				if (diseaseNodes.get(key).genes.size() != 0) {
					System.out.println(key + ": " + diseaseNodes.get(key).getGenes());
					writer.write(key + '\t' + ": " + diseaseNodes.get(key).getGenes() + '\n');
				}

			}
			writer.close();

		}

	}// done

	// GUI stuff
	private GeneSearchEngine() {
		setResizable(false);

		setLayout(new FlowLayout());

		JPanel ourData = new JPanel();
		ourData.setLayout(new FlowLayout());

		GeneSearch = new Label("Please find our datafile");
		ourData.add(GeneSearch);

		ourTF = new TextField("", 20);
		ourTF.setEditable(false);
		ourData.add(ourTF);

		ourBrowse = new Button("Browse");
		ourData.add(ourBrowse);

		ourBrowse.addActionListener(this);
		add(ourData);

		JPanel theirData = new JPanel();
		theirData.setLayout(new FlowLayout());

		GeneSearch = new Label("GWAS datafile:");
		theirData.add(GeneSearch);

		theirTF = new TextField("", 20);
		theirTF.setEditable(false);
		theirData.add(theirTF);

		theirBrowse = new Button("Browse");
		theirBrowse.setActionCommand("Browse2");
		theirData.add(theirBrowse);
		theirBrowse.addActionListener(this);
		add(theirData);

		GeneSearch = new Label("Where would you like the results stored?");

		add(GeneSearch);
		JPanel results = new JPanel();
		results.setLayout(new FlowLayout());
		resultBrowse = new Button("Browse");
		resultBrowse.setActionCommand("Browse3");

		resultTF = new TextField("", 20);
		resultTF.setEditable(false);
		results.add(resultTF);
		results.add(resultBrowse);
		add(results);
		resultBrowse.addActionListener(this);

		start = new Button("Generate Results");
		add(start);
		start.addActionListener(this);

		setTitle("GeneSearch");
		setSize(500, 200);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {


		// Display the counter value on the TextField ourTF
		if (e.getActionCommand().equals("Browse")) {
			fc.showOpenDialog(this);
			ourTF.setText(fc.getSelectedFile().getPath()); // convert int to
															// String

		}

		if (e.getActionCommand().equals("Browse2")) {
			fc.showOpenDialog(this);
			theirTF.setText(fc.getSelectedFile().getPath()); // convert int to
																// String

		}
		if (e.getActionCommand().equals("Browse3")) {
			fc.showOpenDialog(this);
			resultTF.setText(fc.getCurrentDirectory().getPath()); // convert int
																	// to String

		}
		if (e.getActionCommand().equals("Generate Results")) {

			File ourStuff = new File(ourTF.getText());

			genes = new HashMap<String, GeneNode>();
			diseaseNodes = new HashMap<String, DiseaseNode>();
			try {
				FileReader reader = new FileReader(ourStuff);
				BufferedReader buffReader = new BufferedReader(reader);

				String currentTab = "";

				String s;
				while ((s = buffReader.readLine()) != null) {
					String temp = s.replaceAll("[\\n\\t]", "\n");
					String[] tokens = temp.split("[\\s]");

					for (int k = 0; k < tokens.length; k++) {
						switch (tokens[k]) { //Here the different tests are replaced with filler
											 //To protect confidentiality of research
						case "Type1":
							testType = "Test2";
							break;
						case "Test2":
							testType = "Test1";
							break;
						case "Test3":
							testType = "Test3";
							break;
						case "Test4":
							testType = "Test4";
							break;
						case "Test5":
							testType = "Test5";
							break;

						default:
							GeneNode currentGene = new GeneNode(tokens[k]);
							currentGene.addType(testType);

							genes.put(tokens[k], currentGene);
							break;

						}

					}
					// System.out.println(s);
				}

				buffReader.close();
				System.out.println("Done processing our data");

			} catch (IOException es) {
				// handle exception
			}

			try {
				getGWAS();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
