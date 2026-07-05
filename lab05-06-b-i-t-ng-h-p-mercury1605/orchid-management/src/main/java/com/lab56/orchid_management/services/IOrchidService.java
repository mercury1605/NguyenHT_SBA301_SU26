package com.lab56.orchid_management.services;

import com.lab56.orchid_management.entities.Orchid;
import java.util.*;

public interface IOrchidService {
    List<Orchid> getAllOrchids();
    Optional<Orchid> getOrchidById(Integer id);
    Orchid createOrchid(Orchid orchid);
    Orchid updateOrchid(Integer id, Orchid orchid);
    void deleteOrchid(Integer id);
    boolean existsById(Integer id);
}
