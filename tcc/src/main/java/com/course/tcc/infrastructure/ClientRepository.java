package com.course.tcc.infrastructure;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.course.tcc.domain.Client;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final EntityManager em;
    private final GenericIdentityMap<Client> identityMap;

    public Client findById(final Long id) {

        if (identityMap.contains(id)) {
            System.out.println("Returning cacheable client");
            return identityMap.get(id);
        }

        System.out.println("Searching client in Database");
        final Client client = em.find(Client.class, id);

        if (Objects.nonNull(client)) {
            identityMap.add(client.getId(), client);
        }

        return client;

    }
}
