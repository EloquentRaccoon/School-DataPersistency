package com.jeffDev.DataPersistency.Interface;

import com.jeffDev.DataPersistency.Object.OvChipkaart;
import com.jeffDev.DataPersistency.Object.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OvChipkaartDAO {
    public List<OvChipkaart> findByReiziger(Reiziger reiziger);
    public OvChipkaart findByKaartnummer(String kaartnummer);
    public OvChipkaart save(OvChipkaart ovChipkaart);
    public OvChipkaart update(OvChipkaart ovChipkaart);
    public OvChipkaart delete(OvChipkaart ovChipkaart);
    public List<OvChipkaart> findAll();

}
