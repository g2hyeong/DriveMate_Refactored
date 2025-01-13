package drivemate.drivemate.dto.routeInfo;

import lombok.Data;

import java.util.List;

@Data
public class InfoFeatureCollectionDTO {
    private String type;
    private List<InfoFeatureDTO> features;
}
