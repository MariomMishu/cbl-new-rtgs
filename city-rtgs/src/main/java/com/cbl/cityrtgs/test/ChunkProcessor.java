package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChunkProcessor implements Runnable {
    private final List<MxMessageLog> chunk;
    private final InwardTransactionHandlerService inwardHandle;

    public ChunkProcessor(List<MxMessageLog> chunk, InwardTransactionHandlerService inwardHandle) {
        this.chunk = chunk;
        this.inwardHandle = inwardHandle;
    }

    @Override
    public void run() {
        // Process each data in the chunk
        for (MxMessageLog data : chunk) {
            if (!chunk.isEmpty()) {
                    String block4 = data.getMxMessage();
                    inwardHandle.handleInwardBlock4Message(block4);
           }
            System.out.println("Processing data: " + data);
        }
    }
}
