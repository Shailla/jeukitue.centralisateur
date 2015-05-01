package jkt.centralisateur.mail;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.Resource;

public abstract class Email {
    protected VelocityEngine velocityEngine;
    protected Resource vmTemplate;

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    public void setVmTemplate(Resource vmTemplate) {
        this.vmTemplate = vmTemplate;
    }
}
