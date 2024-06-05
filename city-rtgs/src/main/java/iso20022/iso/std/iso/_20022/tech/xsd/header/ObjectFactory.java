
package iso20022.iso.std.iso._20022.tech.xsd.header;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cbl.filereadwrite.service.header package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cbl.filereadwrite.service.header
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Header.Message }
     * 
     */
    public Header.Message createHeaderMessage() {
        return new Header.Message();
    }

    /**
     * Create an instance of {@link Header.Message.Receiver }
     * 
     */
    public Header.Message.Receiver createHeaderMessageReceiver() {
        return new Header.Message.Receiver();
    }

    /**
     * Create an instance of {@link Header.Message.Sender }
     * 
     */
    public Header.Message.Sender createHeaderMessageSender() {
        return new Header.Message.Sender();
    }

    /**
     * Create an instance of {@link Header.Message.InterfaceInfo }
     * 
     */
    public Header.Message.InterfaceInfo createHeaderMessageInterfaceInfo() {
        return new Header.Message.InterfaceInfo();
    }

    /**
     * Create an instance of {@link Header.Message.NetworkInfo }
     * 
     */
    public Header.Message.NetworkInfo createHeaderMessageNetworkInfo() {
        return new Header.Message.NetworkInfo();
    }

    /**
     * Create an instance of {@link Header.Message.Receiver.FullName }
     * 
     */
    public Header.Message.Receiver.FullName createHeaderMessageReceiverFullName() {
        return new Header.Message.Receiver.FullName();
    }

    /**
     * Create an instance of {@link Header.Message.Sender.FullName }
     * 
     */
    public Header.Message.Sender.FullName createHeaderMessageSenderFullName() {
        return new Header.Message.Sender.FullName();
    }

}
