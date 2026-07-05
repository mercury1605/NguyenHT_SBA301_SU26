package com.lab04.orchidmanagement.controllers;

import com.lab04.orchidmanagement.exceptions.OrchidNotFoundException;
import com.lab04.orchidmanagement.pojos.Orchid;
import com.lab04.orchidmanagement.services.IOrchidService;


import com.lab04.orchidmanagement.dto.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orchids")
public class OrchidController {

        private final IOrchidService orchidService;

        public OrchidController(IOrchidService orchidService) {
                this.orchidService = orchidService;
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<Orchid>>> getAllOrchids() {
                List<Orchid> orchids = orchidService.getAllOrchids();
                return ResponseEntity.ok(ApiResponse.success("Retrieved all orchids successfully", orchids));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Orchid>> getOrchidById(@PathVariable Integer id) {
                Optional<Orchid> orchid = orchidService.getOrchidById(id);
                if (orchid.isPresent()) {
                        return ResponseEntity.ok(ApiResponse.success("Retrieved orchid successfully", orchid.get()));
                } else {
                        throw new OrchidNotFoundException(id);
                }
        }

        @PostMapping
        public ResponseEntity<ApiResponse<Orchid>> createOrchid(@RequestBody @Valid Orchid orchid) {
                Orchid createdOrchid = orchidService.createOrchid(orchid);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Orchid created successfully", createdOrchid));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Orchid>> updateOrchid(@PathVariable("id") Integer id,
                        @Valid @RequestBody Orchid orchid) {
                Orchid updatedOrchid = orchidService.updateOrchid(id, orchid);
                return ResponseEntity.ok(ApiResponse.success("Orchid updated successfully", updatedOrchid));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteOrchid(@PathVariable Integer id) {
                orchidService.deleteOrchid(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                .body(ApiResponse.success("Orchid deleted successfully", null));
        }

        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<Orchid>>> searchOrchids(
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String category,
                        @RequestParam(required = false) Boolean isNatural) {
                List<Orchid> orchids = orchidService.searchOrchids(name, category, isNatural);
                return ResponseEntity.ok(ApiResponse.success("Search completed successfully", orchids));
        }

        @GetMapping("/paged")
        public ResponseEntity<ApiResponse<Page<Orchid>>> getOrchidsPaged(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size,
                        @RequestParam(defaultValue = "orchidId") String sortBy,
                        @RequestParam(defaultValue = "asc") String direction) {
                Page<Orchid> orchids = orchidService.getOrchidsPaged(page, size, sortBy, direction);
                return ResponseEntity.ok(ApiResponse.success("Retrieved paged orchids successfully", orchids));
        }
}