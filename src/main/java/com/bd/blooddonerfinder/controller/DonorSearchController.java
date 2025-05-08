package com.bd.blooddonerfinder.controller;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.model.enums.BloodGroup;
import com.bd.blooddonerfinder.payload.response.RestApiResponse;
import com.bd.blooddonerfinder.service.DonorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorSearchController {
    private final DonorSearchService donorSearchService;

    public DonorSearchController(DonorSearchService donorSearchService) {
        this.donorSearchService = donorSearchService;
    }
    @GetMapping("/getDonors")
    public ResponseEntity<RestApiResponse<List<Users>>> getNearByDonors(@RequestParam("lat") double lat,
                                                                      @RequestParam("lon") double lon,
                                                                      @RequestParam("bloodGroup") BloodGroup bloodGroup,
                                                                      @RequestParam(value = "radius",defaultValue = "10") double radius){
        List<Users> nearByUserList = donorSearchService.findNearByDonors(lat, lon, bloodGroup, radius);
        return ResponseEntity.ok()
                .body(RestApiResponse.success(nearByUserList,"Fetched nearby user list successfully",null));
    }
}
