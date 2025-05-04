package com.bd.blooddonerfinder.service;

import com.bd.blooddonerfinder.model.Location;
import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.model.enums.BloodGroup;
import com.bd.blooddonerfinder.model.enums.Role;
import com.bd.blooddonerfinder.repository.UserRepository;
import com.bd.blooddonerfinder.util.GeoUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DonorSearchServiceImpl implements DonorSearchService{
    private final UserRepository userRepository;

    public DonorSearchServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> findNearByDonors(double lat, double lon, BloodGroup bloodGroup, double radiusKm) {
        List<Users> allDonors = userRepository.findByRoleAndBloodGroup(Role.DONOR, bloodGroup);
        List<Users> nearByDonors = new ArrayList<>();
        for(Users user : allDonors){
            Location location = user.getLocation();
            if (location == null || location.getLatitude() == null || location.getLongitude() == null){
                continue;
            }
            double distance = GeoUtils.haversine(lat,location.getLatitude(), lon, location.getLongitude());
            if(distance <= radiusKm){
                nearByDonors.add(user);
            }
        }
        return nearByDonors;
    }
}
