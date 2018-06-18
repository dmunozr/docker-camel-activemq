# Proof of concept of the integration of Docker, Docker Compose, Apache Camel and Apache ActiveMQ 
The purpose of this project is to create a sample project which can be used as a useful guide to integrate custom Docker images with common images. Aside from that, the modules contained in this project will use Apache Camel and Apache ActiveMQ.
The design to implement is explained in the following sections. It does not follow to show all the details required as it will only used to determine an overview of the entire architecture.

## Step 1: HTTP requests are received in a rest controller. The HTTP content will be a JSON containing some information about a client interested in a specific car.

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

## Step 2: The HTTP content is used for creating a new message which will be sent to a queue. The body of this message will be the original HTTP content with no modifications and it will contain a new header with the sponsor identifier. For example: 
	
	sponsorId: "www.coches.net"

## Step 3: Message is sent to a queue named "requestsToProcess"

## Step 4: An idle node reads the message from the queue and starts to process it.

## Step 5: The message content is processed. The process will enrich the message content so useful information is included. All the transformations applied along this process will be done using Camel routes. Therefore, let's take a look at the transformations which will be done over the original message content:
* Split the message content up so different requests are created from the origin message content.
		The output of this process will be a set of messages like this:

		{
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
        }

* Enrich every message so the message header "sponsorId" is included as part of the JSON content. For example:

		{
			"transactionInfo": {
				"sponsorId": "www.coches.net"
			},
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
		}

* Include the minimum and maximum price for the requested car and which is the availability level for every request:

		{
			"transactionInfo": {
				"sponsorId": "www.coches.net"
				"minPrice": 25.000,
				"maxPrice": 33.000,
				"availability": "HIGH"
			},
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
		}

## Step 6: The message is sent to the topic "requestsProcessed" so lots of cars dealerships are notified about the sales opportunity.

## Step 7: The message leaves the topic as it can be processed by the applications used by final salesmen.

## Step 8: The application filters the messages received from the topic so only desired brands are processed. Although initially all the messages are processed, they will be filtered by its brand. Only the messages related to desired brands will be written into the temporary folder.


