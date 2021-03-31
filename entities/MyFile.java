package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MyFile implements Serializable {

	private String fileName = null;
	private int size = 0;
	public byte[] mybytearray;
	private String Type= null;
	private String Rnumber;
	private String UserName;

	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	public MyFile(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}

	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	public void setMybytearray(byte[] mybytearray) {
		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		this.Type = type;
	}

	public String getRnumber() {
		return Rnumber;
	}

	public void setRnumber(String rnumber) {
		this.Rnumber = rnumber;
	}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		this.UserName = userName;
	}
	
	public String ToString() {
		return ("\nFile Name: "+fileName+"\nFile sent by: "+UserName+ "\nFile Size: " +size+"\nFile Array: " +mybytearray.toString()+"\nFile Type: " +Type+"\nFile RNumber: "+ Rnumber);
	}

}
