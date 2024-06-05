package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InwardDataProcessor {
    private final InwardTransactionHandlerService inwardHandle;

    public void processDataInChunks(List<MxMessageLog> dataList, int chunkSize) {
      //  int chunkSize = 5; // Example chunk size

        // Create an ExecutorService to manage threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Process data in chunks
        for (int i = 0; i < dataList.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, dataList.size());
            List<MxMessageLog> chunk = dataList.subList(i, endIndex);

            // Create a new ChunkProcessor and submit it to the executor
            executorService.submit(new ChunkProcessor(chunk,inwardHandle));
        }

        // Shutdown the ExecutorService after all tasks are submitted
        executorService.shutdown();

    }
}
