/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.14).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Measure;
import org.threeten.bp.OffsetDateTime;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-21T19:17:22.578Z[GMT]")
@Api(value = "measures", description = "the measures API")
public interface MeasuresApi {

    @ApiOperation(value = "Añadir nueva medida", nickname = "addMeasure", notes = "Añadir nueva medida", tags={ "measure", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Operación válida"),
        @ApiResponse(code = 405, message = "No se pudo validar"),
        @ApiResponse(code = 500, message = "Error Interno") })
    @RequestMapping(value = "/measures",
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    ResponseEntity<Void> addMeasure(@ApiParam(value = "Medida" ,required=true )  @Valid @RequestBody Measure body);


    @ApiOperation(value = "Evalúa las últimas medidas", nickname = "evaluate", notes = "Evalúa las últimas medidas para saber si hay alguna anomalía", response = Boolean.class, tags={ "measure", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Operación válida", response = Boolean.class),
        @ApiResponse(code = 405, message = "No se pudo validar") })
    @RequestMapping(value = "/measures/evaluate",
        produces = { "application/xml", "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Boolean> evaluate();


    @ApiOperation(value = "Encuentra medidas por magnitud", nickname = "findByMagnitude", notes = "Busca las gráficas que sean de una determinada magnitud", response = Measure.class, responseContainer = "List", tags={ "measure", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Operación válida", response = Measure.class, responseContainer = "List"),
        @ApiResponse(code = 405, message = "No se pudo validar") })
    @RequestMapping(value = "/measures/findByMagnitude",
        produces = { "application/xml", "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Measure>> findByMagnitude(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude);


    @ApiOperation(value = "Encuentra medidas por magnitud y rango de fechas", nickname = "findByMagnitudeAndDateRange", notes = "Busca las gráficas que sean de una determinada magnitud y en una determinada fecha", response = Measure.class, responseContainer = "List", tags={ "measure", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Operación válida", response = Measure.class, responseContainer = "List"),
        @ApiResponse(code = 405, message = "No se pudo validar") })
    @RequestMapping(value = "/measures/findByMagnitudeAndDateRange",
        produces = { "application/xml", "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Measure>> findByMagnitudeAndDateRange(@NotNull @ApiParam(value = "Magnitud de las medidas", required = true) @Valid @RequestParam(value = "magnitude", required = true) Long magnitude,@NotNull @ApiParam(value = "Fecha de inicio del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "startDate", required = true) OffsetDateTime startDate,@NotNull @ApiParam(value = "Fecha de fin del rango temporal de las medidas", required = true) @Valid @RequestParam(value = "endDate", required = true) OffsetDateTime endDate);

}
