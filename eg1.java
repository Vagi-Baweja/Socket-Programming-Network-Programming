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
class psp
{
public static void main(String gg[])
{
try
{
Student s1 = new Student();
s1.rollNumber=101;
s1.name="Sameer";
s1.gender='M';
City c =new City();
c.code=1;
c.name = "Ambala";
s1.city=c;
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(s1);
oos.flush();
byte b[];
b=baos.toByteArray();
System.out.println("Object serialized");
ByteArrayInputStream bais = new ByteArrayInputStream(b);
ObjectInputStream ois = new ObjectInputStream(bais);
Student s2 = (Student)ois.readObject();
System.out.println("Object deserialized");
System.out.println("Roll number : "+s2.rollNumber);
System.out.println("Name :"+s2.name);
System.out.println("Gender : "+s2.gender);
System.out.println("City details");
System.out.println("City code : "+s2.city.code);
System.out.println("City Name : "+s2.city.name);
}catch(Exception e)
{
System.out.println(e);
}
}
} 