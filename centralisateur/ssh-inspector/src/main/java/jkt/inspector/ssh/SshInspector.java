package jkt.inspector.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SshInspector {

	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";

	// Paramètres plateforme
	private static final String HOST = "host";
	private static final String PORT = "port";
	private static final String LOGIN = "login";
	private static final String PWD = "pwd";

	// Paramètres sondes
	private static final String SCRIPT = "script";

	public static void main(String[] args) {
		try {
			new SshInspector();
		}
		catch(final Exception exception) {
			exception.printStackTrace(System.err);
		}
	}

	private SshInspector() throws Exception {

		/* *************************************************** */
		// Load platforms and probes properties
		/* *************************************************** */

		final List<Properties> pfs = loadPropertiesDir(new File("platforms"));
		final List<Properties> probes = loadPropertiesDir(new File("probes"));
		
		final Map<String, Map<String, String>> results = new HashMap<>();


		/* *************************************************** */
		// Inspect each platform with all probes
		/* *************************************************** */

		for(final Properties pf : pfs) {
			final String pfName = pf.getProperty(NAME);
			final String pfDescription = pf.getProperty(DESCRIPTION);
			final String pfHost = pf.getProperty(HOST);
			final int pfPort = Integer.parseInt(pf.getProperty(PORT));
			final String pfLogin = pf.getProperty(LOGIN);
			final String pfPwd = pf.getProperty(PWD);

			final Map<String, String> pfResults = new HashMap<>();
			results.put(pfName, pfResults);
			
			System.out.println("\nConnecting to '" + pfName + " (" + pfDescription + ")' avec " + pfHost + ":" + pfPort + "@" + pfLogin + ":" + pfPwd + "'");

			try {
				final Session session = connect(pfHost, pfPort, pfLogin, pfPwd);

				for(final Properties probe : probes) {
					final String pbName = probe.getProperty(NAME);
					final String pbDescription = probe.getProperty(DESCRIPTION);
					final String pbScript = probe.getProperty(SCRIPT);

					System.out.println("Exécution du script '" + pbName + " (" + pbDescription + ")' qui est '" + pbScript + "'");

					try {
						final String result = execute(session, pbScript);
						final String lastLine = result.substring(result.lastIndexOf("\n"));
						System.out.println("Dernière ligne du résultat : " + lastLine + "'");
						
						pfResults.put(pbName, lastLine);
					}
					catch(final Exception exception) {
						System.err.println("Echec de connexion !");
					}
				}
			}
			catch(final Exception exception) {
				System.err.println("Echec de connexion !");
				exception.printStackTrace(System.err);
			}
		}
		
		csvExport(results);
	}

	private Session connect(final String host, final int port, final String login, final String pwd) throws Exception {
		JSch jsch = new JSch();

		UserInfo ui = new UserInfo() {
			@Override
			public String getPassphrase() {
				return null;
			}

			@Override
			public String getPassword() {
				return pwd;
			}

			@Override
			public boolean promptPassword(String message) {
				return true;
			}

			@Override
			public boolean promptPassphrase(String message) {
				return false;
			}

			@Override
			public boolean promptYesNo(String message) {
				return false;
			}

			@Override
			public void showMessage(String message) {
			}
		};

		final Session session = jsch.getSession(login, host, port);
		session.setUserInfo(ui);
		session.connect();

		return session;
	}

	private String execute(final Session session, final String script) throws Exception {
		String result = "";
		ChannelExec channel = (ChannelExec)session.openChannel("exec");

		// X Forwarding
		// channel.setXForwarding(true);

		channel.setCommand(script);
		channel.setInputStream(System.in);
		channel.setOutputStream(System.out);
		channel.setErrStream(System.err);

		InputStream in = channel.getInputStream();


		channel.connect();

		byte[] tmp = new byte[10000];
		
		while(true) {
			while(in.available() > 0){
				int i = in.read(tmp, 0, tmp.length);
				
				if(i<0)
					break;
				
				result = new String(tmp, 0, i);
				System.out.print(new String(tmp, 0, i));
			}
			
			if(channel.isClosed()){
				if(in.available() > 0) continue; 
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
			
			try {
				Thread.sleep(1000);
			}
			catch(Exception exception){
				exception.printStackTrace(System.err);
			}
		}
		
		channel.disconnect();
		session.disconnect();
		
		return result;
	}

	/**
	 * Load all properties files in the specified directory.
	 * @param dir directory in which are the properties files
	 * @return list of the properties
	 */
	private List<Properties> loadPropertiesDir(final File dir) throws Exception {
		if(!dir.exists()) {
			System.err.println("Hé ho, ton répertoire il existe pas '" + dir.getAbsolutePath() + "'");
		}

		if(!dir.isDirectory()) {
			System.err.println("Hé ho, ton répertoire c'est carrément pas un répertoire '" + dir.getAbsolutePath() + "'");
		}

		final List<Properties> props = new ArrayList<>();

		File[] files = dir.listFiles(
				new FilenameFilter() {
					public boolean accept(File directory, String fileName) {
						return fileName.endsWith(".properties");
					}
				});

		for(final File file : files) {
			final Properties prop = loadPropertiesFile(file);
			props.add(prop);

			System.out.println("Plateforme chargée '" + file + "'");
		}

		return props;
	}

	/**
	 * Load a properties file.
	 * @param file properties file to load
	 * @return properties loaded
	 */
	private Properties loadPropertiesFile(final File file) throws Exception {
		Properties properties = new Properties();
		FileInputStream input = null;

		try {
			input = new FileInputStream(file);
			properties.load(input);

		}
		finally{
			IOUtils.closeQuietly(input);
		} 

		return properties;
	}
	
	private void csvExport(final Map<String, Map<String, String>> results) throws Exception {
		final FileWriter writer = new FileWriter("results.csv");
		
		for(final String pfName : results.keySet()) {
			final Map<String, String> pfResults = results.get(pfName);
			
			for(final String pbName : pfResults.keySet()) {
				final String pbResult = pfResults.get(pbName);
				writer.write(pfName);
				writer.write(",");
				writer.write(pbName);
				writer.write(",");
				writer.write(pbResult);
				writer.write("\n");
			}			
		}
		
		IOUtils.closeQuietly(writer);
	}
}
