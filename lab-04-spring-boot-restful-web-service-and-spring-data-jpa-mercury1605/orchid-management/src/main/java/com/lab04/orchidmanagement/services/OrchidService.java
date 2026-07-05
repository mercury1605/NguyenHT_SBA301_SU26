package com.lab04.orchidmanagement.services;

import com.lab04.orchidmanagement.exceptions.OrchidNotFoundException;
import com.lab04.orchidmanagement.pojos.Orchid;
import com.lab04.orchidmanagement.repositories.IOrchidRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Service
public class OrchidService implements IOrchidService {

    private final IOrchidRepository orchidRepository;

    public OrchidService(IOrchidRepository orchidRepository) {
        this.orchidRepository = orchidRepository;
    }

    @Override
    public List<Orchid> getAllOrchids() {
        return orchidRepository.findAll();
    }

    @Override
    public Optional<Orchid> getOrchidById(Integer id) {
        return orchidRepository.findById(id);
    }

    @Override
    public Orchid createOrchid(Orchid orchid) {
        return orchidRepository.save(orchid);
    }

    @Override
    public Orchid updateOrchid(Integer id, Orchid orchid) {
        Orchid existingOrchid = orchidRepository.findById(id)
                .orElseThrow(() -> new OrchidNotFoundException(id));

        existingOrchid.setOrchidName(orchid.getOrchidName());
        existingOrchid.setIsNatural(orchid.getIsNatural());
        existingOrchid.setOrchidDescription(orchid.getOrchidDescription());
        existingOrchid.setOrchidCategory(orchid.getOrchidCategory());
        existingOrchid.setIsAttractive(orchid.getIsAttractive());
        existingOrchid.setOrchidURL(orchid.getOrchidURL());

        return orchidRepository.save(existingOrchid);
    }

    @Override
    public void deleteOrchid(Integer id) {
        if (!orchidRepository.existsById(id)) {
            throw new OrchidNotFoundException(id);
        }
        orchidRepository.deleteById(id);
    }

    @Override
    public List<Orchid> searchOrchids(String name, String category, Boolean isNatural) {
        return orchidRepository.searchOrchids(name, category, isNatural);
    }

    @Override
    public Page<Orchid> getOrchidsPaged(int page, int size, String sortBy, String direction) {
        Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Direction.DESC
                : Direction.ASC;

        Sort sort = Sort.by(sortDirection, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        return orchidRepository.findAll(pageable);
    }
}