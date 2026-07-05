package com.lab05.orchid_management.controllers;

import com.lab05.orchid_management.pojos.Orchid;
import com.lab05.orchid_management.services.IOrchidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orchids")
@CrossOrigin(origins = "http://localhost:5173")
public class OrchidController {

    @Autowired
    private IOrchidService orchidService;

    @GetMapping("/")
    public ResponseEntity<List<Orchid>> getAllOrchids() {
        return ResponseEntity.ok(orchidService.getAllOrchids());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orchid> getOrchidById(@PathVariable Integer id) {
        return orchidService.getOrchidById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Orchid> createOrchid(@RequestBody Orchid orchid) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orchidService.createOrchid(orchid));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orchid> updateOrchid(@PathVariable Integer id, @RequestBody Orchid orchid) {
        if (!orchidService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orchidService.updateOrchid(id, orchid));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrchid(@PathVariable Integer id) {
        if (!orchidService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orchidService.deleteOrchid(id);
        return ResponseEntity.noContent().build();
    }
}
