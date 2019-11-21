package io.swagger.servicios;

import io.swagger.model.Measure;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import org.threeten.bp.OffsetDateTime;

import io.swagger.model.Magnitude;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("MeasureService")
public class MeasureServiceImpl implements MeasureService {

    private static final Logger log = LoggerFactory.getLogger(MeasureServiceImpl.class);

    private static List<Measure> measuresO2;
    private static List<Measure> measuresBpm;
    static {
        log.info("Init static");
        measuresO2 = new ArrayList<>();
        measuresBpm = new ArrayList<>();

        Measure measureO2_1 = new Measure();
        measureO2_1.setId(1L);
        measureO2_1.setTimestamp(OffsetDateTime.now());
        measureO2_1.setMagnitude(Magnitude.SPO2);
        measureO2_1.setValue(1L);

        Measure measureO2_2 = new Measure();
        measureO2_2.setId(2L);
        measureO2_2.setTimestamp(OffsetDateTime.now());
        measureO2_2.setMagnitude(Magnitude.SPO2);
        measureO2_2.setValue(1L);

        Measure measureO2_3 = new Measure();
        measureO2_3.setId(3L);
        measureO2_3.setTimestamp(OffsetDateTime.now());
        measureO2_3.setMagnitude(Magnitude.SPO2);
        measureO2_3.setValue(1L);

        measuresO2.add(measureO2_1);
        measuresO2.add(measureO2_2);
        measuresO2.add(measureO2_3);


        Measure measureBpm_1 = new Measure();
        measureBpm_1.setId(1L);
        measureBpm_1.setTimestamp(OffsetDateTime.now());
        measureBpm_1.setMagnitude(Magnitude.BPM);
        measureBpm_1.setValue(1L);

        Measure measureBpm_2 = new Measure();
        measureBpm_2.setId(2L);
        measureBpm_2.setTimestamp(OffsetDateTime.now());
        measureBpm_2.setMagnitude(Magnitude.BPM);
        measureBpm_2.setValue(1L);

        Measure measureBpm_3 = new Measure();
        measureBpm_3.setId(3L);
        measureBpm_3.setTimestamp(OffsetDateTime.now());
        measureBpm_3.setMagnitude(Magnitude.BPM);
        measureBpm_3.setValue(1L);

        measuresBpm.add(measureBpm_1);
        measuresBpm.add(measureBpm_2);
        measuresBpm.add(measureBpm_3);


    }

   	@Override
    public boolean add(Measure measure) {
        log.info("add: " + measure.toString());
        return true;
    }

   	@Override
	public boolean check(Measure measure) {
        log.info("check: " + measure.toString());
		return (null != measure.getValue());
	}

   	@Override
    public boolean evaluate() {
        log.info("evaluate");
        int x = (int) Math.random();
        return ((x % 2) == 0);
    }

   	@Override
    public List<Measure> findByMagnitude(Magnitude magnitude) {
        log.info("findByMagnitude: " + magnitude.toString());
        if (Magnitude.BPM.equals(magnitude)) {
            return measuresBpm;
        }
        else {
            return measuresO2;
        }
    }

   	@Override
    public List<Measure> findByMagnitudeAndDateRange(Magnitude magnitude, OffsetDateTime startDate, OffsetDateTime endDate) {

        log.info("findByMagnitudeAndDateRange: " + magnitude.toString() + " " + startDate + " " + endDate);
        if (Magnitude.BPM.equals(magnitude)) {
            return measuresBpm;
        }
        else {
            return measuresO2;
        }
    }

}