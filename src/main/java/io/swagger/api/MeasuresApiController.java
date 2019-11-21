package io.swagger.api;

import io.swagger.model.Measure;
import io.swagger.service.MeasureService;
import org.threeten.bp.OffsetDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-21T19:17:22.578Z[GMT]")
@Controller
public class MeasuresApiController implements MeasuresApi {

    private static final Logger log = LoggerFactory.getLogger(MeasuresApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private MeasureService measureService;

    @org.springframework.beans.factory.annotation.Autowired
    public MeasuresApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addMeasure(@ApiParam(value = "Medida" ,required=true )  @Valid @RequestBody Measure body) {
        log.info("addMeasure");

        boolean result = (measureService.check(body) && measureService.add(body));
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Boolean> evaluate() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Boolean result = measureService.evaluate();
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Measure>> findByMagnitude(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Measure> listMeasure = measureService.findByMagnitude(magnitude);
            return new ResponseEntity<List<Measure>>(listMeasure, HttpStatus.OK);
        }

        return new ResponseEntity<List<Measure>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Measure>> findByMagnitudeAndDateRange(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude,@NotNull @ApiParam(value = "Fecha de inicio del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "startDate", required = true) OffsetDateTime startDate,@NotNull @ApiParam(value = "Fecha de fin del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "endDate", required = true) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Measure> listMeasure = measureService.findByMagnitudeAndDateRange(magnitude, startDate, endDate);
            return new ResponseEntity<List<Measure>>(listMeasure, HttpStatus.OK);
        }

        return new ResponseEntity<List<Measure>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
