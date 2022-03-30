package com.jeffDev.DataPersistency.Interface;

import com.jeffDev.DataPersistency.Domein.Product;

public interface ProductDAO {
    Product save(Product product);
    Product update(Product product);
    Product delete(Product product);

}
