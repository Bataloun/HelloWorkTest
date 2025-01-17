package testHelloWork.util;

import testHelloWork.App;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XSLTTransformer {

	/**
	 * Transforme un fichier XML avec un fichier XSLT et génère un fichier XML résultant.
	 *
	 * @return Chemin absolu du fichier XML transformé.
	 */
	public String transformXML() {
		try {
			// Chemins des fichiers
			String inputXml = App.class.getClassLoader().getResource("offers.xml").getPath();
			String xsltFile = App.class.getClassLoader().getResource("offers-transform.xsl").getPath();
			String outputDirectory = "src/main/resources/xmlSortie";
			String outputFileName = "transformed-offers.xml";
			String outputXml = outputDirectory + File.separator + outputFileName;

			File outputDir = new File(outputDirectory);
			if (!outputDir.exists()) {
				boolean created = outputDir.mkdirs();
				if (created) {
					System.out.println("Répertoire de sortie créé : " + outputDir.getAbsolutePath());
				} else {
					throw new IOException("Impossible de créer le répertoire de sortie : " + outputDir.getAbsolutePath());
				}
			}

			if (!new File(inputXml).exists()) {
				throw new FileNotFoundException("Le fichier XML d'entrée est introuvable : " + inputXml);
			}
			if (!new File(xsltFile).exists()) {
				throw new FileNotFoundException("Le fichier XSLT est introuvable : " + xsltFile);
			}

			// Logs des chemins
			System.out.println("Fichier XML d'entrée : " + inputXml);
			System.out.println("Fichier XSLT : " + xsltFile);
			System.out.println("Fichier de sortie : " + outputXml);

			// Préparer les sources
			Source xmlSource = new StreamSource(new File(inputXml));
			Source xsltSource = new StreamSource(new File(xsltFile));
			Result result = new StreamResult(new File(outputXml));


			// Créer le transformateur et appliquer la transformation
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xsltSource);
			transformer.transform(xmlSource, result);

			System.out.println("Transformation réussie : fichier généré à " + new File(outputXml).getAbsolutePath());
			return new File(outputXml).getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
