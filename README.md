#JMS TUNNELLING
This project presents a simple solution for the JMS over HTTP Tunnelling problem. 

##Contents
###ServiceProducer
A service which has two queues working over tcp protocol. It does not do anything special, listens to Requests queue, after receiving a message enriches it with 
text "PRODUCER RESPONDING" and puts it as a JMS message to the Response queue

###ServiceConsumer
A service which has sends a message to one queue and listens for a response on the other.
 
###ServiceProxy
A proxy which has two queues and one http endpoint. 
The proxy listens to the Request queue, after receiving a message, enriches it with text "PROXY FORWARDING MESSAGE"
and sends it via http POST to a specified endpoint.
The proxy then broadcasts an endpoint, from which, after receiving a message, enriches it with text "PROXY FORWARDING TO JMS" and puts it on a jms Response queue.

###ServiceGateway
A gateway project which broadcasts on http endpoint, from which, after receiving a message, enriches it with text "GATEWAY FORWARDING TO JMS" and puts it on 
a specified jms queue.
It also listens to a specified jms queue and, after receiving a message, enriches it with text "GATEWAY FORWARDING MESSAGE HTTP" and sends it to a specified 
http endpoint.

##Schema
Request flow:

    SERVICE CONSUMER ---tcp (jms)---> SERVICE PROXY ---http (POST)---> GATEWAY ---tcp (jms)---> SERVICE CONSUMER

Response flow:

    SERVICE CONSUMER <---tcp (jms)--- SERVICE PROXY <---http (POST)--- GATEWAY <---tcp (jms)--- SERVICE CONSUMER

##Technologies
*Queues - ActiveMQ, started in by the application itself.

*HTTP Endpoints - Jetty server, also started by the application.

*Logging - logback and slf4j