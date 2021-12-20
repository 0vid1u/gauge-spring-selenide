package my.company.datastore;

import org.springframework.stereotype.Service;

@Service
public interface Brainiac {
    <T> void remember(Memory key, T value);

    <T> T recall(Memory key);

    void forget(Memory key);

    void forgetEverything();

    boolean isMemorized(Memory key);
}
