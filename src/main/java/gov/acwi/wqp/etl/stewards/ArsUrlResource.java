package gov.acwi.wqp.etl.stewards;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.io.UrlResource;

public class ArsUrlResource extends UrlResource {

	public ArsUrlResource(String path) throws MalformedURLException {
		super(path);
	}

	public ArsUrlResource(URL path) throws MalformedURLException {
		super(path);
	}

	@Override
	protected void customizeConnection(HttpURLConnection con) throws IOException {
		//The ARS endpoint does not implement HEAD, so we will use GET instead
		con.setRequestMethod("GET");
	}

}
