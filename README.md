# Network-Communication-
The course is an introduction to networks, protocols and communication. The tasks includes socket programming to implement simple applications according to the client/server model.

The course included a project work which was divided into different tasks. The tasks are task1, task2, and task 3 which can be found in the folder with the name "Nätverk". A explanation of the various tasks can be seen below:

- Task 1: The assignment involves creating a TCP client in Java (TCPClient) for bidirectional communication with servers. TCPAsk, a companion application, utilizes TCPClient to send requests, receive responses, and decode them into text for display, emphasizing understanding TCP socket communication.

- Task 2: This task expands the TCPClient class to handle diverse server communication methods. It introduces options for closing connections based on timeout or data limit conditions. The extended TCPClient class features a constructor with parameters for shutdown, timeout, and limit. TCPAsk, the companion application, includes support for these parameters, allowing users to specify connection behavior.

- Task 3: This task involves creating HTTPAsk, a web server that uses TCPClient from previous tasks. HTTPAsk reads HTTP GET requests and returns the output as an HTTP response. The main method of HTTPAsk initializes the server with a given port number. Key steps include reading and parsing HTTP requests to detect completion and generating valid HTTP responses.
