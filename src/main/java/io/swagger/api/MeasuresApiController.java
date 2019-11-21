package io.swagger.api;

import io.swagger.model.Measure;
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

    @org.springframework.beans.factory.annotation.Autowired
    public MeasuresApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addMeasure(@ApiParam(value = "Medida" ,required=true )  @Valid @RequestBody Measure body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Boolean> evaluate() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Boolean>(objectMapper.readValue("true", Boolean.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Boolean>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Measure>> findByMagnitude(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Measure>>(objectMapper.readValue("[ {\n  \"magnitude\" : 6,\n  \"id\" : 0,\n  \"value\" : 1,\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n}, {\n  \"magnitude\" : 6,\n  \"id\" : 0,\n  \"value\" : 1,\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Measure>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Measure>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Measure>> findByMagnitudeAndDateRange(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude,@NotNull @ApiParam(value = "Fecha de inicio del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "startDate", required = true) OffsetDateTime startDate,@NotNull @ApiParam(value = "Fecha de fin del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "endDate", required = true) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Measure>>(objectMapper.readValue("[ {\n  \"magnitude\" : 6,\n  \"id\" : 0,\n  \"value\" : 1,\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n}, {\n  \"magnitude\" : 6,\n  \"id\" : 0,\n  \"value\" : 1,\n  \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Measure>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Measure>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
