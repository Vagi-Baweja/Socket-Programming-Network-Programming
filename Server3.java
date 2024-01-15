import java.io.*;
import java.net.*;
class RequestProcessor extends Thread
{
private Socket socket;
RequestProcessor(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream is =  socket.getInputStream();
OutputStream os = socket.getOutputStream();
int bytesToReceive=1024;
byte tmp[] = new byte[1024];
byte header[] = new byte[1024];
int bytesReadCount;
int i,j,k;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount = is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i] = tmp[k];
i++;
}
j=j+bytesReadCount;
}
int requestLength=0;
i=1;
j = 1023;
while(j>=0)
{
requestLength = requestLength+(header[j]*i);
i=i*10;
j--;
}
byte ack[] = new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

byte request[] = new byte[requestLength];
bytesToReceive=requestLength;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount = is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
request[i] = tmp[k];
i++;
}
j=j+bytesReadCount;
}
ByteArrayInputStream bais = new ByteArrayInputStream(request);
ObjectInputStream ois = new ObjectInputStream(bais);
Student s = (Student)ois.readObject();
System.out.println("Roll number : "+s.rollNumber);
System.out.println("Name : "+s.name);
System.out.println("Gender : "+s.gender);
System.out.println("City code : "+s.city.code);
System.out.println("City : "+s.city.name);
String responseString = "Data Saved";
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(responseString);
oos.flush();
byte objectBytes[] = baos.toByteArray();
int responseLength = objectBytes.length;
int x;
i=1023;
x = responseLength;
header = new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
os.write(header,0,1024); //from which index, how many
os.flush();
System.out.println(" Response Header Sent : "+responseLength);

while(true)
{
bytesReadCount = is.read(ack);
if(bytesReadCount==-1) continue;
break;
} 

System.out.println("Acknowledment received");

int bytesToSend = responseLength;
int chunkSize=1024;
j = 0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
System.out.println("Response Sent");

while(true)
{
bytesReadCount = is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("Ack recived");

socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
class Server3
{
private ServerSocket serverSocket;
Server3()
{
try
{
serverSocket = new ServerSocket(5500);
startListening();
}catch(Exception e)
{
System.out.println(e);
}
}
private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port 5500");
socket = serverSocket.accept();  
requestProcessor=new RequestProcessor(socket);
}
}catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String gg[])
{
Server3 server = new Server3();
}
}