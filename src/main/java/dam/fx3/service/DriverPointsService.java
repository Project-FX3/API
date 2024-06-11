package dam.fx3.service;

import dam.fx3.api.repository.DriverPointsRepository;
import dam.fx3.modelo.entities.Driverpoints;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@Transactional
public class DriverPointsService {

    @Autowired
    private DriverPointsRepository driverPointsRepository;

    public DriverPointsService(){

    }

    public void insertDriverPoints(Map<Integer, Integer> driverPositions) {
        // Definir la asignación de puntos
        int[] points = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};

        // Iterar sobre el mapa de posiciones de los drivers
        for (Map.Entry<Integer, Integer> entry : driverPositions.entrySet()) {
            Driverpoints driverPoints = driverPointsRepository.findById(entry.getKey()).get();
            int driverNumber = entry.getKey();
            int position = entry.getValue();

            // Calcular los puntos para la posición
            int pointsForPosition = (position <= 10) ? points[position - 1] : 0;

            if(pointsForPosition != 0){
                // Crear una nueva instancia de DriverPoints
                driverPoints.setPoints(driverPoints.getPoints() + pointsForPosition);

                // Insertar en la base de datos utilizando JpaRepository
                driverPointsRepository.save(driverPoints);
            }
        }
    }
}
