package drivemate.drivemate.controller;

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
    public String getRoute(@RequestBody RouteSetRequestDTO routeSetRequestDTO){
        return routeService.getRoute(routeSetRequestDTO);
    }

}
