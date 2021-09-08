package com.cryptoalert.cryptoalert.controllers.responseEntities;

import com.cryptoalert.cryptoalert.mappers.Alert;
import com.cryptoalert.cryptoalert.mappers.ObjectEntity;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResponseMessageListWithRelatablesEntity<T> {
    public Collection<T> results;
    public Map<String, ObjectEntity> relatables;

    public ResponseMessageListWithRelatablesEntity(Collection<T> results) {
        this.results = results;
        this.relatables = new HashMap<String, ObjectEntity>();
    }

    public void addRelatable(ObjectEntity newRelatable) {
        if (newRelatable != null) {
            relatables.put(newRelatable.id, newRelatable);
        }
    }

    public void addAllRelatables(Object newRelatables) {
        if (newRelatables instanceof Collection) {
            for (ObjectEntity obj : (Collection<ObjectEntity>) newRelatables) {
                String id = "";
                if (obj instanceof Alert) {
                    id = ((Alert) obj).id;
                } else if (obj instanceof Price) {
                    id = ((Price) obj).id;
                } else if (obj instanceof Stock) {
                    id = ((Stock) obj).id;
                } else {
                    continue;
                }

                relatables.put(id, obj);
            }
        }
    }

}
