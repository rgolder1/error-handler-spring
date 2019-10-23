package errorhandler.messages;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Find all the message resource bundles under messages dir in the class path
 * and load each as a message source. Also parse the default properties file to build
 * a map of error code to template
 */
public class MessageSource extends ReloadableResourceBundleMessageSource {
	private static final String LOCATION_PATTERN = "classpath*:messages/**/*-msg.properties";

	private PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private List<Resource> resourceList = new ArrayList<>();

	public MessageSource loadMessages(){
		try{
			Resource[] resources = resourcePatternResolver.getResources(LOCATION_PATTERN);
			for (Resource resource : resources) {
				loadMessages(resource);
			}
		}
		catch (Exception e){
			throw new RuntimeException("Failed to setup messages sources", e);
		}
		return this;
	}

	/**
	 * Determine basename from Resource URL and add it to our list of Resource Bundle base names
	 * @param resource
	 * @throws IOException
	 */
	private void loadMessages(Resource resource) throws IOException {
		resourceList.add(resource);
		URL url = resource.getURL();
		String basename = basenameFromUrl(url.toString());
		addBasenames(basename);
	}

	private String basenameFromUrl(String url) {
		int pos1 = url.indexOf("messages");
		int pos2 = url.indexOf(".properties");
		return "classpath:" + url.substring(pos1,pos2);
	}
}
