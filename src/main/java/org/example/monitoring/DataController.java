package org.example.monitoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class DataController {

    @Autowired
    private DataProcessingService dataProcessingService;

    // Endpoint para procesar datos
    @GetMapping("/api/process-data")
    public List<String> processData(@RequestParam List<String> data) {
        List<String> results = new ArrayList<>();

        // Procesar datos en paralelo
        List<Future<String>> futures = dataProcessingService.processData(data);

        // Obtener resultados
        for (Future<String> future : futures) {
            try {
                results.add(future.get());  // Espera hasta que el hilo termine
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return results;  // Retorna los resultados al cliente
    }
}
