package ma.fstt.service;

import ma.fstt.entities.Client;

public interface ClientService extends IDAO<Client> {
    Client findByEmail(String email);
}
