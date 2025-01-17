package testHelloWork.service;

import testHelloWork.dao.OfferDAO;
import testHelloWork.model.Offer;

public class OfferService {
    private final OfferDAO offerDAO = new OfferDAO();

    public void storeOffer(String reference, String description, String frenchDate) {
        Offer offer = new Offer(reference, description, frenchDate);
        offerDAO.insertOffer(offer);

        // Afficher le nombre d'offres dans la base
        long count = offerDAO.countOffers();
        System.out.println("Nombre total d'offres dans la base : " + count);
    }
}
