package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.model.Rider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Priyanka Pitla
 */
public class RiderRepositoryImpl implements RiderRepository {

    private static final Map<String, Rider> riderMap = new ConcurrentHashMap<>();

    @Override
    public Rider registerRider(Rider rider) {
        riderMap.put(rider.getId(), rider);
        System.out.println("Rider registered successfully");
        System.out.println(rider);
        return rider;
    }

    @Override
    public Rider getRiderById(String id) {
        return riderMap.get(id);
    }
}
