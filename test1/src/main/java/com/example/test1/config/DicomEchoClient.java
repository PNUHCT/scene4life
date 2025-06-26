package com.example.test1.config;

import org.dcm4che3.net.ApplicationEntity;
import org.dcm4che3.net.Device;
import org.dcm4che3.net.Connection;
import java.util.concurrent.Executors;
import org.dcm4che3.net.Association;
import org.dcm4che3.net.pdu.AAssociateRQ;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.data.UID;
import org.springframework.stereotype.Service;

@Service
public class DicomEchoClient {

    public boolean echo(String remoteHost, int remotePort, String remoteAET, String localAET) {
        try {
            Device device = new Device("dicom-client");
            ApplicationEntity ae = new ApplicationEntity(localAET);
            Connection conn = new Connection();

            device.setExecutor(Executors.newSingleThreadExecutor());
            device.setScheduledExecutor(Executors.newSingleThreadScheduledExecutor());

            device.addConnection(conn);
            device.addApplicationEntity(ae);
            ae.addConnection(conn);
            ae.setAETitle(localAET);

            Connection remoteConn = new Connection();
            remoteConn.setHostname(remoteHost);
            remoteConn.setPort(remotePort);

            AAssociateRQ rq = new AAssociateRQ();
            rq.setCalledAET(remoteAET);
            rq.setCallingAET(localAET);
            rq.addPresentationContext(new PresentationContext(
                    1, "1.2.840.10008.1.1", UID.ImplicitVRLittleEndian));

            Association assoc = ae.connect(remoteConn, rq);
            assoc.cecho().next(); // 실제 Echo 수행
            assoc.release();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
