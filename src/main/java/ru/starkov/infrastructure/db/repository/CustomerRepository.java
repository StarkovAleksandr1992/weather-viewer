package ru.starkov.infrastructure.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import ru.starkov.application.port.CustomerGateway;
import ru.starkov.domain.entity.Customer;
import ru.starkov.infrastructure.db.dao.CustomerDao;
import ru.starkov.infrastructure.db.mapper.CustomerDbModelMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerRepository implements CustomerGateway {

    private final CustomerDao customerDao;
    private final CustomerDbModelMapper mapper;


    @Override
    public void save(Customer customer) {
        customerDao.save(mapper.toModel(customer));
    }

    @Override
    @Cacheable("customers")
    public Optional<Customer> findById(UUID id) {
        return customerDao.findById(id)
                .map(mapper::toEntity);
    }


    @Override
    public Optional<Customer> findByLogin(String login) {
        return customerDao.findByLogin(login)
                .map(mapper::toEntity);
    }

}
