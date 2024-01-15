import java.net.*;
import java.io.*;
class Client2
{
public static void main(String gg[])
{
try
{
int rollNumber=Integer.parseInt(gg[0]);
String name=gg[1];
String  gender = gg[2];
String request = rollNumber + "," +name+","+gender+"#";
String response;
Socket socket=new Socket("localhost",5500);
OutputStream os;
OutputStreamWriter osw;
InputStream is;
InputStreamReader isr;
StringBuffer sb;
os=socket.getOutputStream();
osw = new OutputStreamWriter(os);
osw.write(request);
osw.flush(); //very very important
is=socket.getInputStream();
isr = new InputStreamReader(is);
sb = new StringBuffer();
int x;
while(true)
{
x=isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
response=sb.toString();
System.out.println("Response is : "+response);
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}