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

	private static final String RESULT_BEGIN = "{result}";
	private static final String RESULT_END = "{/result}";

	public static void main(String[] args) {
		try {
			new SshInspector();
		}
		catch(final Exception exception) {
			exception.printStackTrace(System.err);
			System.err.flush();
		}
	}

	private SshInspector() throws Exception {

		/* *************************************************** */
		// Load platforms and probes properties
		/* *************************************************** */

		final List<Properties> pfs = loadPropertiesDir(new File("platforms"), "platforme");
		final List<Properties> probes = loadPropertiesDir(new File("probes"), "sonde");

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

			System.out.println("");
			
			System.out.println("=========================================================================================================================");
			System.out.println("Connecting to '" + pfName + "' : '" + pfHost + ":" + pfPort + "@" + pfLogin + ":" + pfPwd + "'");
			System.out.println("=========================================================================================================================");

			Session session = null;

			try {
				session = connect(pfHost, pfPort, pfLogin, pfPwd);

				for(final Properties probe : probes) {
					final String pbName = probe.getProperty(NAME);
					final String pbDescription = probe.getProperty(DESCRIPTION);
					final String pbScript = probe.getProperty(SCRIPT);

					System.out.println("-------------------------------------------------------------------------------------------------------------------------");
					System.out.println("Executing '" + pbName + "' : '" + pbScript + "'");

					try {
						final String result = execute(session, pbScript);
						final String finalResult = result.substring(result.lastIndexOf(RESULT_BEGIN) + RESULT_BEGIN.length(), result.lastIndexOf(RESULT_END));

						pfResults.put(pbName, finalResult);
						
						System.out.println("Résultats intermédiaire => final : '" + result + "' => '" + finalResult + "'");
					}
					catch(final Exception exception) {
						System.err.println("Echec d'exécution !");
						exception.printStackTrace(System.err);
						System.err.flush();

						pfResults.put(pbName, "Probe failed");
					}
				}
			}
			catch(final Exception exception) {
				System.err.println("Echec de connexion !");
				exception.printStackTrace(System.err);
				System.err.flush();
			}
			finally {
				if(session != null) {
					session.disconnect();
				}
			}
			
			System.out.println("");
		}

		csvExport(results, pfs, probes);
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
		session.setConfig("StrictHostKeyChecking", "no");
		session.setUserInfo(ui);
		session.connect();

		return session;
	}

	private String execute(final Session session, final String script) throws Exception {
		StringBuilder outputBuffer = new StringBuilder();

		final ChannelExec channel = (ChannelExec)session.openChannel("exec");

		channel.setErrStream(System.err);

		channel.setCommand(script);
		InputStream in = channel.getInputStream();

		channel.connect();

		try {
			Thread.sleep(1000);
		}
		catch(Exception exception){
			exception.printStackTrace(System.err);
			System.err.flush();
		}

		while(in.available() > 0) {
			outputBuffer.append((char)in.read());
		}

		channel.disconnect();

		return new String(outputBuffer);
	}

	/**
	 * Load all properties files in the specified directory.
	 * @param dir directory in which are the properties files
	 * @return list of the properties
	 */
	private List<Properties> loadPropertiesDir(final File dir, final String object) throws Exception {
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

			System.out.println(object + " chargée '" + file + "'");
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

	private void csvExport(final Map<String, Map<String, String>> results, final List<Properties> pfs, final List<Properties> probes) throws Exception {

		/* *************************************************** */
		// Prepare CSV organisation
		/* *************************************************** */

		final List<String> pfNames = new ArrayList<>();
		final List<String> probeNames = new ArrayList<>();

		for(final Properties pf : pfs) {
			final String pfName = pf.getProperty(NAME);
			pfNames.add(pfName);
			System.out.println("pfName" + pfName);
		}

		for(final Properties probe : probes) {
			final String pbName = probe.getProperty(NAME);
			probeNames.add(pbName);
			System.out.println("pbName" + pbName);
		}


		/* *************************************************** */
		// CSV export
		/* *************************************************** */

		final FileWriter writer = new FileWriter("results.csv");

		// Export platforms list
		writer.write("PFS");
		
		for(final String pfName : pfNames) {
			writer.write(";");
			writer.write(pfName);
		}

		writer.write("\n");

		// Export probes results
		for(final String pbName : probeNames) {
			writer.write(pbName);

			for(final String pfName : pfNames) {
				final Map<String, String> pbs = results.get(pfName);

				writer.write(";");

				final String pbResult = pbs.get(pbName);

				if(pbResult != null) {
					writer.write(pbResult);
				}
				else {
					writer.write("-");
				}
			}
			
			writer.write("\n");
		}

		writer.flush();		
		IOUtils.closeQuietly(writer);
	}
}
