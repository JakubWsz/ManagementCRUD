package com.kuba.carrentalcompany3.infrastructure.database.jpa.client;

import com.kuba.carrentalcompany3.domain.client.ClientRepository;
import com.kuba.carrentalcompany3.domain.client.model.Client;
import com.kuba.carrentalcompany3.infrastructure.database.jpa.client.entity.ClientDao;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ClientRepositoryJPAImpl implements ClientRepository {
    private final ClientRepositoryJPA clientRepositoryJPA;
    private final ConversionService conversionService;

    public ClientRepositoryJPAImpl(ClientRepositoryJPA clientRepositoryJPA, ConversionService conversionService) {
        this.clientRepositoryJPA = clientRepositoryJPA;
        this.conversionService = conversionService;
    }

    @Override
    public Client save(Client client) {
        ClientDao clientDao = clientRepositoryJPA.save(conversionService.convert(client, ClientDao.class));
        return conversionService.convert(clientDao, Client.class);
    }
}