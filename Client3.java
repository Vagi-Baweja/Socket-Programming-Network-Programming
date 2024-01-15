import java.net.*;
import java.io.*;
class City implements Serializable
{
public int code;
public String name;
}
class Student implements Serializable
{
public int rollNumber;
public String name;
public char gender;
public City city;
}
class Client3
{
public static void main(String gg[])
{
try
{
int rollNumber=Integer.parseInt(gg[0]);
String name=gg[1];
String  gender = gg[2];
int cityCode=Integer.parseInt(gg[3]);
String cityName=gg[4];
City c = new City();
c.code=cityCode;
c.name=cityName;
Student s = new Student();
s.rollNumber = 101;
s.name="Sameer";
s.gender='M';
s.city=c;
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(s);
oos.flush();
byte objectBytes[];
objectBytes=baos.toByteArray();
int requestLength = objectBytes.length;
byte header[] = new byte[1024];
int x;
int i;
i=1023;
x = requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
Socket socket=new Socket("localhost",5500);
OutputStream os = socket.getOutputStream();
os.write(header,0,1024); //from which index, how many
os.flush();
InputStream is = socket.getInputStream();
byte ack[] = new byte[1];
int bytesReadCount;
while(true)
{
bytesReadCount = is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
int bytesToSend = requestLength;
int chunkSize=1024;
int j = 0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
int bytesToReceive=1024;
byte tmp[] = new byte[1024];
int k;
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
int responseLength=0;
i=1;
j = 1023;
while(j>=0)
{
responseLength = responseLength+(header[j]*i);
i=i*10;
j--;
}
System.out.println("Header recieved : "+responseLength);
ack[0]=1;
os.write(ack,0,1);
os.flush();
System.out.println("Ack sent");
byte response[] = new byte[responseLength];
bytesToReceive=responseLength;
i=0;
j=0;
System.out.println("now receivng response");
while(j<bytesToReceive)
{
bytesReadCount = is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
response[i] = tmp[k];
i++;
}
j=j+bytesReadCount;
}
System.out.println("response recieved");
ack[0]=1;
os.write(ack);
os.flush();
socket.close();
ByteArrayInputStream bais = new ByteArrayInputStream(response);
ObjectInputStream ois = new ObjectInputStream(bais);
String responseString=(String)ois.readObject();
System.out.println("Response is : "+responseString);
}catch(Exception e)
{
System.out.println(e);
}
}
}