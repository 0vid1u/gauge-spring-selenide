package my.company.datastore.internal;

import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import lombok.extern.slf4j.Slf4j;
import my.company.datastore.Brainiac;
import my.company.datastore.Memory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SuppressWarnings("unchecked")
@Component
@Slf4j
public class DataStoreHolder implements Brainiac {

    private final DataStore dataStore;

    public DataStoreHolder() {
        dataStore = getDataStore();
    }

    @Override
    public <T> void remember(Memory key, T value) {
        dataStore.put(key, value);
    }

    @Override
    public <T> T recall(Memory key) {
        return (T) dataStore.get(key);
    }

    @Override
    public void forget(Memory key) {
        dataStore.remove(key);
    }

    @Override
    public void forgetEverything() {
        if (dataStore != null) {
            Arrays.stream(Memory.values()).forEach(dataStore::remove);
        }
    }

    @Override
    public boolean isMemorized(Memory key) {
        return dataStore.get(key) != null;
    }

    private DataStore getDataStore() {
        return DataStoreFactory.getScenarioDataStore();
    }
}
