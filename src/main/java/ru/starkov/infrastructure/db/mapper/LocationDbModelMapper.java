package ru.starkov.infrastructure.db.mapper;

import org.mapstruct.Mapper;
import ru.starkov.domain.entity.Location;
import ru.starkov.domain.identifier.CustomerId;
import ru.starkov.domain.identifier.LocationId;
import ru.starkov.infrastructure.db.model.LocationDbModel;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class LocationDbModelMapper {

    public Location toEntity(LocationDbModel model) {
        var id = model.getId();
        var customerId = model.getCustomerId();
        var name = model.getName();
        var countryCode = model.getCountryCode();
        var latitude = model.getLatitude();
        var longitude = model.getLongitude();
        return Location.loadFromDb(new LocationId(id), new CustomerId(customerId), name, countryCode, latitude, longitude);
    }

    public abstract LocationDbModel toModel(Location location);

    public abstract List<Location> toCollectionEntity(List<LocationDbModel> locations);

    public abstract List<LocationDbModel> toCollectionModel(List<Location> locations);

    public UUID map(LocationId value) {
        return value.id();
    }
    public UUID map(CustomerId value) {
        return value.id();
    }

}
