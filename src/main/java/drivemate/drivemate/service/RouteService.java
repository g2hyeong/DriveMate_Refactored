package drivemate.drivemate.service;

import drivemate.drivemate.dto.RouteSetRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RouteService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String routeUrl = "https://apis.openapi.sk.com/tmap/routes";

    // application.properties 나 yml 에 정의된 값을 Spring Bean으로 만들고, 해당 값을 변수에 주입한다.
    @Value("${tmap.api.key}")
    private String appKey;

    public String getRoute(RouteSetRequestDTO routeSetRequestDTO) {
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
        ResponseEntity<String> responseEntity = restTemplate.exchange(routeUrl, HttpMethod.POST, requestEntity, String.class);

        // 응답 반환
        return responseEntity.getBody();
    }
}
