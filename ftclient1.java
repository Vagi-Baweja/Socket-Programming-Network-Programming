import java.net.*;
import java.io.*;
class FTClient
{
public static void main(String gg[])
{
try
{
String fileName = gg[0];
File file = new File(fileName);
if(file.exists()==false)
{
System.out.println("File : "+fileName+" does not exist");
return;
}
if(file.isDirectory())
{
System.out.println(fileName+ "is a directory, not a file");
return;
}
long lengthOfFile = file.length();
String name = file.getName();
byte header[] = new byte[1024];
int i;
long x,k;
i = 0;
k=lengthOfFile;
while(k>0)
{
header[i]=(byte)(k%10);
k=k/10;
i++;
}
header[i] = (byte)',';
i++;
x=name.length();
int r = 0;
while(r<x)
{
header[i]=(byte)name.charAt(r);
i++;
r++;
}
while(i<=1023)
{
header[i]=(byte)32;
i++;
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
FileInputStream fis = new FileInputStream(file);
int chunkSize = 4096;
byte bytes[] = new byte[chunkSize];
int j = 0;
while(j<lengthOfFile)
{
bytesReadCount = fis.read(bytes);
os.write(bytes,0,bytesReadCount);
os.flush();
j=j+bytesReadCount;
}
fis.close();
while(true)
{
bytesReadCount =is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("File "+fileName+" uploaded");
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
