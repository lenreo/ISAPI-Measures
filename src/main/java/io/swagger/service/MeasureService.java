package io.swagger.servicios;

import io.swagger.model.Measure;
import io.swagger.model.Magnitude;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public interface MeasureService {

    public boolean add(Measure measure);
   	public boolean check(Measure measure);
    public boolean evaluate();
    public List<Measure> findByMagnitude(Magnitude magnitude);
    public List<Measure> findByMagnitudeAndDateRange(Magnitude magnitude, OffsetDateTime startDate, OffsetDateTime endDate);
}