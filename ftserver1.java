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
InputStream is=socket.getInputStream();
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
int lengthOfFile = 0;
i=0;
j=1;
while(header[i]!=',')
{
lengthOfFile = lengthOfFile+(header[i]*j);
j=j*10;
i++;
}
i++;
StringBuffer sb = new StringBuffer();
while(i<=1023)
{
sb.append((char)header[i]);
i++;
}
String fileName = sb.toString().trim();
System.out.println("Receving file : "+fileName+" of length: "+lengthOfFile);
File file = new File("uploads"+File.separator+fileName);
if(file.exists()) file.delete();
FileOutputStream fos = new FileOutputStream(file);
byte ack[] = new byte[1];
ack[0]=1;
os.write(ack,0,1); 
os.flush();
int chunkSize = 4096;
byte bytes[] = new byte[chunkSize];
i=0;
long m;
m = 0;
while(m<lengthOfFile)
{
bytesReadCount = is.read(bytes);
if(bytesReadCount==-1) continue;
fos.write(bytes,0,bytesReadCount);// which index and how many
fos.flush(); 
m=m+bytesReadCount;
}
fos.close();
ack[0]=1;
os.write(ack,0,1);
os.flush();
System.out.println("File Saved to "+file.getAbsolutePath());
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
class FTServer
{
private ServerSocket serverSocket;
FTServer()
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
FTServer server = new FTServer();
}
}