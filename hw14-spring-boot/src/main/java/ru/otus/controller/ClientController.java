package ru.otus.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.service.ClientService;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/save")
    public String saveNewClient(Model model) {
        var name = model.getAttribute("clientNameTextBox").toString();
        var phone = model.getAttribute("clientPhoneTextBox").toString();
        var additionalPhone = model.getAttribute("clientAdditionalPhoneTextBox").toString();
        var street = model.getAttribute("clientStreetTextBox").toString();
        clientService.saveClient(new Client(null, name, new Address(null, street), List.of(new Phone(null, phone), new Phone(null, additionalPhone))));
        return "redirect:/";
    }

    @GetMapping
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

}
