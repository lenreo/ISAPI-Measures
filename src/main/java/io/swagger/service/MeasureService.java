package io.swagger.service;

import io.swagger.model.Measure;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public interface MeasureService {

    public boolean add(Measure measure);
   	public boolean check(Measure measure);
    public boolean evaluate();
    public List<Measure> findByMagnitude(Long magnitude);
    public List<Measure> findByMagnitudeAndDateRange(Long magnitude, OffsetDateTime startDate, OffsetDateTime endDate);
}