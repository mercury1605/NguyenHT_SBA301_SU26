package com.lab05.orchid_management.services;

import com.lab05.orchid_management.pojos.Orchid;
import com.lab05.orchid_management.repositories.IOrchidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrchidService implements IOrchidService {

    @Autowired
    private IOrchidRepository orchidRepository;

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
        Orchid existing = orchidRepository.findById(id).orElseThrow();
        existing.setOrchidName(orchid.getOrchidName());
        existing.setIsNatural(orchid.getIsNatural());
        existing.setOrchidDescription(orchid.getOrchidDescription());
        existing.setOrchidCategory(orchid.getOrchidCategory());
        existing.setIsAttractive(orchid.getIsAttractive());
        existing.setOrchidURL(orchid.getOrchidURL());
        return orchidRepository.save(existing);
    }

    @Override
    public void deleteOrchid(Integer id) {
        orchidRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return orchidRepository.existsById(id);
    }
}
