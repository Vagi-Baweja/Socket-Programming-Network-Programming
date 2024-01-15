Server Side:

1.Create a ServerSocket:
Use the ServerSocket class to create a socket that will listen for incoming connections.

2.Wait for Connection:
Use the accept() method of the ServerSocket class to wait for a client to connect.

3.Get Input and Output Streams:
Obtain input and output streams from the Socket to send and receive data.

4.Read and Write Data:
Use InputStream and OutputStream to read from and write to the client.

5.Close the Connection:
Close the Socket and ServerSocket when the communication is complete.

Client Side:

1.Create a Socket:
Use the Socket class to create a socket and connect to the server.

2.Get Input and Output Streams:
Obtain input and output streams from the Socket to send and receive data.

3.Read and Write Data:
Use InputStream and OutputStream to read from and write to the server.

4.Close the Connection:
Close the Socket when the communication is complete.
