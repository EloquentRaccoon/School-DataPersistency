package com.jeffDev.DataPersistency.Interface;

import com.jeffDev.DataPersistency.Object.Adres;
import com.jeffDev.DataPersistency.Object.Reiziger;
import java.util.List;

public interface AdresDAO {
    List<Adres> findAll();
    Adres findByReiziger(Reiziger reiziger);
    Adres findById(int i);
    Adres save(Adres reiziger);
    Adres update(Adres reiziger);
    boolean delete(Adres reiziger);
}
