# Proof of concept of the integration of Docker, Docker Compose, Apache Camel and Apache ActiveMQ. 
The purpose of this project is to create a sample project which can be used as a useful guide to integrate custom Docker images with common images. 
Aside from that, the modules contained in this project will use Apache Camel and Apache ActiveMQ.
The design to implement is explained in the following sections. It does not follow to show all the details required as it will only used to determine an overview of the entire architecture.
That's being said, let's explain the four components which the architecture is based on.

## Step 1: [JMeter test](https://github.com/dmunozr/docker-camel-activemq/tree/master/load-tests) . This test suite has been created to make easier the generation of the HTTP requests which will be the entry point of the whole process.
The JMeter suite includes just one test which is basically a HTTP POST sample like this:

    POST http://localhost:8080/rest/v1/requests
    
    Content-Type: application/json
    SponsorWebsite: www.coches.net
    
    {
        "requests": [{
                "car": {
                    "brand": "Peugeot",
                    "model": "5008",
                    "version": "GT Line",
                    "year": "2017"
                },
                "client": {
                    "firstName": "Daniel",
                    "lastName": "Muñoz Rivas",
                    "telephone": "690525252",
                    "email": "daniel@gmail.com"
                }
            },
            {
                "car": {
                    "brand": "Ford",
                    "model": "Focus",
                    "version": "Inspire",
                    "year": "2012"
                },
                "client": {
                    "firstName": "Nazareth",
                    "lastName": "Jimenez Vela",
                    "telephone": "610111111",
                    "email": "nazareth@gmail.com"
                }
            }
        ]
    }

NOTE: as you can see, the HTTP content will be a JSON containing some information about a client interested in a specific car. 
The identifier of the sponsor Website where the request was originated must be included as a HTTP header.

