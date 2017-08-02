package hello;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/webhook")
public class HelloWorldController {

   /* @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String obj){

        System.out.println(obj);

        return new WebhookResponse("Hello! " + obj);
    }
}	*/
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody WebhookResponse webhook(@RequestBody String jsonData) throws UnsupportedEncodingException{

		 String postData = "{\"question\":\"" + getResolvedQuery(jsonData) + "\"}";
		 
		String action = getAction(jsonData);
		
		if(action.equalsIgnoreCase("getAddress")){
		CloseableHttpClient httpclient = HttpClients.custom()
		         .build();
		String responseBody = null;
		String actionValue = null;
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
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode rootNode = objectMapper.readTree(responseBody.getBytes());

			JsonNode result = rootNode.path("answers");
			                           
			for (JsonNode node : result) {
			           actionValue = node.path("answer").asText();
			                           System.out.println(actionValue);                                             
			           }

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
        return new WebhookResponse(actionValue, actionValue);
    }
		return null;
}		
		private String getAction(String jsonData) {
            ObjectMapper objectMapper = new ObjectMapper();
    try {
        JsonNode rootNode = objectMapper.readTree(jsonData.getBytes());
      
        JsonNode result = rootNode.path("result");
        JsonNode action = result.path("action");
        String actionValue = action.asText();   
        return actionValue;
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "";
}
		
	    private String getResolvedQuery (String jsonData) {
            ObjectMapper objectMapper = new ObjectMapper();
    try {
        JsonNode rootNode = objectMapper.readTree(jsonData.getBytes());
      
        JsonNode result = rootNode.path("result");
        JsonNode resolvedQuery = result.path("resolvedQuery");
        String resolvedQueryStr = resolvedQuery.asText();   
        return resolvedQueryStr;
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "";
}

}

