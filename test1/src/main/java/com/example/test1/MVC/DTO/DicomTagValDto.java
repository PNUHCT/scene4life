package com.example.test1.MVC.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DicomTagValDto {

    private String patientName;
    private String institutionName;
    private String patientId;
    private String studyInstanceUid;
    private String SOPInstanceUid;

    private String SOPClassUid;
    private String viewPosition;
    private String imageLaterality;
    private String imageType;
    private Double estimatedRadiographicMagnificationFactor;
    private String patientSex;
    private String breastImplantPresent;
    private String performedProcedureStepDescription;
    private String seriesDescription;
    private String ProtocolName;
    private String presentationLUTShape;
    private String exposureStatus;

    private String studyDescription;
    private Date studyDate;

}
