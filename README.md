Server Side:
Create a ServerSocket:
Use the ServerSocket class to create a socket that will listen for incoming connections.

Wait for Connection:
Use the accept() method of the ServerSocket class to wait for a client to connect.

Get Input and Output Streams:
Obtain input and output streams from the Socket to send and receive data.

Read and Write Data:
Use InputStream and OutputStream to read from and write to the client.

Close the Connection:
Close the Socket and ServerSocket when the communication is complete.

Client Side:
Create a Socket:
Use the Socket class to create a socket and connect to the server.

Get Input and Output Streams:
Obtain input and output streams from the Socket to send and receive data.

Read and Write Data:
Use InputStream and OutputStream to read from and write to the server.

Close the Connection:
Close the Socket when the communication is complete.
