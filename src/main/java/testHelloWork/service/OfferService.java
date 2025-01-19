package testHelloWork.service;

import testHelloWork.dao.OfferDAO;
import testHelloWork.model.Offer;

public class OfferService {
    private final OfferDAO offerDAO = new OfferDAO();

    public void storeOffer(String reference, String description, String frenchDate) {
        if (offerDAO.offerExists(reference)) {
            System.out.println("L'offre avec la référence " + reference + " existe déjà.");
            return;
        }

        Offer offer = new Offer(reference, description, frenchDate);
        offerDAO.insertOffer(offer);

        // Afficher le nombre d'offres dans la base
        long count = offerDAO.countOffers();
        System.out.println("Nombre total d'offres dans la base : " + count);
    }
}
