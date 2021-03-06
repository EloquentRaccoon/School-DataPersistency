package com.jeffDev.DataPersistency.Interface;

import com.jeffDev.DataPersistency.Domein.Adres;
import com.jeffDev.DataPersistency.Domein.Reiziger;
import java.util.List;

public interface AdresDAO {
    List<Adres> findAll();
    Adres findByReiziger(Reiziger reiziger);
    Adres findById(int i);
    Adres save(Adres reiziger);
    Adres update(Adres reiziger);
    Adres delete(Adres reiziger);
}
