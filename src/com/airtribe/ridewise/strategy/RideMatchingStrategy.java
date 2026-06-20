package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

/**
 * @author Priyanka Pitla
 */
public interface RideMatchingStrategy {

    Driver findDriver(Rider rider, List<Driver> drivers);
}
