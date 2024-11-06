package spring.ai.s01e03;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalibrationDataController {

    private final CalibrationDataService calibrationDataService;

    public CalibrationDataController(CalibrationDataService calibrationDataService) {
        this.calibrationDataService = calibrationDataService;
    }

    @GetMapping("/calibration-data")
    public CalibrationData getCalibrationData() {
        return calibrationDataService.fetchCalibrationData();
    }
}

