/**
 *	@author Ariana Fairbanks
 */

package dns;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Handler 
{
	public static final int BUFFER_SIZE = 1024;
	
	@SuppressWarnings("unused")
	public void process(Socket client) throws java.io.IOException 
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		InputStream  fromClient = null;
		OutputStream toClient = null;
		
		try 
		{
			fromClient = new BufferedInputStream(client.getInputStream());
			toClient = new BufferedOutputStream(client.getOutputStream());
			int numBytes;
			
			
			while ( (numBytes = fromClient.read(buffer)) != -1) 
			{
				String full = new String(buffer);
				System.out.println(full);
				int lengthEnd = full.indexOf(" ");
				int length = Integer.parseInt(full.substring(0, lengthEnd));
				String hostName = full.substring(lengthEnd + 1, length + lengthEnd + 1);
				
				System.out.println(hostName);
				byte[] hostAddressBytes = null;
				try
				{
					String hostAddress = InetAddress.getByName(hostName).getHostAddress() + "\n";
					System.out.println("Address " + hostAddress);
					hostAddressBytes = hostAddress.getBytes();
				}
				catch (UnknownHostException uhe) 
				{
					System.err.println("Unknown Host");
					hostAddressBytes = "Unknown Host\n".getBytes();
					System.out.println("reached catch");
				}
				finally
				{
					length = hostAddressBytes.length;
					toClient.write(hostAddressBytes, 0, length);
					toClient.flush();	
					System.out.println("reached finally");
				}
			}	
   		}
		catch (IOException | NumberFormatException e) 
		{
			System.err.println(e);
		}
		finally 
		{
			buffer = null;
			if (fromClient != null) 
			{
				fromClient.close();
			}
			if (toClient != null)
			{
				toClient.close();
			}
		}
	}
}
