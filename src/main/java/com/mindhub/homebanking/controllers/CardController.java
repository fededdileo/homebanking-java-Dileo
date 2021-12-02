package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    //@RequestMapping(path= "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType type, @RequestParam CardColor color, Authentication authentication) {

        Client clientGeneral = clientRepository.findByEmail(authentication.getName());
        if (clientGeneral.getCards().stream().filter(e -> e.getType().toString().equals(type.toString())).count() > 2) {
            return new ResponseEntity<>("403 You cannot have more than three cards", HttpStatus.FORBIDDEN);
        }
        Card card = new Card(clientGeneral.getFirstName() + " " + clientGeneral.getLastName(), type, color, getCardNumber(1000, 9999), getRandomNumber(100, 999), LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        clientGeneral.addCards(card);
        cardRepository.save(card);

        return new ResponseEntity<>("201 Card created", HttpStatus.CREATED);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getCardNumber(int min, int max) {
        return getRandomNumber(min, max) + "-" + getRandomNumber(min, max) + "-" + getRandomNumber(min, max) + "-" + getRandomNumber(min, max);
    }
    @DeleteMapping("/cards/delete/{id}")
    public ResponseEntity<?> deleteCard(Authentication authentication, @PathVariable long id){

        Client client = clientRepository.findByEmail(authentication.getName());
        if(cardRepository.findById(id) == null){
            return new ResponseEntity<>("Tarjeta inexistente", HttpStatus.FORBIDDEN);
        }
        if (!client.getCards().contains(cardRepository.findById(id))){
            return new ResponseEntity<>("Tarjeta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }
        cardRepository.delete(cardRepository.findById(id));
        return new ResponseEntity<>("Tarjeta eliminada correctamente", HttpStatus.ACCEPTED);

    }
}
