package com.example.test1.config;

import org.dcm4che3.data.*;
import org.dcm4che3.net.*;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class DicomQueryClient {

    public boolean query(String patientId, String accessionNumber, String seriesNumber, String imageNumber) {
        try {
            Device device = new Device("dicom-query-client");
            ApplicationEntity ae = new ApplicationEntity("MAROTECH");
            Connection conn = new Connection();

            device.setExecutor(Executors.newSingleThreadExecutor());
            device.setScheduledExecutor(Executors.newSingleThreadScheduledExecutor());

            device.addConnection(conn);
            device.addApplicationEntity(ae);
            ae.addConnection(conn);

            Connection remoteConn = new Connection();
            remoteConn.setHostname("192.168.1.251");
            remoteConn.setPort(1400);

            AAssociateRQ rq = new AAssociateRQ();
            rq.setCalledAET("MAROTECH");
            rq.setCallingAET("MAROTECH");
            rq.addPresentationContext(new PresentationContext(
                    1, UID.StudyRootQueryRetrieveInformationModelFind,
                    UID.ImplicitVRLittleEndian));

            Association assoc = ae.connect(remoteConn, rq);

            Attributes keys = new Attributes();
            keys.setString(Tag.QueryRetrieveLevel, VR.CS, "STUDY");
            keys.setString(Tag.PatientID, VR.LO, patientId);
            keys.setString(Tag.AccessionNumber, VR.SH, accessionNumber);
            // 시리즈/이미지 검색 시 QueryRetrieveLevel을 SERIES 또는 IMAGE로 바꾸고 Tag도 추가

            DimseRSP rsp = assoc.cfind(UID.StudyRootQueryRetrieveInformationModelFind, Priority.NORMAL, keys, UID.ImplicitVRLittleEndian, 0);

            System.out.println("테스트 데이터\n" + rsp + "\n");

            while (rsp.next()) {
                Attributes data = rsp.getDataset();
                if (data != null) {
                    System.out.println("응답 받은 DICOM 데이터:");
                    System.out.println(data);
                }
            }

            assoc.release();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
