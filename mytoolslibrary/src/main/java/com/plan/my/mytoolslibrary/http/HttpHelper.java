package com.plan.my.mytoolslibrary.http;

import android.net.Uri;
import android.text.TextUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.plan.my.mytoolslibrary.file.FileManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpHelper {

	public interface DownloadListener {
		public void pushProgress(int progress, int max);

		public void completed(String url, String localPath);

		public void cancel(String url);
	}
	
	// private static final int CONNECT_TIMEOUT = 10 * 1000;

	// private static final int READ_TIMEOUT = 10 * 1000;

	private static final int DOWNLOAD_CONNECT_TIMEOUT = 15 * 1000;

	private static final int DOWNLOAD_READ_TIMEOUT = 60 * 1000;

	// private static final int UPLOAD_CONNECT_TIMEOUT = 15 * 1000;

	// private static final int UPLOAD_READ_TIMEOUT = 5 * 60 * 1000;

	/** {@value} */
	protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

	protected static final int MAX_REDIRECT_COUNT = 5;

	/** {@value} */
	protected static final int BUFFER_SIZE = 32 * 1024; // 32 Kb

	private static Proxy getProxy() {
		String proxyHost = System.getProperty("http.proxyHost");
		String proxyPort = System.getProperty("http.proxyPort");
		if (!TextUtils.isEmpty(proxyHost) && !TextUtils.isEmpty(proxyPort)) {
			return new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(
					proxyHost, Integer.valueOf(proxyPort)));
		} else {
			return null;
		}
	}


	// Create an anonymous class to trust all certificates.
	// This is bad style, you should create a separate class.
	private X509TrustManager xtm = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			// System.out.println("cert: " + chain[0].toString() + ", authType:
			// "
			// + authType);
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};
	// Create an class to trust all hosts
	private HostnameVerifier hnv = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			// System.out.println("hostname: " + hostname);
			return true;
		}
	};



	public static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		// Android use X509 cert
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain,
										   String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
										   String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};


	public boolean doGetSaveFile(String urlStr, String localPath,
								 DownloadListener downloadListener) {

		File file = FileManager.createNewFile(localPath);
		if (file == null) {
			return false;
		}

		boolean result = false;

		BufferedOutputStream out = null;
		InputStream in = null;
		HttpURLConnection urlConnection = null;
		try {


			/**
			 // 创建SSLContext对象，并使用我们指定的信任管理器初始化
			 TrustManager[] tm = { new MyX509TrustManager() };
			 SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			 sslContext.init(null, tm, new java.security.SecureRandom());
			 // 从上述SSLContext对象中得到SSLSocketFactory对象
			 SSLSocketFactory ssf = sslContext.getSocketFactory();
			 ***/
			SSLContext sslContext = null;
			try {
				sslContext = SSLContext.getInstance("SSL");//TLS
				X509TrustManager[] xtmArray = new X509TrustManager[] {xtm};
				sslContext.init(null, xtmArray, new java.security.SecureRandom());
			} catch (GeneralSecurityException e) {
				// Print out some error message and deal with this exception
				e.printStackTrace();
			}

			URL url = new URL(urlStr);
//			AppLogger.d("download request=" + urlStr);
			Proxy proxy = getProxy();
//			if (proxy != null) {
//				urlConnection = (HttpURLConnection) url.openConnection(proxy);
//			} else {
				urlConnection = (HttpURLConnection) url.openConnection();
//			}

//			if (sslContext != null) {
//				urlConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//			}
//			urlConnection.setDefaultHostnameVerifier(hnv);


			//关键代码
			//ignore https certificate validation |忽略 https 证书验证
			if (url.getProtocol().toUpperCase().equals("HTTPS")) {
				trustAllHosts();
				HttpsURLConnection https = (HttpsURLConnection) url
						.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				urlConnection = https;
			} else {
				urlConnection = (HttpURLConnection) url.openConnection();
			}


		/* 允许Input、Output，不使用Cache */
//			urlConnection.setDoInput(true);
//			urlConnection.setDoOutput(true);
//			urlConnection.setUseCaches(false);

			urlConnection.setRequestMethod("GET");//GET  POST
			urlConnection.setDoOutput(false);
			urlConnection.setConnectTimeout(DOWNLOAD_CONNECT_TIMEOUT);
			urlConnection.setReadTimeout(DOWNLOAD_READ_TIMEOUT);
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Charset", "UTF-8");//UTF-8  GBK
			urlConnection
					.setRequestProperty("Accept-Encoding", "gzip, deflate");

			urlConnection.connect();

			int status = urlConnection.getResponseCode();

			if (status != HttpURLConnection.HTTP_OK) {
				return false;
			}

			int bytetotal = (int) urlConnection.getContentLength();
			int bytesum = 0;
			int byteread = 0;
			out = new BufferedOutputStream(new FileOutputStream(file));

			InputStream is = urlConnection.getInputStream();
			String content_encode = urlConnection.getContentEncoding();
			if (!TextUtils.isEmpty(content_encode)
					&& content_encode.equals("gzip")) {
				is = new GZIPInputStream(is);
			}
			in = new BufferedInputStream(is);

			final Thread thread = Thread.currentThread();
			byte[] buffer = new byte[1444];
			while ((byteread = in.read(buffer)) != -1) {
				if (thread.isInterrupted()) {
					if (((float) bytesum / (float) bytetotal) < 0.8f) {
						file.delete();
						throw new InterruptedIOException();
					}

				}

				bytesum += byteread;
				out.write(buffer, 0, byteread);
				if (downloadListener != null && bytetotal > 0) {
					downloadListener.pushProgress(bytesum, bytetotal);
				}
			}
			if (downloadListener != null) {
				downloadListener.completed(urlStr, localPath);
			}
//			AppLogger.v("download request= " + urlStr + " download finished");
			result = true;

		} catch (IOException e) {
			e.printStackTrace();
//			AppLogger.v("download request= " + urlStr + " download failed");
		} finally {
			closeSilently(in);
			closeSilently(out);
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		// return result && ImageUtility.isThisBitmapCanRead(path);
		return result;
	}

	/**
	 * Retrieves {@link InputStream} of image by URI (image is located in the
	 * network).
	 * 
	 * @param url
	 *            URI
	 * @param extra
	 *            Auxiliary object which was passed to
	 *            {@link DisplayImageOptions.Builder#extraForDownloader(Object)
	 *            DisplayImageOptions.extraForDownloader(Object)}; can be null
	 * @return {@link InputStream} of image
	 * @throws IOException
	 *             if some I/O error occurs during network request or if no
	 *             InputStream could be created for URL.
	 */
	public InputStream getStreamFromNetwork(String url, Object extra)
			throws IOException {
		HttpURLConnection conn = createConnection(url, extra);

		int redirectCount = 0;
		while (conn.getResponseCode() / 100 == 3
				&& redirectCount < MAX_REDIRECT_COUNT) {
			conn = createConnection(conn.getHeaderField("Location"), extra);
			redirectCount++;
		}

		InputStream imageStream;
		try {
			imageStream = conn.getInputStream();
		} catch (IOException e) {
			// Read all data to allow reuse connection (http://bit.ly/1ad35PY)
			IoUtils.readAndCloseStream(conn.getErrorStream());
			throw e;
		}
		return new ContentLengthInputStream(new BufferedInputStream(
				imageStream, BUFFER_SIZE), conn.getContentLength());
	}

	/**
	 * Create {@linkplain HttpURLConnection HTTP connection} for incoming URL
	 * 
	 * @param url
	 *            URL to connect to
	 * @param extra
	 *            Auxiliary object which was passed to
	 *            {@link DisplayImageOptions.Builder#extraForDownloader(Object)
	 *            DisplayImageOptions.extraForDownloader(Object)}; can be null
	 * @return {@linkplain HttpURLConnection Connection} for incoming URL.
	 *         Connection isn't established so it still configurable.
	 * @throws IOException
	 *             if some I/O error occurs during network request or if no
	 *             InputStream could be created for URL.
	 */
	protected HttpURLConnection createConnection(String url, Object extra)
			throws IOException {
		String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl)
				.openConnection();
		conn.setConnectTimeout(DOWNLOAD_CONNECT_TIMEOUT);
		conn.setReadTimeout(DOWNLOAD_READ_TIMEOUT);
		return conn;
	}

	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {

			}
		}
	}
}