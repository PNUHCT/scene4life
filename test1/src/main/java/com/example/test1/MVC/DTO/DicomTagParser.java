package com.example.test1.MVC.DTO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
//import example.global.exception.DicomTagParseException;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;

public class DicomTagParser {

    /**
     * Parse dicom tag
     *
     * @param dicomFile dicom file
     * @return dicom tag value
     */
    public static DicomTagValDto parseDicomTag(File dicomFile) throws IOException {

        DicomTagValDto dicomTagValDto = new DicomTagValDto(
                getDicomTagValue(dicomFile, Tag.PatientName, String.class),
                getDicomTagValue(dicomFile, Tag.InstitutionName, String.class),
                getDicomTagValue(dicomFile, Tag.PatientID, String.class),
                getDicomTagValue(dicomFile, Tag.StudyInstanceUID, String.class),
                getDicomTagValue(dicomFile, Tag.SOPInstanceUID, String.class),
                getDicomTagValue(dicomFile, Tag.SOPClassUID, String.class),
                getDicomTagValue(dicomFile, Tag.ViewPosition, String.class),
                getDicomTagValue(dicomFile, Tag.ImageLaterality, String.class),
                getDicomTagValue(dicomFile, Tag.ImageType, String.class),
                getDicomTagValue(dicomFile, Tag.EstimatedRadiographicMagnificationFactor, Double.class),
                getDicomTagValue(dicomFile, Tag.PatientSex, String.class),
                getDicomTagValue(dicomFile, Tag.BreastImplantPresent, String.class),
                getDicomTagValue(dicomFile, Tag.PerformedProcedureStepDescription, String.class),
                getDicomTagValue(dicomFile, Tag.SeriesDescription, String.class),
                getDicomTagValue(dicomFile, Tag.ProtocolName, String.class),
                getDicomTagValue(dicomFile, Tag.PresentationLUTShape, String.class),
                getDicomTagValue(dicomFile, Tag.ExposureStatus, String.class),
                getDicomTagValue(dicomFile, Tag.StudyDescription, String.class),
                getDicomTagValue(dicomFile, Tag.StudyDate, Date.class)
        );

        return dicomTagValDto;
    }

    /**
     * Get dicom tag value
     *
     * @param file dicom file
     * @param tag  dicom tag
     * @param type tag value type
     * @return tag value
     */
    private static <T> T getDicomTagValue(File file, int tag, Class<T> type) throws IOException {

        try (DicomInputStream dis = new DicomInputStream(file)) {

            Attributes dicomAttributes = dis.readDataset(-1, -1);

            if (type.equals(Double.class)) {
                return (T) Double.valueOf(dicomAttributes.getString(tag));
            }

            if (type.equals(java.util.Date.class)) {
                return (T) dicomAttributes.getDate(tag);
            }

            return (T) dicomAttributes.getString(tag);

        } catch (IOException e) {
//            throw new DicomTagParseException();
            throw new IOException();
        }
    }

}
