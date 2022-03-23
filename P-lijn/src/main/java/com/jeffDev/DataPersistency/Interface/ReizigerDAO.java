package com.jeffDev.DataPersistency.Interface;

import com.jeffDev.DataPersistency.Object.Reiziger;

import java.sql.*;
import java.util.List;

public interface ReizigerDAO {
        List<Reiziger> findAll();
        List<Reiziger> findByGBdatum(Date GBdatum);
        Reiziger findById(int i);
        Reiziger save(Reiziger reiziger);
        Reiziger update(Reiziger reiziger);
        boolean delete(Reiziger reiziger);


}