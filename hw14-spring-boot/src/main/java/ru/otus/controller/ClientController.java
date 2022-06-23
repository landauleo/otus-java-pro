package ru.otus.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.model.Phone;
import ru.otus.service.ClientService;

//light guide: https://betacode.net/11547/spring-boot-and-freemarker
@Controller
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/save")
    public String saveNewClient(String name, String phone, String additionalPhone, String street) {
        log.info("Is about to save new client with para,s: {}, {}, {}, {}", name, phone, additionalPhone, street);
        var phones = List.of(new Phone(null, phone), new Phone(null, additionalPhone));
        clientService.saveClient(name, street, List.of(phone, additionalPhone));
        return "redirect:/";
    }

    @GetMapping
    public String getAllClients(Model model) {
        var clients = clientService.findAll();
        log.info("Got all clients: {}", clients);
        model.addAttribute("clients", clients);
        return "clients";
    }

}
