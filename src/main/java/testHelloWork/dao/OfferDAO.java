package testHelloWork.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import testHelloWork.App;
import testHelloWork.model.Offer;

public class OfferDAO {
    private final MongoCollection<Document> collection;

    public OfferDAO() {
        MongoDatabase database = App.getMongoClient().getDatabase("testDatabase"); // Nom base
        this.collection = database.getCollection("offers"); // Nom collection
    }

    public void insertOffer(Offer offer) {
        Document doc = new Document("reference", offer.getReference())
                .append("description", offer.getDescription())
                .append("frenchDate", offer.getFrenchDate());
        collection.insertOne(doc);
        System.out.println("Offer insérée avec succès : " + offer.getDescription());
    }

    public long countOffers() {
        return collection.countDocuments();
    }

    public boolean offerExists(String reference) {
        Document existingOffer = collection.find(new Document("reference", reference)).first();
        return existingOffer != null;
    }

}
