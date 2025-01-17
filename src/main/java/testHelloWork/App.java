package testHelloWork;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import testHelloWork.model.Offer;
import testHelloWork.util.XSLTTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class App {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; // URI de connexion
    private static MongoClient mongoClient;

    public static void main(String[] args) {
        // Initialiser le client MongoDB
        initMongoClient();

        // Transformer le fichier XML avec XSLT et insérer les offres dans la base de données
        processTransformedOffers();

        // Nettoyer les ressources
        closeMongoClient();

        System.out.println("Application terminée avec succès.");
    }

    /**
     * Initialise le client MongoDB.
     */
    private static void initMongoClient() {
        if (mongoClient == null) {
            ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            mongoClient = MongoClients.create(settings);
            System.out.println("Connexion à MongoDB établie.");
        }
    }

    /**
     * Retourne le client MongoDB.
     *
     * @return MongoClient
     */
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            initMongoClient();
        }
        return mongoClient;
    }

    /**
     * Transforme un fichier XML avec XSLT, analyse le résultat et insère les offres transformées dans MongoDB.
     */
    private static void processTransformedOffers() {
        try {
            // Transformer le fichier XML avec XSLT
            XSLTTransformer transformer = new XSLTTransformer();
            String outputXmlPath = transformer.transformXML();

            // Lire le fichier XML transformé et extraire les offres
            List<Offer> offers = parseTransformedXML(outputXmlPath);

            //Insérer les offres dans MongoDB
            MongoDatabase database = getMongoClient().getDatabase("testDatabase");
            MongoCollection<Document> collection = database.getCollection("offers");

            int insertedCount = 0;
            for (Offer offer : offers) {
                // Vérifier si l'offre existe déjà
                Document existingOffer = collection.find(new Document("reference", offer.getReference())).first();

                if (existingOffer == null) {
                    // Insérer l'offre si elle n'existe pas
                    Document doc = new Document("reference", offer.getReference())
                            .append("description", offer.getDescription())
                            .append("frenchDate", offer.getFrenchDate());
                    collection.insertOne(doc);
                    System.out.println("Offre insérée : " + doc.toJson());
                    insertedCount++;
                } else {
                    System.out.println("Offre déjà présente avec la description : " + offer.getDescription());
                }
            }

            System.out.println("Nombre total d'offres insérées : " + insertedCount);
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des offres transformées : " + e.getMessage());
        }
    }

    /**
     * Lit un fichier XML transformé et extrait une liste d'objets Offer.
     *
     * @param transformedXml Chemin du fichier XML transformé.
     * @return Liste des objets Offer.
     */
    private static List<Offer> parseTransformedXML(String transformedXml) {
        List<Offer> offers = new ArrayList<>();
        try {
            File file = new File(transformedXml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            org.w3c.dom.NodeList offerNodes = document.getElementsByTagName("TransformedOffer");

            for (int i = 0; i < offerNodes.getLength(); i++) {
                org.w3c.dom.Element offerElement = (org.w3c.dom.Element) offerNodes.item(i);

                String reference = offerElement.getElementsByTagName("Reference").item(0).getTextContent();
                String description = offerElement.getElementsByTagName("UpperCase").item(0).getTextContent();
                String frenchDate = offerElement.getElementsByTagName("UTCDate").item(0).getTextContent();

                offers.add(new Offer(reference, description, frenchDate));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier XML transformé : " + e.getMessage());
        }
        return offers;
    }

    /**
     * Ferme la connexion au client MongoDB.
     */
    private static void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Connexion à MongoDB fermée.");
        }
    }
}
