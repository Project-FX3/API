package dam.fx3.schedules;

import dam.fx3.modelo.entities.Gp;
import dam.fx3.service.DriverPointsService;
import dam.fx3.service.GpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

@EnableScheduling
@Component
public class RaceChecker{
    private static final Logger logger = LoggerFactory.getLogger(RaceChecker.class);
    @Autowired
    private GpService gpService;
    @Autowired
    private DriverPointsService driverPointsService;

    public RaceChecker() {

    }

    @Scheduled(fixedRate = 3600000) // Ejecutar cada hora (3600000 milisegundos)
    public void checkRace() {
        try {
            if (actualGPIsFinished()) {
              Map<Integer, Integer> raceResults = gpService.findNextGp().getRaceResults();
              if (!raceResults.isEmpty()){
                  Gp gp = gpService.findById(gpService.findNextGp().getId());
                  if(!gp.getProcessed()){
                      driverPointsService.insertDriverPoints(raceResults);
                      logger.info("Puntos listos: {}", raceResults);
                      gp.setProcessed(true);
                      gpService.save(gp);
                  } else {
                	  logger.info("Gp ya procesado");
                  }
              }
              else{
            	  logger.info("Puntos no disponibles");
              }
            } else{
                logger.error("El próximo GP no ha terminado");
            }
        } catch (Exception e) {
            logger.error("Error en la ejecución de RaceChecker", e);
            throw new RuntimeException(e);
        }
    }

    private boolean actualGPIsFinished() {
        try {
            // Aquí asumo que findNextGp() devuelve el próximo GP a verificar
            return gpService.actualGPIsFinished();
        } catch (Exception e) {
            // Manejar la excepción según sea necesario
            throw new RuntimeException(e);
        }
    }
}
