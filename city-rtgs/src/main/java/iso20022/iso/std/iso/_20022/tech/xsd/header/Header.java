
package iso20022.iso.std.iso._20022.tech.xsd.header;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Message"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="SenderReference" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="MessageIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Format" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Sender"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="FullName"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Receiver"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="FullName"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="InterfaceInfo"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="UserReference" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="NetworkInfo"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="Network" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
        "message"
})
@XmlRootElement(name = "Header")
public class Header {

    @XmlElement(name = "Message", required = true)
    protected Message message;

    /**
     * Gets the value of the message property.
     *
     * @return possible object is
     * {@link Message }
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value allowed object is
     *              {@link Message }
     */
    public void setMessage(Message value) {
        this.message = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="SenderReference" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="MessageIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Format" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Sender"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="FullName"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Receiver"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="FullName"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="InterfaceInfo"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="UserReference" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="NetworkInfo"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="Network" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Message", propOrder = {
            "senderReference", "messageIdentifier", "format", "sender", "receiver", "interfaceInfo", "networkInfo"
    })
    public static class Message {
        @XmlElement(name = "SenderReference", required = true, namespace = "")
        protected String senderReference;
        @XmlElement(name = "MessageIdentifier", required = true, namespace = "")
        protected String messageIdentifier;
        @XmlElement(name = "Format", required = true, namespace = "")
        protected String format;
        @XmlElement(name = "Sender", required = true, namespace = "")
        protected Sender sender;
        @XmlElement(name = "Receiver", required = true, namespace = "")
        protected Receiver receiver;
        @XmlElement(name = "InterfaceInfo", required = true, namespace = "")
        protected InterfaceInfo interfaceInfo;
        @XmlElement(name = "NetworkInfo", required = true, namespace = "")
        protected NetworkInfo networkInfo;

        /**
         * Gets the value of the senderReference property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSenderReference() {
            return senderReference;
        }

        /**
         * Sets the value of the senderReference property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSenderReference(String value) {
            this.senderReference = value;
        }

        /**
         * Gets the value of the messageIdentifier property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getMessageIdentifier() {
            return messageIdentifier;
        }

        /**
         * Sets the value of the messageIdentifier property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setMessageIdentifier(String value) {
            this.messageIdentifier = value;
        }

        /**
         * Gets the value of the format property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getFormat() {
            return format;
        }

        /**
         * Sets the value of the format property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setFormat(String value) {
            this.format = value;
        }

        /**
         * Gets the value of the sender property.
         *
         * @return possible object is
         * {@link Sender }
         */
        public Sender getSender() {
            return sender;
        }

        /**
         * Sets the value of the sender property.
         *
         * @param value allowed object is
         *              {@link Sender }
         */
        public void setSender(Sender value) {
            this.sender = value;
        }

        /**
         * Gets the value of the receiver property.
         *
         * @return possible object is
         * {@link Receiver }
         */
        public Receiver getReceiver() {
            return receiver;
        }

        /**
         * Sets the value of the receiver property.
         *
         * @param value allowed object is
         *              {@link Receiver }
         */
        public void setReceiver(Receiver value) {
            this.receiver = value;
        }

        /**
         * Gets the value of the interfaceInfo property.
         *
         * @return possible object is
         * {@link InterfaceInfo }
         */
        public InterfaceInfo getInterfaceInfo() {
            return interfaceInfo;
        }

        /**
         * Sets the value of the interfaceInfo property.
         *
         * @param value allowed object is
         *              {@link InterfaceInfo }
         */
        public void setInterfaceInfo(InterfaceInfo value) {
            this.interfaceInfo = value;
        }

        /**
         * Gets the value of the networkInfo property.
         *
         * @return possible object is
         * {@link NetworkInfo }
         */
        public NetworkInfo getNetworkInfo() {
            return networkInfo;
        }

        /**
         * Sets the value of the networkInfo property.
         *
         * @param value allowed object is
         *              {@link NetworkInfo }
         */
        public void setNetworkInfo(NetworkInfo value) {
            this.networkInfo = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="UserReference" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "userReference"
        })
        public static class InterfaceInfo {

            @XmlElement(name = "UserReference", required = true)
            protected String userReference;

            /**
             * Gets the value of the userReference property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getUserReference() {
                return userReference;
            }

            /**
             * Sets the value of the userReference property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setUserReference(String value) {
                this.userReference = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="Network" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "priority",
                "network"
        })
        public static class NetworkInfo {

            @XmlElement(name = "Priority", required = true)
            protected String priority;
            @XmlElement(name = "Network", required = true)
            protected String network;

            /**
             * Gets the value of the priority property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getPriority() {
                return priority;
            }

            /**
             * Sets the value of the priority property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setPriority(String value) {
                this.priority = value;
            }

            /**
             * Gets the value of the network property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getNetwork() {
                return network;
            }

            /**
             * Sets the value of the network property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setNetwork(String value) {
                this.network = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="FullName"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "bic12",
                "fullName"
        })
        public static class Receiver {

            @XmlElement(name = "BIC12", required = true)
            protected String bic12;
            @XmlElement(name = "FullName", required = true)
            protected FullName fullName;

            /**
             * Gets the value of the bic12 property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBIC12() {
                return bic12;
            }

            /**
             * Sets the value of the bic12 property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBIC12(String value) {
                this.bic12 = value;
            }

            /**
             * Gets the value of the fullName property.
             *
             * @return possible object is
             * {@link FullName }
             */
            public FullName getFullName() {
                return fullName;
            }

            /**
             * Sets the value of the fullName property.
             *
             * @param value allowed object is
             *              {@link FullName }
             */
            public void setFullName(FullName value) {
                this.fullName = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "x1"
            })
            public static class FullName {

                @XmlElement(name = "X1", required = true)
                protected String x1;

                /**
                 * Gets the value of the x1 property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getX1() {
                    return x1;
                }

                /**
                 * Sets the value of the x1 property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setX1(String value) {
                    this.x1 = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="BIC12" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="FullName"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "bic12",
                "fullName"
        })
        public static class Sender {

            @XmlElement(name = "BIC12", required = true)
            protected String bic12;
            @XmlElement(name = "FullName", required = true)
            protected FullName fullName;

            /**
             * Gets the value of the bic12 property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBIC12() {
                return bic12;
            }

            /**
             * Sets the value of the bic12 property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBIC12(String value) {
                this.bic12 = value;
            }

            /**
             * Gets the value of the fullName property.
             *
             * @return possible object is
             * {@link FullName }
             */
            public FullName getFullName() {
                return fullName;
            }

            /**
             * Sets the value of the fullName property.
             *
             * @param value allowed object is
             *              {@link FullName }
             */
            public void setFullName(FullName value) {
                this.fullName = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="X1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "x1"
            })
            public static class FullName {

                @XmlElement(name = "X1", required = true)
                protected String x1;

                /**
                 * Gets the value of the x1 property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getX1() {
                    return x1;
                }

                /**
                 * Sets the value of the x1 property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setX1(String value) {
                    this.x1 = value;
                }

            }

        }

    }

}
