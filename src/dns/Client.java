package dns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client 
{
	public static final int DEFAULT_PORT = 6052;
	
	public static void main(String[] args) throws IOException 
	{
		BufferedReader networkBin = null;
		PrintWriter networkOutput = null;
		BufferedReader localBin = null;
		Socket socket = null;
		
		try 
		{
			System.out.println("Type 'quit' to exit.");
			System.out.println("Enter the server IP.");
			localBin = new BufferedReader(new InputStreamReader(System.in));
			String serverIP = localBin.readLine();
			socket = new Socket(serverIP, DEFAULT_PORT);
			networkBin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			networkOutput = new PrintWriter(socket.getOutputStream(),true);
			System.out.println("Enter the name you wish to resolve.");
			
			boolean done = false;
			while (!done) 
			{
				String line = localBin.readLine();
				if (line.equals("exit"))
				{
					done = true;
				}
				else 
				{
//					int length = line.length();
					networkOutput.println(line);
					System.out.println(networkBin.readLine());
					System.out.println("Enter another name to resolve or type 'exit' to quit.");
				}
			}
		}
		catch (IOException ioe) 
		{
			System.err.println(ioe);
		}
		finally 
		{
			if (networkBin != null)
			{	networkBin.close();	}
			if (localBin != null)
			{	localBin.close();	}
			if (networkOutput != null)
			{	networkOutput.close();	}
			if (socket != null)
			{	socket.close();	}
		}
	}
}
