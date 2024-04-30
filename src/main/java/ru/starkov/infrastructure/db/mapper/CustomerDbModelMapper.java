package ru.starkov.infrastructure.db.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.starkov.domain.entity.Customer;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.value.Login;
import ru.starkov.domain.value.Password;
import ru.starkov.infrastructure.db.model.CustomerDbModel;

@Component
@RequiredArgsConstructor
public class CustomerDbModelMapper {


    public Customer toEntity(CustomerDbModel model) {
        var id = new CustomerId(model.getId());
        var login = Login.loadFromDb(model.getLogin());
        var password = Password.loadFromDb(model.getPassword());
        return Customer.loadFromDb(id, login, password);
    }


    public CustomerDbModel toModel(Customer entity) {
        var model = new CustomerDbModel();
        model.setId(entity.getId().id());
        model.setLogin(entity.getLogin().getStringValue());
        model.setPassword(entity.getPassword().getEncodedPassword());
        return model;
    }

}
