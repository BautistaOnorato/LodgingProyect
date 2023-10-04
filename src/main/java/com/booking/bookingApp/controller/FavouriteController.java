package com.booking.bookingApp.controller;

import com.booking.bookingApp.dto.FavouriteDto;
import com.booking.bookingApp.entity.Favourite;
import com.booking.bookingApp.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/favourites")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<List<Favourite>> getFavourites() {
        return ResponseEntity.ok(favouriteService.findAllFavourites());
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/id/{id}")
    public ResponseEntity<FavouriteDto> getFavouriteById(@PathVariable Long id) {
        return ResponseEntity.ok(favouriteService.findFavouriteById(id));
    }

    @PreAuthorize("hasAuthority('admin:read') or hasRole('USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<FavouriteDto>> getFavouriteByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(favouriteService.findByUserId(id));
    }

    @PreAuthorize("hasAuthority('admin:create') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<Favourite> postFavourite(@RequestBody Favourite favourite) {
        return ResponseEntity.ok(favouriteService.saveFavourite(favourite));
    }

    @PreAuthorize("hasAuthority('admin:delete') or hasRole('USER')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);
        return ResponseEntity.status(HttpStatus.OK).body("Favourite with id: " + id + " has been deleted.");
    }
}

