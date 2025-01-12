package drivemate.drivemate.controller;

import com.fasterxml.jackson.databind.JsonNode;
import drivemate.drivemate.domain.Route;
import drivemate.drivemate.dto.routeJSON.RouteFeatureCollectionDTO;
import drivemate.drivemate.dto.RouteSetRequestDTO;
import drivemate.drivemate.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    @PostMapping("/routes")
    public JsonNode getRoute(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        return routeService.getRouteJSON(routeSetRequestDTO);
    }

    @PostMapping("/DTO")
    public RouteFeatureCollectionDTO getDTO(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        JsonNode jsonNode = routeService.getRouteJSON(routeSetRequestDTO);
        return routeService.parseRouteJSON(jsonNode);
    }

    @PostMapping("/entity")
    public Route getEntity(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        JsonNode jsonNode = routeService.getRouteJSON(routeSetRequestDTO);
        RouteFeatureCollectionDTO routeFeatureCollectionDTO = routeService.parseRouteJSON(jsonNode);
        return routeService.createRoute(routeFeatureCollectionDTO);
    }

}
