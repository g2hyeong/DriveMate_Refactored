package drivemate.drivemate.controller;

import com.fasterxml.jackson.databind.JsonNode;
import drivemate.drivemate.domain.Route;
import drivemate.drivemate.domain.SemiRouteInfo;
import drivemate.drivemate.dto.pathResponse.PathRespondDTO;
import drivemate.drivemate.dto.route.RouteFeatureCollectionDTO;
import drivemate.drivemate.dto.RouteSetRequestDTO;
import drivemate.drivemate.dto.routeInfo.InfoFeatureCollectionDTO;
import drivemate.drivemate.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

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

    /*
    @PostMapping("/entity")
    public Route getEntity(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        JsonNode jsonNode = routeService.getRouteJSON(routeSetRequestDTO);
        RouteFeatureCollectionDTO routeFeatureCollectionDTO = routeService.parseRouteJSON(jsonNode);
        Route route = routeService.createRoute(routeFeatureCollectionDTO);
        route = routeService.setSemiRouteSection(route);
        return routeService.setSemiRouteInfo(route);
    }
    */

    @PostMapping("/entity")
    public Route getEntity(@RequestBody RouteSetRequestDTO routeSetRequestDTO) {
        // 경로 JSON 데이터 가져오기
        JsonNode jsonNode = routeService.getRouteJSON(routeSetRequestDTO);
        RouteFeatureCollectionDTO routeFeatureCollectionDTO = routeService.parseRouteJSON(jsonNode);
        Route route = routeService.createRoute(routeFeatureCollectionDTO);

        // 비동기 메서드 호출 후 결과를 기다림
        CompletableFuture<Route> semiRouteSectionFuture = routeService.setSemiRouteSection(route);
        CompletableFuture<Route> semiRouteInfoFuture = routeService.setSemiRouteInfo(route);

        // 두 작업 완료를 기다림 (두 개의 스레드가 종료될 때까지 메인 스레드는 블록된다.)
        CompletableFuture.allOf(semiRouteSectionFuture, semiRouteInfoFuture).join();

        // 최종적으로 처리된 Route 객체 반환
        try {
            Route updatedRoute = semiRouteSectionFuture.get(); // 또는 semiRouteInfoFuture.get() 사용 가능
            //routeService.saveRouteWithBatch(updatedRoute);
            return updatedRoute;
        } catch (Exception e) {
            throw new RuntimeException("Failed to process route data asynchronously", e);
        }
    }

    @GetMapping("/info")
    public SemiRouteInfo getInfoEntity(@RequestParam Double lat, @RequestParam Double lng){
        JsonNode jsonNode = routeService.getInfoJSON(lat, lng);
        InfoFeatureCollectionDTO infoFeatureCollectionDTO = routeService.parseInfoJSON(jsonNode);
        return routeService.createSemiRouteInfo(infoFeatureCollectionDTO);
    }

    @PostMapping("/response")
    public PathRespondDTO respond(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        JsonNode jsonNode = routeService.getRouteJSON(routeSetRequestDTO);
        RouteFeatureCollectionDTO routeFeatureCollectionDTO = routeService.parseRouteJSON(jsonNode);
        Route route = routeService.createRoute(routeFeatureCollectionDTO);

        // 비동기 메서드 호출 후 결과를 기다림
        CompletableFuture<Route> semiRouteSectionFuture = routeService.setSemiRouteSection(route);
        CompletableFuture<Route> semiRouteInfoFuture = routeService.setSemiRouteInfo(route);

        // 두 작업 완료를 기다림 (두 개의 스레드가 종료될 때까지 메인 스레드는 블록된다.)
        CompletableFuture.allOf(semiRouteSectionFuture, semiRouteInfoFuture).join();

        // 최종적으로 처리된 Route 객체 반환
        try {
            Route updatedRoute = semiRouteSectionFuture.get(); // 또는 semiRouteInfoFuture.get() 사용 가능
            return routeService.createPathRespondDTO(updatedRoute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process route data asynchronously", e);
        }
    }

}