## Step 2: [Producer](https://github.com/dmunozr/docker-camel-activemq/tree/master/producer). It is a Spring Boot application which main goal is to receive the HTTP requests sent by the JMeter aforementioned.
The rest controlled is based on the Camel component [Restlet](http://camel.apache.org/restlet.html).
At this point, the HTTP content is validated and transformed into a Java object of the class [CarBudgetsRequest.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/model/src/main/java/com/demo/dto/CarBudgetsRequest.java) through Jackson.
Here we will apply the enterprise integration pattern Content Based Router to determine if the request is valid or not according to the expected header 'SponsorWebsite'.
If the HTTP request is valid, then a message is sent to a queue of ActiveMQ named 'requestsToProcess'. For example:

    {
        "requests": [{
            "car": {
                "brand": "Peugeot",
                "model": "5008",
                "version": "GT Line",
                "year": 2017
            },
            "client": {
                "firstName": "Daniel",
                "lastName": "Muñoz Rivas",
                "telephone": "690525252",
                "email": "daniel@gmail.com"
            }
        }, {
            "car": {
                "brand": "Ford",
                "model": "Focus",
                "version": "Inspire",
                "year": 2012
            },
            "client": {
                "firstName": "Nazareth",
                "lastName": "Jimenez Vela",
                "telephone": "610111111",
                "email": "nazareth@gmail.com"
            }
        }]
    }

The messages are sent to the queue 'requestToProcess' using the [ActiveMQ component](http://camel.apache.org/activemq.html). 
This component must be configured previously by using the Spring XML context file. It is configured [here](https://github.com/dmunozr/docker-camel-activemq/blob/master/producer/src/main/resources/applicationContext.xml). 

## Step 3: [Consumer](https://github.com/dmunozr/docker-camel-activemq/tree/master/consumer). It is another Spring Boot application which reads, processes the messages from an ActiveMQ queue, and then it sends the messages to the topic 'requestProcessed'.
The messages are retrieved from the queue 'requestToProcess' using the [ActiveMQ component](http://camel.apache.org/activemq.html).
To process a message, the first step is splitting up the original message to get individual car budget requests. After that, we get a JSON like this:

    {
        "car": {
            "brand": "Peugeot",
            "model": "5008",
            "version": "GT Line",
            "year": 2017
        },
        "client": {
            "firstName": "Daniel",
            "lastName": "Muñoz Rivas",
            "telephone": "690525252",
            "email": "daniel@gmail.com"
        }
    }

The splitting up of the original message into different JSON piece has been implemented using the JsonPath utility which is available as of Camel 2.13.
At this point, the next step is unmarshal the JSON (plain text) into a Java object of the class [CarBudgetRequest.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/model/src/main/java/com/demo/dto/CarBudgetRequest.java). This is implemented by defining a [custom type converter](http://camel.apache.org/type-converter.html), which is "JSONToCarBudgetRequestTypeConverter".
By using a [Camel route](http://camel.apache.org/routes.html) and a [Camel handler](https://camel.apache.org/maven/current/camel-core/apidocs/org/apache/camel/Handler.html), the budget request is enriched so it also contains the minimum and maximum price, and the availability of that model car as well.
This enricher also extract the value of the header 'SponsorWebsite' and include it as part of the JSON of the message body. 
Therefore, two new nodes are added to the input JSON: transactionInfo and additionalInfo.

    {
        "transactionInfo": {
            "sponsorId": "www.coches.net"
        },
        "additionalInfo": {
            "minPrice": 20452.324,
            "maxPrice": 27133.469,
            "availability": "LOW"
        },
        "car": {
            "brand": "Peugeot",
            "model": "5008",
            "version": "GT Line",
            "year": 2017
        },
        "client": {
            "firstName": "Daniel",
            "lastName": "Muñoz Rivas",
            "telephone": "690525252",
            "email": "daniel@gmail.com"
        }
    }

Aside from this update, the handler [EnrichHandleCarBudgetRequestHandler.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/consumer/src/main/java/com/demo/camel/handle/EnrichHandleCarBudgetRequestHandler.java) adds a new header to the message ('CarBrand'), which contains the brand of the car.
This header will be used in the next steps to filter the messages retrieved from the topic 'requestsProcessed'. Finally, the message is sent to the topic.

## Step 4: [Final-Consumer](https://github.com/dmunozr/docker-camel-activemq/tree/master/final-consumer). It is the last of our Spring Boot applications. It processes and logs the messages from the topic just mentioned 'requestsProcessed'. 

Although initially all the messages are processed, they are filtered by their car brand. Only the messages related to desired brands will be processed (i.e.: "Peugeot"). This means that the messages related to a non-allowed car brand are rejected.
This behaviour is implemented by [CarBrandFilter.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/camel/filter/CarBrandFilter.java) which is a [custom Camel Filter](http://camel.apache.org/message-filter.html).
After filtering, we execute [AddCarInformationAsHeaderProcessor.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/camel/processor/AddCarInformationAsHeaderProcessor.java) which is a [custom Camel Processor](http://camel.apache.org/processor.html).
It processes the message to extract the brand, model and version from the input message, and save them as a Camel Message Header. For example, the result of processing the above JSON will be:

        ...
        "car": {
            "brand": "Peugeot",
            "model": "5008",
            "version": "GT Line",
            "year": 2017
        },
        ...

        Header:
            imgFinderQuery = "Peugeot 5008 GT Line"

The next step is to invoke [ImgFinderComponent.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/camel/component/ImgFinderComponent.java) which is a [custom Camel Component](http://camel.apache.org/writing-components.html).
For further details, take a look at the Java classes in the package [com.demo.camel.component](https://github.com/dmunozr/docker-camel-activemq/tree/master/final-consumer/src/main/java/com/demo/camel/component)
This component is invoked as same as a [predefined Camel component](http://camel.apache.org/components.html):

    to("imgfinder:foo?headerContainingQuery=imgFinderQuery");

The purpose of our custom component "ImgFinder" is to execute a search on Yahoo Website (https://www.yahoo.com/) and retrieve the first image returned from the HTTP response.
For example, it extracts the value to search from the header which name is received as the parameter "headerContainingQuery". In our case, this header is "imgFinderQuery".
Therefore, the component reads that header and then it will invoke the following URL:

    https://es.images.search.yahoo.com/search/images?p=Peugeot%205008%20GT%20Line

We execute the search by an utility class [YahooSearchUtil.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/util/YahooSearchUtil.java). 
It parses the HTTP response, extracts the first result URL and stores it as a new header named "imgFinderResult".
According to the route [ProcessRequestsProcessedAndLogThemRoute.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/camel/route/ProcessRequestsProcessedAndLogThemRoute.java), the next step is to inject that image URL in the JSON.
To achieve that, we have implemented [InjectImageUrlProcessor.java](https://github.com/dmunozr/docker-camel-activemq/blob/master/final-consumer/src/main/java/com/demo/camel/processor/InjectImageUrlProcessor.java) which is another [custom Camel Processor](http://camel.apache.org/processor.html)
So, the JSON will contains an image of the car that we are looking for. Let's see an example:

        "car": {
            "year": 2017,
            **"imageUrl": "https:\/\/tse4.mm.bing.net\/th?id=OIP.ZLkSxsHwq1oaenB1duTK6wHaE8&pid=15.1&P=0&w=300&h=300",**
            "model": "5008",
            "brand": "Peugeot",
            "version": "GT Line"
        },

At this point, we are done. We finish the process writing the message into the log:
    
    {
        "car": {
            "year": 2017,
            "imageUrl": "https:\/\/tse4.mm.bing.net\/th?id=OIP.ZLkSxsHwq1oaenB1duTK6wHaE8&pid=15.1&P=0&w=300&h=300",
            "model": "5008",
            "brand": "Peugeot",
            "version": "GT Line"
        },
        "additionalInfo": {
            "minPrice": 20452.324,
            "maxPrice": 27133.469,
            "availability": "LOW"
        },
        "client": {
            "firstName": "Daniel",
            "lastName": "Muñoz Rivas",
            "telephone": "690525252",
            "email": "daniel@gmail.com"
        },
        "transactionInfo": {
            "sponsorId": "www.coches.net"
        }
    }

# How to start up the environment using Docker.

Clone the repository in your local machine and execute the following command from the main directory:

    ./docker-run.sh build_start
    
This command will start an instance of each container:
* 1 instance of the ActiveMQ
* 1 instance of the Producer application
* 1 instances of the Consumer application
* 1 instances of the Final-Consumer application
    * Configured to listen messaged containing the brand "Peugeot" or "Ford"
 
You can invoke the entry endpoint easily by using the JMeter [load-test](https://github.com/dmunozr/docker-camel-activemq/blob/master/load-tests/src/test/jmeter/loadTests.jmx).

## Clustering

If you prefer to test the applications under a cluster environment, you can test it by executing the following command instead:

    ./docker-run.sh cluster_build_start

This command ends up using another docker compose configuration ([docker-cluster-compose.yml](https://github.com/dmunozr/docker-camel-activemq/blob/master/docker-cluster-compose.yml)), which defines:
* 1 instance of the ActiveMQ
* 1 instance of the Producer application
* 2 instances of the Consumer application
* 2 instances of the Final-Consumer application
    * One of them is configured to listen messages containing the brands "Peugeot"
    * The other one listens messages containing the brand "Ford"
