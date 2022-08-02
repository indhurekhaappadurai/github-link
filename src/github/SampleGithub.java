package github;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SampleGithub
{

        public static void main(String[] args) throws Throwable {
            String link = "https://raw.githubusercontent.com/indhukuty/sample/main/helloworld";
            URL url = new URL(link);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            Map<String, List<String>> head = http.getHeaderFields();


            for (String header : head.get(null)) {
                if (header.contains(" 302 ")
                        || header.contains(" 301 ")) {
                    link = head.get("Location").get(0);
                    url  = new URL(link);
                    http = (HttpURLConnection) url.openConnection();
                    head = http.getHeaderFields();
                }
            }

            InputStream stream = http.getInputStream();
            String response = getStringFromStream(stream);
            System.out.println(response);
        }

        // ConvertStreamToString() Utility - we name it as GetStringFromStream()
        private static String getStringFromStream(InputStream Stream) throws IOException {
            if (Stream != null) {
                Writer writer = new StringWriter();

                char[] buffer = new char[2048];
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(Stream, "UTF-8"));
                    int counter;
                    while ((counter = reader.read(buffer)) != -1) {
                        writer.write(buffer, 0, counter);
                    }
                } finally {
                    Stream.close();
                }
                return writer.toString();
            } else {
                return "No Contents";
            }
        }
    }

