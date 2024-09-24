package com.hostmdy.ecommerce.utility;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClasspathFileLoader {
	private final ResourceLoader resourceLoader;
	
	public String getClasspathFileRelativePath(String filePath) throws IOException {
		Resource resource = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX+filePath);
		return resource.getFile().getAbsolutePath();
	}
	
}
