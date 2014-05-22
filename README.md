#PROJECT DESCRIPTION
This project presents a simple solution for the JMS over HTTP Tunnelling problem. 

These are seperate projects, aimed to be only as a proof of concept. Please look into RUNNING section to be sure you 
start it correctly.

##Contents
###ServiceProducer
A service which has two queues working over tcp protocol. It does not do anything special, listens to Requests queue, 
after receiving a message enriches it with text "~~PRODUCER RESPONDING~~" and puts it as a JMS message to the Response 
queue.

###ServiceConsumer
A service which sends a message "~~ORIGINAL MESSAGE FROM SERVICE CONSUMER~~" to one queue and listens for a response on 
the other.
 
###ServiceProxy
A proxy which has two queues and one http endpoint. 
The proxy listens to the Request queue, after receiving a message, enriches it with text "PROXY FORWARDING MESSAGE TO HTTP"
and sends it via http POST to a specified endpoint.
The proxy then broadcasts an endpoint, from which, after receiving a message, enriches it with text "PROXY FORWARDING TO 
JMS" and puts it on a jms Response queue.

###ServiceGateway
A gateway project which broadcasts on http endpoint, from which, after receiving a message, enriches it with text 
"GATEWAY FORWARDING TO JMS" and puts it on a specified jms queue.
It also listens to a specified jms queue and, after receiving a message, enriches it with text "GATEWAY FORWARDING 
MESSAGE HTTP" and sends it to a specified http endpoint.

##Schema
Request flow:

    SERVICE CONSUMER ---tcp (jms)---> SERVICE PROXY ---http (POST)---> GATEWAY ---tcp (jms)---> SERVICE CONSUMER

Response flow:

    SERVICE CONSUMER <---tcp (jms)--- SERVICE PROXY <---http (POST)--- GATEWAY <---tcp (jms)--- SERVICE CONSUMER

##Running
To avoid issues with starting a project which tries to listen on a port which doesn't exist start projects in the 
following order:
1. ServiceProducer
2. ServiceProxy
3. Gateway
4. ServiceConsumer
All the projects have a class with main method for starting.
All the projects have seperate logback.xml configuration files.

##Technologies
*Queues - ActiveMQ, started in by the application itself.

*HTTP Endpoints - Jetty server, also started by the application.

*Logging - logback and slf4j