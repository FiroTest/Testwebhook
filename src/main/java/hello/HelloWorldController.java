package hello;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


@Controller
@RequestMapping("/webhook")
public class HelloWorldController {

    /*@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj){

        System.out.println(obj);

        return new WebhookResponse("Hello! " + obj, "Text " + obj);
    }*/
	
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String inputData) throws UnsupportedEncodingException{

		String postData = "{\"question\":inputData}";
      
		CloseableHttpClient httpclient = HttpClients.custom()
		         .build();
		String responseBody = null;
		try {
		 HttpPost httpPost = new HttpPost("https://westus.api.cognitive.microsoft.com/qnamaker/v2.0/knowledgebases/8ed8da68-db9b-45ba-8424-2e12c36273ce/generateAnswer");
		       httpPost.setHeader("Accept", "application/json");
		       httpPost.setHeader("Content-Type", "application/json");
		       httpPost.setHeader("Ocp-Apim-Subscription-Key", "1b4908fa1b6d4bf4a4b9bd8dfdb6eb08");
		 HttpEntity entity = new ByteArrayEntity(postData.getBytes("utf-8"));
		       httpPost.setEntity(entity);
		 
		System.out.println("Executing request " + httpPost.getRequestLine());
		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
		   System.out.println("----------------------------------------");
		   System.out.println(response.getStatusLine());
		   responseBody = EntityUtils.toString(response.getEntity());
		   System.out.println(responseBody);
		} finally {
		   response.close();
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
			finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
        return new WebhookResponse(responseBody);
    }
}
