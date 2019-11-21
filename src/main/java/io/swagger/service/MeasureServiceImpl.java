package io.swagger.service;

import io.swagger.model.Measure;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.threeten.bp.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;

import org.threeten.bp.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("MeasureService")
public class MeasureServiceImpl implements MeasureService {

    private static final Logger log = LoggerFactory.getLogger(MeasureServiceImpl.class);
    
    private static final Long PULSO = 0L;
    private static final Long OXIGENO = 1L;
    private static final Long GPS = 3L;

    private static List<Measure> measuresO2;
    private static List<Measure> measuresBpm;
    static {
        log.info("Init static");
        
        measuresO2 = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
        	final Long value = 80 + ((long) (Math.random() * 20));
        	final Measure measure = new Measure();
        	measure.setId(Long.valueOf(i));
        	measure.setTimestamp(OffsetDateTime.of(2019, 01, 01, 0, 0, i, 0, ZoneOffset.UTC));
        	measure.setMagnitude(OXIGENO);
        	measure.setValue(value);
        	measuresO2.add(measure);
        }
        
        measuresBpm = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
        	final Long value = 40 + ((long) (Math.random() * 120));
        	final Measure measure = new Measure();
        	measure.setId(Long.valueOf(i));
        	measure.setTimestamp(OffsetDateTime.of(2019, 01, 01, 0, 0, i, 0, ZoneOffset.UTC));
        	measure.setMagnitude(PULSO);
        	measure.setValue(value);
        	measuresBpm.add(measure);
        }
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
        int x = (int) (Math.random() * 100);
        return ((x % 2) == 0);
    }

   	@Override
    public List<Measure> findByMagnitude(Long magnitude) {
        log.info("findByMagnitude: " + magnitude.toString());
        
        if (PULSO.equals(magnitude)) {
            return measuresBpm;
        } 
        else if (OXIGENO.equals(magnitude)) {
            return measuresO2;
        }
        else {
            return new ArrayList<>();
        }
    }

   	@Override
    public List<Measure> findByMagnitudeAndDateRange(Long magnitude, OffsetDateTime startDate, OffsetDateTime endDate) {

        log.info("findByMagnitudeAndDateRange: " + magnitude.toString() + " " + startDate + " " + endDate);
        
        final List<Measure> list = new ArrayList<>();
        if (PULSO.equals(magnitude)) {
        	measuresBpm.forEach(x -> {
        		if (startDate.isBefore(x.getTimestamp()) && endDate.isAfter(x.getTimestamp())) {
        			list.add(x);
        		}
        	});
        } 
        else if (OXIGENO.equals(magnitude)) {
            measuresO2.forEach(x -> {
        		if (startDate.isBefore(x.getTimestamp()) && endDate.isAfter(x.getTimestamp())) {
        			list.add(x);
        		}
        	});
        }
        
        return list;
    }

}