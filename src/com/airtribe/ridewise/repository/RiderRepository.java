package com.airtribe.ridewise.repository;

import com.airtribe.ridewise.model.Rider;

/**
 * @author Priyanka Pitla
 */
public interface RiderRepository {
    Rider registerRider(Rider rider);
    Rider getRiderById(String id);
}
