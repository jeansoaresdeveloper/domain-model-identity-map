package com.course.tcc.service;

import org.springframework.stereotype.Service;

import com.course.tcc.domain.Client;
import com.course.tcc.infrastructure.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client findById(final Long id) {
        return repository.findById(id);
    }

}
