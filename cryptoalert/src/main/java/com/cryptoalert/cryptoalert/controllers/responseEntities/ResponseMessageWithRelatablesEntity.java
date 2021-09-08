package com.cryptoalert.cryptoalert.controllers.responseEntities;

import com.cryptoalert.cryptoalert.mappers.Alert;
import com.cryptoalert.cryptoalert.mappers.ObjectEntity;
import com.cryptoalert.cryptoalert.mappers.Price;
import com.cryptoalert.cryptoalert.mappers.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResponseMessageWithRelatablesEntity<T> {
    public T results;
    public Map<String, ObjectEntity> relatables;

    public ResponseMessageWithRelatablesEntity(T results) {
        this.results = results;
        this.relatables = new HashMap<String, ObjectEntity>();
    }

    public void addRelatable(ObjectEntity newRelatable) {
        if (newRelatable != null) {
            String id = "";
            if (newRelatable instanceof Alert) {
                id = ((Alert) newRelatable).id;
            } else if (newRelatable instanceof Price) {
                id = ((Price) newRelatable).id;
            } else if (newRelatable instanceof Stock) {
                id = ((Stock) newRelatable).id;
            } else {
                return;
            }

            relatables.put(id, newRelatable);
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
