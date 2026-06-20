package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.repository.RiderRepository;

/**
 * @author Priyanka Pitla
 */
public class RiderService {

    RiderRepository repository;

    public RiderService(RiderRepository repository) {
        this.repository = repository;
    }

    public Rider registerRider(Rider rider) {
       return repository.registerRider(rider);

    }

    public Rider getRiderById(String id) {
        return repository.getRiderById(id);
    }
}
