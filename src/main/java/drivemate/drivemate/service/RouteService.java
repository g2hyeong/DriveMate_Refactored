package drivemate.drivemate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import drivemate.drivemate.domain.*;
import drivemate.drivemate.dto.pathResponse.PathRespondDTO;
import drivemate.drivemate.dto.route.RouteFeatureCollectionDTO;
import drivemate.drivemate.dto.RouteSetRequestDTO;
import drivemate.drivemate.dto.routeGeo.RouteGeoDTO;
import drivemate.drivemate.dto.routeInfo.InfoFeatureCollectionDTO;
import drivemate.drivemate.repository.CoordinateRepository;
import drivemate.drivemate.repository.RouteRepository;
import drivemate.drivemate.repository.SemiRouteInfoRepository;
import drivemate.drivemate.repository.SemiRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final RouteRepository routeRepository;
    private final SemiRouteRepository semiRouteRepository;
    private final SemiRouteInfoRepository semiRouteInfoRepository;
    private final CoordinateRepository coordinateRepository;
    private final String routeUrl = "https://apis.openapi.sk.com/tmap/routes";
    private final String trafficUrl = "https://apis.openapi.sk.com/tmap/traffic";
    private final String geoURl = "https://apis.openapi.sk.com/tmap/geo/reversegeocoding";

    // application.properties 나 yml 에 정의된 값을 Spring Bean으로 만들고, 해당 값을 변수에 주입한다.
    @Value("${tmap.api.key}")
    private String appKey;

    /**
     * @param routeSetRequestDTO 시작 좌표. 끝 좌표
     * @return String
     * TMAP API 서버에서 경로 데이터 JSON으로 받아옴
     */
    public JsonNode getRouteJSON(RouteSetRequestDTO routeSetRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("appKey", appKey);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 요청 본문 작성
        String requestBody = String.format(
                "{ \"startY\": %s, \"startX\": %s, \"endY\": %s, \"endX\": %s, \"tollgateFareOption\": 2, \"mainRoadInfo\": \"N\" }",
                routeSetRequestDTO.getStart().getLat(),
                routeSetRequestDTO.getStart().getLng(),
                routeSetRequestDTO.getEnd().getLat(),
                routeSetRequestDTO.getEnd().getLng()
        );

        // HttpEntity 생성. 본문과 헤더의 캡슐화.
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        /*
            restTemplate: Spring의 RestTemplate 객체로, RESTful 서비스와의 HTTP 통신을 쉽게 처리하기 위한 도구입니다. 주로 HTTP 요청을 보내고 응답을 처리하는 역할을 합니다.
            exchange: RestTemplate의 메서드 중 하나로, HTTP 요청을 보내고 응답을 받을 수 있는 기능을 제공합니다. exchange는 매우 유연하며, 다양한 HTTP 메서드(GET, POST, PUT, DELETE 등)를 지원합니다.
            apiUrl: 요청을 보낼 대상의 URL입니다. 이 URL은 API 엔드포인트를 나타냅니다. 예를 들어, https://example.com/api/resource와 같은 형식으로, HTTP 요청이 이 주소로 전송됩니다.
            HttpMethod.POST: 요청을 보낼 HTTP 메서드를 지정합니다. 여기서는 POST 메서드를 사용하고 있습니다. 즉, 클라이언트가 서버로 데이터를 전송할 때 사용됩니다. 다른 예로는 HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE 등이 있습니다.
            requestEntity: HTTP 요청 본문과 헤더를 포함하는 HttpEntity 객체입니다. 이 객체는 요청에 포함될 데이터를 캡슐화하고, 헤더 정보도 포함할 수 있습니다. 위에서 예시로 HttpEntity<String>으로 구성된 요청 데이터를 보내는 부분에 대해 설명드렸습니다.
            String.class: 이 값은 응답 본문의 타입을 지정합니다. 여기서는 응답이 문자열 형식일 것이라는 것을 의미합니다. 즉, API에서 반환하는 응답이 String 형식으로 처리된다는 것을 나타냅니다. 만약 API 응답이 JSON이면, String 대신 MyResponseObject.class와 같이 특정 객체로 변환할 수도 있습니다.
        */

        // POST 요청 보내기
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(routeUrl, HttpMethod.POST, requestEntity, JsonNode.class);

        // JsonNode로 응답 반환
        return responseEntity.getBody();
    }

    /**
     *
      * @param routeJSON
     *  @return RouteFeatureCollectionDTO
     *  경로 JSON 데이터를 파싱해서 DTO에 저장한다.
     */
    public RouteFeatureCollectionDTO parseRouteJSON(JsonNode routeJSON){
        try {
            return objectMapper.treeToValue(routeJSON, RouteFeatureCollectionDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON to DTO",e);
        }
    }

    public Route createRoute(RouteFeatureCollectionDTO dto){
        return Route.fromDTO(dto);
    }


    /**
     *
     * @param lat
     * @param lng
     * @return JsonNode 형식으로 해당 좌표의 Info를 받아옴
     */
    public JsonNode getInfoJSON(Double lat, Double lng){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("appKey", appKey);

        // URL에 쿼리 파라미터 포함
        String urlWithParams = String.format(
                "%s?version=1&centerLat=%f&centerLon=%f&trafficType=POINT&radius=1&zoomLevel=10",
                trafficUrl, lat, lng
        );

        // HttpEntity는 헤더만 포함 (GET 요청은 보통 바디 없이 보냄)
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // GET 요청으로 변경된 URL과 함께 전송
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(urlWithParams, HttpMethod.GET, requestEntity, JsonNode.class);

        return responseEntity.getBody();
    }

    /**
     *
     * @param infoNode
     * @return Info JSON을 DTO로 변환한다
     */

    public InfoFeatureCollectionDTO parseInfoJSON(JsonNode infoNode){
        try {
            return objectMapper.treeToValue(infoNode, InfoFeatureCollectionDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON to DTO", e);
        }
    }

    /**
     *
     * @param dto
     * @return Info DTO를 SemiRouteInfo 엔티티로 변환한다.
     */

    public SemiRouteInfo createSemiRouteInfo(InfoFeatureCollectionDTO dto){
        return SemiRouteInfo.fromDTO(dto);
    }

    /**
     * @param route
     * @return route 객체에서 파생되는 (SemiRoutePoint 마다 생성되는) SemiRouteInfo 객체를 연관관계 매핑한다.
     */

    @Async
    public CompletableFuture<Route> setSemiRouteInfo(Route route){
        for (SemiRoute lineString : route.getSemiRouteList()){
            if (lineString.getClass() == SemiRouteLineString.class){
                Double lat = lineString.getCoordinateList().get(0).getLatitude();
                Double lng = lineString.getCoordinateList().get(0).getLongitude();
                JsonNode jsonNode = getInfoJSON(lat, lng);
                InfoFeatureCollectionDTO dto = parseInfoJSON(jsonNode);
                SemiRouteInfo info = createSemiRouteInfo(dto);
                info.setSemiRoute(lineString);
            }
        }
        return CompletableFuture.completedFuture(route);
    }

    /**
     *
     * @param lat
     * @param lng
     * @return JSON 형식으로 좌표에 대응하는 주소값을 받아옴
     */

    public JsonNode getGeoJSON(Double lat, Double lng){
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("appKey", appKey);

        // URL에 쿼리 파라미터 포함
        String urlWithParams = String.format(
                "%s?version=1&&lat=%f&lon=%f&coordType=WGS84GEO&addressType=A02&newAddressExtend=Y",
                geoURl, lat, lng
        );

        // HttpEntity는 헤더만 포함 (GET 요청은 보통 바디 없이 보냄)
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // GET 요청으로 변경된 URL과 함께 전송
        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(urlWithParams, HttpMethod.GET, requestEntity, JsonNode.class);

        return responseEntity.getBody();
    }

    /**
     *
     * @param geoNode
     * @return JsonNode를 DTO로 변환한다.
     */

    public RouteGeoDTO parseGeoJSON(JsonNode geoNode){
        try {
            return objectMapper.treeToValue(geoNode, RouteGeoDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param route
     * @return 각 SemiRoutePoint에 대응하는 주소값을 외부 API를 통해 받아와 저장한다
     */

    @Async
    public CompletableFuture<Route> setSemiRouteSection(Route route){
        for (SemiRoute semiRoute : route.getSemiRouteList()) {
            if (semiRoute.getClass() == SemiRouteLineString.class) {
                Double lat = semiRoute.getCoordinateList().get(0).getLatitude();
                Double lng = semiRoute.getCoordinateList().get(0).getLongitude();
                JsonNode jsonNode = getGeoJSON(lat, lng);
                RouteGeoDTO dto = parseGeoJSON(jsonNode);
                semiRoute.setSectionName(dto.getAddressInfo().getCity_do() + " " + dto.getAddressInfo().getGu_gun());
            }
        }
        return CompletableFuture.completedFuture(route);
    }

    /**
     *
     * @param route
     * @return route batch insert 테스트를 위한 로직
     */

    @Transactional
    public Route saveRouteWithBatch(Route route){
        routeRepository.save(route);
        List<SemiRoute> semiRouteList = route.getSemiRouteList();
        semiRouteRepository.saveAll(semiRouteList);
        for (SemiRoute semiRoute : semiRouteList){
            if(semiRoute.getClass() == SemiRouteLineString.class)
                semiRouteInfoRepository.save(semiRoute.getSemiRouteInfo());
            coordinateRepository.saveAll(semiRoute.getCoordinateList());
        }
        return route;
    }

    public PathRespondDTO createPathRespondDTO(Route route){
        return PathRespondDTO.fromRoute(route);
    }
}
