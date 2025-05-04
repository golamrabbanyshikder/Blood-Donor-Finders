package com.bd.blooddonerfinder.service;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.model.enums.BloodGroup;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DonorSearchService {
    List<Users> findNearByDonors (double lat, double lon, BloodGroup bloodGroup, double radiusKm);
}
