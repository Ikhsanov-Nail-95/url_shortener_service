package faang.school.urlshortenerservice.service.generator;

import faang.school.urlshortenerservice.model.Hash;
import faang.school.urlshortenerservice.repository.HashRepository;
import faang.school.urlshortenerservice.service.encoder.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class HashGenerator {

    private final HashRepository hashRepository;

    private final Base62Encoder base62Encoder;

    @Value("${hash.generation-batch}")
    private long uniqueNumsAmount;

    @Transactional
    public void generateBatch() {
        List<Long> uniqueNums = hashRepository.getUniqueNumbers(uniqueNumsAmount);
        List<Hash> hashes = uniqueNums.stream()
                .map(base62Encoder::encode)
                .map(Hash::new)
                .toList();
        hashRepository.save(hashes);
    }

    @Transactional
    public List<String> getHashes(long amount) {
        List<Hash> hashes = hashRepository.getHashBatch(amount);
        if (hashes.size() < amount) {
            generateBatch();
            hashes.addAll(hashRepository.getHashBatch(amount - hashes.size()));
        }
        return hashes.stream()
                .map(Hash::getHash)
                .toList();
    }

    @Transactional
    @Async("hashGenerationExecutor")
    public CompletableFuture<List<String>> getHashesAsync(long amount) {
        return CompletableFuture.completedFuture(getHashes(amount));
    }
}