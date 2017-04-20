package utils.json;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtils {

	private static String appData = "";

	static {

		BufferedReader reader = null;
		try {
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = patternResolver.getResources("classpath*:appData.json");
			if (resources != null && resources.length > 0) {
				reader = new BufferedReader(new InputStreamReader(resources[0].getInputStream(), "UTF-8"));
				String temp = null;
				while ((temp = reader.readLine()) != null) {
					appData += temp;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
