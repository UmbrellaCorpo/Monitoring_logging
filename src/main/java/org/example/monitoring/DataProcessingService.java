package org.example.monitoring;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DataProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessingService.class);

    // Pool de hilos
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    // Método para procesar datos en paralelo
    public List<Future<String>> processData(List<String> data) {
        List<Callable<String>> tasks = new ArrayList<>();

        for (String datum : data) {
            Callable<String> task = () -> {
                logger.info("Procesando: {}", datum);  // Log cuando inicia el procesamiento
                Thread.sleep(1000);  // Simulación de tarea intensiva
                logger.info("Finalizado: {}", datum);  // Log cuando finaliza
                return "Procesado: " + datum;
            };
            tasks.add(task);
        }

        try {
            return executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            logger.error("Error durante el procesamiento de datos", e);
        }

        return null;
    }
}
